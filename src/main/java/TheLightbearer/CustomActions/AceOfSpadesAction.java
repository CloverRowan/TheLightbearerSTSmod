package TheLightbearer.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class AceOfSpadesAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int magicNumber;

    private DamageInfo info;

    public AceOfSpadesAction(AbstractPlayer p, AbstractMonster target, DamageInfo info, int magicNumber) {
        this.actionType = ActionType.DAMAGE;
        this.p = p;
        this.target = target;
        this.magicNumber = magicNumber;
        this.duration = 0.1F;
        this.info = info;
    }

    public void update() {
        if(this.duration == 0.1F && this.target != null){
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY,AttackEffect.BLUNT_LIGHT));
            this.target.damage(this.info);
            if(this.target.isDying || this.target.currentHealth<=0 && !this.target.halfDead){
                addToBot(new DamageAllEnemiesAction(p, this.info.output, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
                addToBot(new ApplyPowerAction(p, p, new StrengthPower(p,this.magicNumber), this.magicNumber));
            }
        }
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersDead()){
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        this.isDone = true;
    }
}