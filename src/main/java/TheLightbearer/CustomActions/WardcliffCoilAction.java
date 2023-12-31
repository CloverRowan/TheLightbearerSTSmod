package TheLightbearer.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static TheLightbearer.util.CustomTags.*;

import static TheLightbearer.TheLightbearer.logger;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class WardcliffCoilAction extends AbstractGameAction {
    AbstractPlayer p;
    private int[] multiDamage;
    boolean upgraded;
    private int energyOnUse;
    private boolean freeToPlayOnce;
    private DamageInfo.DamageType damageType;


    public WardcliffCoilAction(AbstractPlayer p, int[] multiDamage, DamageInfo.DamageType damageType, boolean freeToPlayOnce, boolean upgraded, int energyOnUse) {
        this.actionType = ActionType.SPECIAL;
        this.multiDamage = multiDamage;
        this.upgraded = upgraded;
        this.energyOnUse = energyOnUse;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.damageType = damageType;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            effect = energyOnUse;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();

        }
        if (effect > 0) {
            int arcCount = 0;
            for (AbstractCard c : player.hand.group) {
                if (c.tags.contains(ARC)) {
                    arcCount++;
                }
            }
            for (AbstractCard c : player.discardPile.group) {
                if (c.tags.contains(ARC)) {
                    arcCount++;
                }
            }
            for (AbstractCard c : player.drawPile.group) {
                if (c.tags.contains(ARC)) {
                    arcCount++;
                }
            }
            effect += arcCount;


            for (int i = 0; i < effect; ++i) {
                addToBot((AbstractGameAction) new DamageAllEnemiesAction((AbstractCreature) this.p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));

            }
        }
        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
            //logger.info("energy down");
        }
        isDone = true;
        //logger.info("Done");
    }
}

