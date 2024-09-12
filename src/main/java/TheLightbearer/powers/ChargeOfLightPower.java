package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

import static TheLightbearer.TheLightbearer.makeID;
import static TheLightbearer.util.CustomTags.SUPERSPELL;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class ChargeOfLightPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("ChargeOfLightPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public boolean playForFree;
    public static ArrayList <Boolean> freeCharge = new ArrayList<>();
    public static final int SUPER_COST = 5;

    public ChargeOfLightPower(AbstractCreature owner, int amount){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }
    public void updateDescription(){
        this.description = DESCRIPTIONS[0];
    }
    @Override
    public AbstractPower makeCopy() {
        return new ChargeOfLightPower(owner, amount);
    }

    public static ArrayList<Boolean> getFreeCharge(){
        return freeCharge;
    }
    public static void setFreeCharge(ArrayList<Boolean> newFreeCharge){
        freeCharge = newFreeCharge;
    }


    @Override
    public void stackPower(int stackAmount) {
        /*if(this.amount%10 + stackAmount >= 10 && AbstractDungeon.player != null && AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){

            //DimmerSwitch logic
            for (AbstractRelic r : player.relics) {
                if (r.relicId.equals(makeID("DimmerSwitch"))) {
                    addToBot(new GainEnergyAction(2));
                    r.flash();
                }
            }

            //TODO play supercharged sound
        }*/
        int newAmount = this.amount + stackAmount;
        flashWithoutSound();
       /* if(newAmount >= MAX_STACKS && AbstractDungeon.player != null && AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {

            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new SuperCharged(AbstractDungeon.player, 1)));
            this.amount = newAmount -MAX_STACKS;
        }else{
            this.amount = newAmount;
        }*/
        this.amount = newAmount;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.tags.contains(SUPERSPELL) && !card.purgeOnUse) {
            flash();
            this.amount-=SUPER_COST;
        }
    }

    public void atStartOfTurnPostDraw() {
        addToBot(new ApplyPowerAction(player, player,
                new ChargeOfLightPower(player, 1)));
    }
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (player != null && AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                c.type.equals(AbstractCard.CardType.ATTACK)) {

            addToTop(new ApplyPowerAction(player, player,
                    new ChargeOfLightPower(player, 1)));
        }
        if(player.hasPower("DuplicationPower")){
            for (int i = 0; i<=1; i++) {
                freeCharge.add(playForFree);
                freeCharge.set(i, true);
            }
        }
    }

}
