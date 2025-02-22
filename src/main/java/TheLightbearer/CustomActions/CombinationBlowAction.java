package TheLightbearer.CustomActions;

import TheLightbearer.cards.Arc.CombinationBlow;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;

import java.util.UUID;

public class CombinationBlowAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int magicNumber;

    private int increaseAmount;
    private DamageInfo info;

    private CombinationBlow sourceCard;

    private UUID uuid;

    public CombinationBlowAction(AbstractPlayer p, AbstractMonster target, DamageInfo info, int magicNumber, UUID sourceCardUUID) {
        this.actionType = ActionType.DAMAGE;
        this.p = p;
        this.target = target;
        this.magicNumber = magicNumber;
        this.duration = 0.1F;
        this.info = info;
        this.uuid = sourceCardUUID;
        this.increaseAmount = 1;
    }

    public void update() {
        if(this.duration == 0.1F && this.target != null && this.target.currentHealth > 0 && !this.target.isDying && !AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){

            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_LIGHT));
            this.target.damage(this.info);

            if(((this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead &&
                    !this.target.hasPower("Minion")){

                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (!c.uuid.equals(this.uuid))
                        continue;
                    c.misc += this.increaseAmount;
                    c.applyPowers();
                    c.baseMagicNumber = c.misc;
                    c.isDamageModified = false;
                }

                for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                    c.misc += this.increaseAmount;
                    c.applyPowers();
                    c.baseMagicNumber = c.misc;
                }

                //addToBot(new IncreaseMiscAction(sourceCard.uuid, sourceCard.misc, 1));
                //sourceCard.increaseHits();
            }

        }
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersDead()){
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        this.isDone = true;
    }
}