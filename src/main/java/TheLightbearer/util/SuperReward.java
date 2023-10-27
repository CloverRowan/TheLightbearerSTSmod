package TheLightbearer.util;

import basemod.abstracts.CustomReward;
import TheLightbearer.TheLightbearer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.BustedCrown;

import java.util.ArrayList;

import static TheLightbearer.TheLightbearer.makeID;
import static TheLightbearer.util.CustomTags.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class SuperReward extends CustomReward {
    public static final String ID = makeID("SuperReward");
    public static final String[] TEXT =(CardCrawlGame.languagePack.getUIString(ID)).TEXT;

    public SuperReward() {
        super(TextureLoader.getTexture(("images/ui/reward/bossCardReward.png")), TEXT[0], RewardType.CARD);

    }
    public static ArrayList<AbstractCard> getCards(){
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        int numOfRewards = 3;
        if(player.hasRelic("Busted Crown")){
            numOfRewards = 1;
        }
        if(player.hasRelic("Question Card")){
            numOfRewards = 4;
        }
        if(player.hasRelic("Busted Crown") && player.hasRelic("Question Card")){
            numOfRewards = 2;

        }


        while(cardsList.size() < numOfRewards){
            AbstractCard d = getSuperCards();
            if(!cardsListDuplicate(cardsList, d)){
                cardsList.add(d);
            }
        }
        return cardsList;
    }

    public void generate_reward_cards() {
        this.cards.clear();
        this.cards.addAll(getCards());
    }
    public static AbstractCard getSuperCards(){
        ArrayList<AbstractCard> list = new ArrayList<>();
        for(AbstractCard d : CardLibrary.getAllCards()){
            if(d.tags.contains(SUPERSPELL)){
                AbstractCard u = d.makeCopy();
                if(d.tags.contains(VOID) && player.hasRelic(makeID("OrpheusRig"))){
                    u.upgrade();
                }
                if(d.tags.contains(SOLAR) && player.hasRelic(makeID("ShardsOfGalanor"))){
                    u.upgrade();
                }
                if(d.tags.contains(ARC) && player.hasRelic(makeID("RaidenFlux"))){
                    u.upgrade();
                }
                if(d.type.equals(AbstractCard.CardType.ATTACK) && player.hasRelic("Molten Egg 2")){
                    u.upgrade();
                }
                if(d.type.equals(AbstractCard.CardType.SKILL) && player.hasRelic(makeID("Toxic Egg 2"))){
                    u.upgrade();
                }
                if(d.type.equals(AbstractCard.CardType.POWER) && player.hasRelic(makeID("Frozen Egg 2"))){
                    u.upgrade();
                }


                list.add(u);
            }
        }
        return list.get(AbstractDungeon.cardRng.random(list.size()-1));
    }

    public static boolean cardsListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card){
        for(AbstractCard alreadyHave : cardsList){
            if(alreadyHave.cardID.equals(card.cardID)){
                return true;
            }
        }
        return false;
    }

    public boolean claimReward(){
        if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD){
            AbstractDungeon.cardRewardScreen.open(this.cards,this,TEXT[1] );
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }
}
