package TheLightbearer.relics;

import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.powers.ChargeOfLightPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

import static TheLightbearer.TheLightbearer.logger;
import static TheLightbearer.TheLightbearer.makeID;
import static TheLightbearer.util.CustomTags.SUPERSPELL;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class TheTraveler extends BaseRelic {
    private static final String NAME = "TheTraveler";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    public boolean playForFree;
    ArrayList <Boolean> freeCharge = new ArrayList<>();

    public TheTraveler() {
        super(ID, NAME, LightbearerCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }


    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

    public void atTurnStartPostDraw() {
        addToBot(new ApplyPowerAction(player, player,
                new ChargeOfLightPower(player, 1)));
    }
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (player != null && AbstractDungeon.currMapNode != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT &&
                c.type.equals(AbstractCard.CardType.ATTACK)) {

            addToTop(new ApplyPowerAction(player, player,
                    new ChargeOfLightPower(player, 1)));
        }
        if(player.hasPower("DuplicationPower")){
            for (int i = 0; i<=1; i++) {
                freeCharge.add(playForFree);
                freeCharge.set(i, true);
            }
        }
    }
    public boolean canPlay(AbstractCard card) {
        if (card.tags.contains(SUPERSPELL)) {
            for (AbstractPower p : player.powers) {
                if (p.ID.equals(makeID("ChargeOfLightPower"))) {
                    if(p.amount >= 10 || (!freeCharge.isEmpty() && freeCharge.get(0) == true)){
                        if (!freeCharge.isEmpty()){
                            freeCharge.remove(0);
                            //logger.info("got to the array reduce");
                            return super.canPlay(card);
                        }
                        return super.canPlay(card);

                    }
                    ArrayList<AbstractCard> cards = AbstractDungeon.actionManager.cardsPlayedThisCombat;

                    for(AbstractRelic r : player.relics){
                        //logger.info(r.toString());
                        if(r.relicId.equals(Necronomicon.ID) && card.cost >= 2 &&
                                !cards.isEmpty() && cards.get(cards.size()-1).tags.contains(SUPERSPELL)){
                            if(cards.size() >= 2 && cards.get(cards.size()-2).tags.contains(SUPERSPELL)){
                                return false;
                            }
                            return super.canPlay(card);
                        }
                    }
                    return false;
                }
            }
        }
        return super.canPlay(card);
    }
}