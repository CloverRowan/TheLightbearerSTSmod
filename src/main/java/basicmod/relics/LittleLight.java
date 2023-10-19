package basicmod.relics;

import basicmod.TheLightbearer;
import basicmod.character.MyCharacter;
import basicmod.powers.ChargeOfLightPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import static basicmod.TheLightbearer.makeID;

public class LittleLight extends BaseRelic{
    private static final String NAME = "LittleLight";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public LittleLight(){
        super(ID, NAME, MyCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

    boolean used = false;


    public void healPlayer() {
        if(!used) {
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            int healAmt = 10;
            AbstractDungeon.player.heal(healAmt, true);
            flash();
            this.grayscale = true;
            used = true;
        }
    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(AbstractDungeon.player.currentHealth - damageAmount <= AbstractDungeon.player.maxHealth/2){
            healPlayer();
        }
        return damageAmount;
    }

    @Override
    public void onVictory() {
        this.grayscale = false;
        used = false;
    }

    public AbstractRelic makeCopy() {
        return new LittleLight();
    }
}


