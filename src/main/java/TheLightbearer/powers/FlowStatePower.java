package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TheLightbearer.TheLightbearer.makeID;


public class FlowStatePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("FlowStatePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public FlowStatePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if(card.type == AbstractCard.CardType.ATTACK){
            addToBot(new DrawCardAction(this.amount));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FlowStatePower(owner, amount);
    }


}
