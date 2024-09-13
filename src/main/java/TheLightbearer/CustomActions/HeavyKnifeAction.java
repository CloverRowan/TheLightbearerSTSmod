package TheLightbearer.CustomActions;

import TheLightbearer.Effects.HeavyKnifeEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import jdk.jfr.internal.Logger;

import static TheLightbearer.TheLightbearer.logger;

public class HeavyKnifeAction extends AbstractGameAction {
    private final AbstractPlayer p;

    private final int magicNumber;

    private DamageInfo info;

    private final float startOffsetx = 550.0F;

    private final float offsety = 150.0F;

    private final float endOffsetx = 290.0F;
    private final float length = 500;

    public HeavyKnifeAction(AbstractPlayer p, AbstractMonster target, DamageInfo info, int magicNumber) {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.p = p;
        this.target = (AbstractCreature)target;
        this.magicNumber = magicNumber;
        this.duration = 0.1F;
        this.info = info;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new HeavyKnifeEffect(AbstractDungeon.player.hb.cX + startOffsetx, AbstractDungeon.player.hb.cY + offsety, 27.0F)));
            addToBot((AbstractGameAction)new WaitAction(0.1F));
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new HeavyKnifeEffect(AbstractDungeon.player.hb.cX + startOffsetx + 2*endOffsetx, AbstractDungeon.player.hb.cY + offsety, -pointToTarget(AbstractDungeon.player.hb.cX + 2*startOffsetx + 2*endOffsetx, AbstractDungeon.player.hb.cY + offsety, this.target.hb.x, this.target.hb.y),length)));
            addToBot((AbstractGameAction)new DamageAction(this.target, this.info));
            if (this.target.isDying || this.target.currentHealth <= 0)
                addToBot((AbstractGameAction)new SkipEnemiesTurnAction());
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersDead())
            AbstractDungeon.actionManager.clearPostCombatActions();
        this.isDone = true;
    }

    public float pointToTarget(float startx, float starty, float targetx, float targety) {
        return (float)Math.toDegrees(Math.atan(((targety - starty) / (targetx - startx))));
    }
}
