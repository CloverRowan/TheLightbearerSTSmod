package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TheLightbearer.TheLightbearer.makeID;


public class FeedTheVoidPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("FeedTheVoidPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public FeedTheVoidPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + " card" + (this.amount > 1 ? "s." :".");
    }

    @Override
    public AbstractPower makeCopy() {
        return new FeedTheVoidPower(owner, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new ExhaustAction(this.amount, false, true, true));
    }
}

