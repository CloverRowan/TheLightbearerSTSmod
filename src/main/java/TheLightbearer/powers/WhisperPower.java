package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TheLightbearer.TheLightbearer.makeID;

public class WhisperPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("WhisperPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final int ENERGY_GAIN = 2;


    public WhisperPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount == 3){
            addToBot(new GainEnergyAction(ENERGY_GAIN));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new Energized(owner, amount);
    }


}
