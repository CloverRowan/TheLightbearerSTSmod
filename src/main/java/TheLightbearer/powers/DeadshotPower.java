package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import static TheLightbearer.TheLightbearer.logger;
import java.util.ArrayList;

import static TheLightbearer.TheLightbearer.makeID;
import static TheLightbearer.util.CustomTags.SUPERSPELL;


public class DeadshotPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("DeadshotPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private int cardsDoubledThisTurn = 0;
    ArrayList<AbstractCard> attacksPlayedThisTurn = new ArrayList<>();

    public DeadshotPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if(this.amount == 1){
            this.description = DESCRIPTIONS[0];
        }else{
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DeadshotPower(owner, amount);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.cardsDoubledThisTurn = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        attacksPlayedThisTurn.clear();
        for (AbstractCard c: AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if(c.type.equals(AbstractCard.CardType.ATTACK))
                attacksPlayedThisTurn.add(c);
        }

        if (!card.purgeOnUse && this.amount > 0 && attacksPlayedThisTurn.size() - this.cardsDoubledThisTurn <= this.amount && card.type.equals(AbstractCard.CardType.ATTACK)) {

            //Allows supers to be double-played regardless of light
            if(card.tags.contains(SUPERSPELL)){
                ArrayList<Boolean> freeChargeCopy = ChargeOfLightPower.getFreeCharge();
                freeChargeCopy.add(true);
                ChargeOfLightPower.setFreeCharge(freeChargeCopy);
            }

            this.cardsDoubledThisTurn++;
            flash();
            AbstractMonster m = null;
            if (action.target != null)
                m = (AbstractMonster)action.target;
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0F;
            if (m != null)
                tmp.calculateCardDamage(m);
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
        }
    }
}
