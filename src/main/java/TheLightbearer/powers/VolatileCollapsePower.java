package TheLightbearer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TheLightbearer.TheLightbearer.makeID;


public class VolatileCollapsePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("VolatileCollapsePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public VolatileCollapsePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        addToBot(new DamageRandomEnemyAction(new DamageInfo(this.owner , this.amount , DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VolatileCollapsePower(owner, amount);
    }


}
