package basicmod.util;

import basemod.abstracts.CustomReward;
import basicmod.TheLightbearer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;

import static basicmod.util.CustomTags.SUPERSPELL;

public class SuperReward extends CustomReward {
    public static final String ID = TheLightbearer.makeID("SuperReward");
    public static final String[] TEXT =(CardCrawlGame.languagePack.getUIString(ID)).TEXT;

    public SuperReward() {
        super(TextureLoader.getTexture(("images/ui/reward/bossCardReward.png")), TEXT[0], RewardType.CARD);

    }
    public static ArrayList<AbstractCard> getCards(){
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        while(cardsList.size() < 3){
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
                list.add(d.makeCopy());
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
        if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.BOSS_REWARD){
            AbstractDungeon.cardRewardScreen.open(this.cards,(RewardItem)this,TEXT[1] );
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.BOSS_REWARD;
        }
        return false;
    }
}
