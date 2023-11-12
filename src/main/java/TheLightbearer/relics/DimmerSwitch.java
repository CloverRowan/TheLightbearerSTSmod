package TheLightbearer.relics;

import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.powers.ChargeOfLightPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static TheLightbearer.TheLightbearer.makeID;
import static TheLightbearer.util.CustomTags.SUPERSPELL;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class DimmerSwitch extends BaseRelic {
    private static final String NAME = "DimmerSwitch";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public DimmerSwitch() {
        super(ID, NAME, LightbearerCharacter.Enums.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }


    public void onUseCard(AbstractCard card, UseCardAction action){
        if (player != null && AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                card.type.equals(AbstractCard.CardType.ATTACK)) {
            this.counter++;
            checkCounter();
        }

        if(card.cardID.equals(makeID("CallOnLight"))) {
            counter += card.magicNumber;
            checkCounter();
        }
        if(card.cardID.equals(makeID("BlessingOfRadiance"))) {
            counter += card.magicNumber;
            checkCounter();
        }
        if(card.cardID.equals(makeID("TrappersAmbush"))) {
            counter += card.magicNumber;
            checkCounter();
        }
        if(card.cardID.equals(makeID("VoidWallGrenade"))) {
            counter += card.magicNumber;
            checkCounter();
        }
        if(card.cardID.equals(makeID("LineEmUp"))) {
            counter += 2;
            checkCounter();
        }
        if(card.cardID.equals(makeID("MonteCarlo"))) {
            counter += card.magicNumber;
            checkCounter();
        }
    }

    public void atTurnStartPostDraw() {
        this.counter++;
    }

    public AbstractRelic makeCopy() {
        return new DimmerSwitch();
    }

    private void checkCounter(){
        if (this.counter >= 10) {
            this.counter -= 10;
            flash();
            addToBot(new GainEnergyAction(1));
        }
    }
}