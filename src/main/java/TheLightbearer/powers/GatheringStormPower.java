package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static TheLightbearer.TheLightbearer.makeID;


public class GatheringStormPower extends BasePower implements CloneablePowerInterface,
        HealthBarRenderPower {
    public static final String POWER_ID = makeID("GatheringStormPower");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    private int counter = 0;

    public GatheringStormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.counter = 0;
    }

    public void updateDescription() {
        if(counter == 0){
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }else{
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1];
        }

    }

    public void onInitialApplication(){
        this.counter = 0;
    }

    public void atStartOfTurn(){
        if((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                !AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.counter == 1){
            flashWithoutSound();
            //addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING));
            addToBot(new LoseHPAction(this.owner, null, this.amount, AbstractGameAction.AttackEffect.NONE));
            addToBot(new VFXAction((new LightningEffect(this.owner.drawX, this.owner.drawY))));
            addToBot(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }else if((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                !AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.counter == 0){
            flashWithoutSound();
            this.counter = 1;
            this.updateDescription();
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new GatheringStormPower(owner, amount);
    }


    @Override
    public int getHealthBarAmount() {
        if(counter ==1){
            return this.amount;
        }
        else{
            return 0;
        }
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }
}
