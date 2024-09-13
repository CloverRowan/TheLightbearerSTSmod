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

public class HeavyKnifeAction extends AbstractGameAction {
    private final AbstractPlayer p;

    private final int magicNumber;

    private DamageInfo info;

    private final float startOffsetx = 450.0F;

    private final float offsety = 250.0F;

    private final float endOffsetx = 320.0F;

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
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new HeavyKnifeEffect(AbstractDungeon.player.hb.cX + 450.0F, AbstractDungeon.player.hb.cY + 250.0F, 27.0F)));
            addToBot((AbstractGameAction)new WaitAction(0.1F));
            addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new HeavyKnifeEffect(AbstractDungeon.player.hb.cX + 450.0F + 320.0F, AbstractDungeon.player.hb.cY + 250.0F, pointToTarget(AbstractDungeon.player.hb.cX + 450.0F + 320.0F, AbstractDungeon.player.hb.cY + 250.0F, this.target.hb.x, this.target.hb.y))));
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
        return (float)Math.atan(((targety - starty) / (targetx - startx)));
    }
}
