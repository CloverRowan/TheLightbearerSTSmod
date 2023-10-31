package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import com.megacrit.cardcrawl.powers.AbstractPower;

import static TheLightbearer.TheLightbearer.makeID;


public class SuperArmor extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SuperArmor");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public SuperArmor(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];}

    @Override
    public AbstractPower makeCopy() {
        return new SuperArmor(owner, amount);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if(this.amount > 0) {
            reducePower(1);
        }
        if(this.amount == 0){
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,makeID("SuperArmor")));
        }
    }
    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {

        return super.atDamageReceive(damage/2, damageType);
    }
}
