package basicmod.relics;

import basicmod.TheLightbearer;
import basicmod.character.MyCharacter;
import basicmod.powers.ChargeOfLightPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basicmod.TheLightbearer.makeID;

public class LittleLight extends BaseRelic{
    private static final String NAME = "LittleLight";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public LittleLight(){
        super(ID, NAME, MyCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void atTurnStart(){
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new ChargeOfLightPower(AbstractDungeon.player, 1)));


    }


}
