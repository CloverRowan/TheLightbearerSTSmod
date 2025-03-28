package TheLightbearer.powers;

import TheLightbearer.patches.EnemyOnExhaust;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.brashmonkey.spriter.Player;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import static TheLightbearer.TheLightbearer.logger;
import static TheLightbearer.TheLightbearer.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class MonsterFrailPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("MonsterFrailPower");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public MonsterFrailPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        flashWithoutSound();

    }
    @Override
    public void duringTurn(){
        AbstractMonster m = (AbstractMonster) this.owner;
        for(AbstractGameAction action : AbstractDungeon.actionManager.actions){
            if(action.actionType == (AbstractGameAction.ActionType.BLOCK) && action.target != AbstractDungeon.player){
                action.amount = (int)Math.floor(action.amount * 0.75);
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new TheLightbearer.powers.MonsterFrailPower(owner, amount);
    }

    @Override
    public void atEndOfRound() {
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, makeID("MonsterFrailPower")));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, makeID("MonsterFrailPower"), 1));
        }
        updateDescription();
    }

}


