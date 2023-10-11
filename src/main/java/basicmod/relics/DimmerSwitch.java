package basicmod.relics;

import basicmod.TheLightbearer;
import basicmod.character.MyCharacter;
import basicmod.powers.ChargeOfLightPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;

import static basicmod.TheLightbearer.makeID;

public class DimmerSwitch extends BaseRelic {
    private static final String NAME = "DimmerSwitch";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public DimmerSwitch() {
        super(ID, NAME, MyCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new DimmerSwitch();
    }
}