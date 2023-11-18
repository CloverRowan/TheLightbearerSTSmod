package TheLightbearer.relics;

import TheLightbearer.character.LightbearerCharacter;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static TheLightbearer.TheLightbearer.makeID;

public class LittleLight extends BaseRelic{
    private static final String NAME = "LittleLight";
    public static final String ID = makeID(NAME);
    public boolean used = false;
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public LittleLight(){
        super(ID, NAME, LightbearerCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

    @Override
    public void atPreBattle() {
        used = false;
        beginLongPulse();
    }


    public void healPlayer() {
        if(!used && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            int healAmt = 10;
            AbstractDungeon.player.heal(healAmt, true);
            flash();
            this.grayscale = true;
            stopPulse();
            used = true;
        }
    }
    @Override
    public void onLoseHp(int damageAmount) {
        if(AbstractDungeon.player.currentHealth - damageAmount <= AbstractDungeon.player.maxHealth/2){
            healPlayer();
        }
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


