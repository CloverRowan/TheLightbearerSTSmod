package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TheLightbearer.TheLightbearer.makeID;


public class EchoOfHarvestPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("EchoOfHarvestPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static int magicNumber = 2;
    public EchoOfHarvestPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + magicNumber + DESCRIPTIONS[1];
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        addToBot(new AddTemporaryHPAction(AbstractDungeon.player,AbstractDungeon.player, this.amount));
    }

    public AbstractPower makeCopy() {
        return new EchoOfHarvestPower(owner, amount);
    }

}
