package TheLightbearer.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class CheckPowerStacks extends AbstractGameAction {
    private AbstractPlayer p;
    private String power;


    public CheckPowerStacks(AbstractPlayer player, String power) {
        this.actionType = ActionType.ENERGY;
        this.p = p;
        this.power = power;

    }

    public int CheckPowerStacksAction() {
        for (AbstractPower FindPower : player.powers) {
            if (FindPower.ID.equals(power)) {
                return FindPower.amount;
            }
        }
        return 0;
    }


    public void update() {

    }
}