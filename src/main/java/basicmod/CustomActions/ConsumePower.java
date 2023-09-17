package basicmod.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ConsumePower extends AbstractGameAction {
    private AbstractPlayer p;
    private String power;
    private int magic;
    public ConsumePower(AbstractPlayer p, String power, int magic){
        this.actionType = ActionType.REDUCE_POWER;
        this.p = p;
        this.power = power;
        this.magic = magic;
    }
    public boolean ConsumePowerAction() {

        for (AbstractPower FindPower : player.powers) {
            if (FindPower.ID.equals(power)) {
                if (FindPower.amount >= magic) {
                    FindPower.amount = FindPower.amount - magic;
                    return true;
                }
            }
        }
        return false;
    }
    public void update(){

    }
}

