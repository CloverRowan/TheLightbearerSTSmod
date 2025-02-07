package TheLightbearer.CustomActions;

import TheLightbearer.Effects.ComfortingFlamesEffect;
import TheLightbearer.Effects.HeavyKnifeEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.stream.LongStream;

public class ComfortingFlamesAction extends AbstractGameAction {
    private final AbstractPlayer p;

   private final int Block;

    private DamageInfo info;

    private final float startOffsetx = 550.0F;

    private final float offsety = 150.0F;

    private final float endOffsetx = 290.0F;
    private final float length = 500;

    public ComfortingFlamesAction(AbstractPlayer p, AbstractMonster target, DamageInfo info, int block) {
        this.actionType = ActionType.DAMAGE;
        this.p = p;
        this.target = p; //self damage targets player;
        this.Block = block;
        this.duration = 0.1F;
        this.info = info;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            for(int i = 0; i < 10; i++){
                addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new ComfortingFlamesEffect(p.hb.cX,p.hb.cY+200)));
            }
            addToBot(new LoseHPAction(p, p, info.base));
            addToBot(new GainBlockAction(p,this.Block));
        }
        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersDead())
            AbstractDungeon.actionManager.clearPostCombatActions();
        this.isDone = true;
    }
}
