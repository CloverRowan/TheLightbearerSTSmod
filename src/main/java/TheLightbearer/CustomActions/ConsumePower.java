package TheLightbearer.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ConsumePower extends AbstractGameAction {
    private final boolean consumeIfNotEnough;
    private final AbstractPlayer p;
    private final String power;
    private final int magic;

    public ConsumePower(AbstractPlayer p, String power, int magic){
        this(p, power, magic, false);
    }
    public ConsumePower(AbstractPlayer p, String power, int magic, boolean consumeIfNotEnough){
        this.actionType = ActionType.REDUCE_POWER;
        this.p = p;
        this.power = power;
        this.magic = magic;
        this.consumeIfNotEnough = consumeIfNotEnough;
    }
    public boolean ConsumePowerAction() {

        for (AbstractPower FindPower : player.powers) {
            if (FindPower.ID.equals(power)) {
                if (FindPower.amount >= magic) {
                    FindPower.amount = FindPower.amount - magic;
                    return true;
                }
                else if(consumeIfNotEnough){
                    FindPower.amount = 0;
                }
            }
        }
        return false;
    }

    public void update(){

    }
}
 /*public int QueryPowerAmount() {
    todo fix this nullpointer
        for (AbstractPower FindPower : player.powers) {
                if (FindPower.ID.equals(power)) {
                    magic = FindPower.amount;
                    return magic;

                }
        }
    return 0;
    }*/

