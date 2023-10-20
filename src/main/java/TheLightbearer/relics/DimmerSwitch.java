package TheLightbearer.relics;

import TheLightbearer.character.LightbearerCharacter;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static TheLightbearer.TheLightbearer.makeID;

public class DimmerSwitch extends BaseRelic {
    private static final String NAME = "DimmerSwitch";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public DimmerSwitch() {
        super(ID, NAME, LightbearerCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new DimmerSwitch();
    }
}