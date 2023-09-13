package basicmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static basicmod.TheLightbearer.makeID;


public class ChargeOfLightPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("ChargeOfLight");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public final int MAX_STACKS = 10;

    public ChargeOfLightPower(AbstractCreature owner, int amount){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }
    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + MAX_STACKS + DESCRIPTIONS[1];
    }
    @Override
    public AbstractPower makeCopy() {
        return new ChargeOfLightPower(owner, amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        int newAmount = this.amount + stackAmount;
        flash();
        if(newAmount >= MAX_STACKS && AbstractDungeon.player != null && AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {

            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new SuperCharged(AbstractDungeon.player, 1)));
            this.amount = newAmount -MAX_STACKS;
        }else{
            this.amount = newAmount;
        }
    }
}
