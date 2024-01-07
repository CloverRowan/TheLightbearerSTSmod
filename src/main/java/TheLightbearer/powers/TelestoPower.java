package TheLightbearer.powers;

import TheLightbearer.patches.EnemyOnExhaust;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import static TheLightbearer.TheLightbearer.logger;
import static TheLightbearer.TheLightbearer.makeID;


public class TelestoPower extends BasePower implements CloneablePowerInterface, EnemyOnExhaust.EnemyOnExhaustPower,
            HealthBarRenderPower {
        public static final String POWER_ID = makeID("TelestoPower");
        private static final PowerType TYPE = PowerType.DEBUFF;
        private static final boolean TURN_BASED = false;

        private int counter = 0;

        public TelestoPower(AbstractCreature owner, int amount) {
            super(POWER_ID, TYPE, TURN_BASED, owner, amount);
            this.counter = 0;
        }

        public void updateDescription() {
            if(counter == 0){
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3];
            }else{
                this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1]+ DESCRIPTIONS[3];
            }

        }

        public void onInitialApplication(){
            this.counter = 0;
        }

        public void atStartOfTurn(){
            if((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                    !AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.counter == 1){
                flashWithoutSound();
                //addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING));
                addToBot(new LoseHPAction(this.owner, null, this.amount, AbstractGameAction.AttackEffect.FIRE));
                addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
            }else if((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                    !AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.counter == 0){
                flashWithoutSound();
                this.counter = 1;
                this.updateDescription();
            }
        }

    @Override
    public void enemyOnExhaust(AbstractCard card) {
        //logger.info("exhausted a card");
        if((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()){
            //logger.info("passed logic gate");
            //addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING));
            addToBot(new LoseHPAction(this.owner, null, this.amount, AbstractGameAction.AttackEffect.FIRE));
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }
    }

    @Override
        public AbstractPower makeCopy() {
            return new TheLightbearer.powers.GatheringStormPower(owner, amount);
        }


        @Override
        public int getHealthBarAmount() {
            if(counter ==1){
                return this.amount;
            }
            else{
                return 0;
            }
        }

        @Override
        public Color getColor() {
            return Color.ROYAL;
        }
    }


