package basicmod.relics;

import basicmod.character.MyCharacter;
import basicmod.powers.ChargeOfLightPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static basicmod.TheLightbearer.makeID;

public class TheTraveler extends BaseRelic{
    private static final String NAME = "TheTraveler";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public TheTraveler(){
        super(ID, NAME, MyCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }


    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

    public void atTurnStartPostDraw(){
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new ChargeOfLightPower(AbstractDungeon.player, 1)));
    }
/*
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        //Make attacks that aren't supers not generate charges?

        if(AbstractDungeon.player != null && AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                info.type == DamageInfo.DamageType.NORMAL){

             addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new ChargeOfLightPower(AbstractDungeon.player, 1)));
        }
    }
*/
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if(AbstractDungeon.player != null && AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                c.type.equals(AbstractCard.CardType.ATTACK)){

            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new ChargeOfLightPower(AbstractDungeon.player, 1)));
        }


    }



}