package TheLightbearer.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ElectroPlatingAction extends AbstractGameAction {
    AbstractPlayer p;
    private int magicNumber;
    boolean upgraded;
    private int energyOnUse;
    private boolean freeToPlayOnce;

    public ElectroPlatingAction(AbstractPlayer p, int magicNumber,boolean upgraded,boolean freeToPlayOnce,int energyOnUse) {
        this.actionType = ActionType.SPECIAL;
        this.magicNumber = magicNumber;
        this.upgraded= upgraded;
        this.energyOnUse = energyOnUse;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
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
            for (int i = 0; i < effect; ++i) {

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                        new MetallicizePower(p, this.magicNumber), this.magicNumber));
            }
            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
            }
        isDone = true;
        }
    }

