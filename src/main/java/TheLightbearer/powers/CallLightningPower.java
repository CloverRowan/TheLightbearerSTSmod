package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TheLightbearer.TheLightbearer.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class CallLightningPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("CallLightningPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public CallLightningPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CallLightningPower(owner, amount);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        for (AbstractCard c: player.discardPile.group) {
            if(c.cardID.equals(makeID("CallLightningAttack"))){
                addToBot(new DiscardToHandAction(c));
            }
        }
    }
}
