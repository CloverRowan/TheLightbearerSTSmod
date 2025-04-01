package TheLightbearer.CustomActions;

import TheLightbearer.TheLightbearer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static TheLightbearer.util.CustomTags.ARC;

public class FulminisDischargeAction extends AbstractGameAction {
    private int blockGain;
    public FulminisDischargeAction(int blockGain) {
        this.duration = 0.0F;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.blockGain = blockGain;


    }

    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.tags.contains(ARC)) {
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.blockGain));
                break;
            }
        }
        this.isDone = true;
    }
}
