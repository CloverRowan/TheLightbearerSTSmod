package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TheLightbearer.TheLightbearer.makeID;


public class PowerDifferentialPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("PowerDifferentialPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public PowerDifferentialPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + 50*this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PowerDifferentialPower(owner, amount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        if(info.type.equals(DamageInfo.DamageType.NORMAL)){
            for (int i = 0; i  < this.amount; i++) {
                if(damageAmount > 0)
                    addToBot(new GainBlockAction(owner, (damageAmount / 2)));
            }
        }
    }
}
