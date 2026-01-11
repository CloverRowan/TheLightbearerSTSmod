package TheLightbearer.CustomActions;

import TheLightbearer.Effects.ComfortingFlamesEffect;
import TheLightbearer.Effects.HeavyKnifeEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.stream.LongStream;

import static TheLightbearer.util.CustomTags.SOLAR;

public class ElementalPlatingAction extends AbstractGameAction {
    private final AbstractPlayer p;

    private final float startOffsetx = 550.0F;

    private final float offsety = 150.0F;

    private final float endOffsetx = 290.0F;
    private final float length = 500;

    public ElementalPlatingAction(AbstractPlayer p) {
        this.actionType = ActionType.DAMAGE;
        this.p = p;
        this.target = p; //self damage targets player;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            int solarCount = countSolar();
            addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, solarCount * 2)));
        }
        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersDead())
            AbstractDungeon.actionManager.clearPostCombatActions();
        this.isDone = true;
    }

    private int countSolar(){
        int count = 1;
        if(AbstractDungeon.isPlayerInDungeon()) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.tags.contains(SOLAR))
                    count++;
            }
        }
        return count;
    }
}
