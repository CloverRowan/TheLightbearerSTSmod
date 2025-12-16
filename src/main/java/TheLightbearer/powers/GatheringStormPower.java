package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
        this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1];
    }

    public void onInitialApplication(){
        this.counter = 0;
    }

    public void atStartOfTurn(){
        if((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            addToBot(new VFXAction((new LightningEffect(this.owner.drawX, this.owner.drawY))));
            addToBot(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
            addToBot(new LoseHPAction(this.owner, null, this.amount, AbstractGameAction.AttackEffect.NONE));
            addToBot(new ApplyPowerAction(this.owner, this.owner, this, 1));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new GatheringStormPower(owner, amount);
    }


    @Override
    public int getHealthBarAmount() {
        return this.amount;
    }

    @Override
    public Color getColor() {
        return Color.CYAN;
    }
}
