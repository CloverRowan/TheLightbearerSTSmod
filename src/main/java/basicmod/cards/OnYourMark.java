package basicmod.cards;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.abstracts.DynamicVariable;
import basicmod.TheLightbearer;
import basicmod.character.MyCharacter;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static basicmod.util.GeneralUtils.removePrefix;
import static basicmod.util.TextureLoader.getCardTextureString;

public class OnYourMark extends BaseCard {
    public static final String ID = makeID("OnYourMark");


    private static final CardStats info = new CardStats(
            MyCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC_NUMBER = 4;
    private static final int UPG_MAGIC_NUMBER = 2;

    public OnYourMark() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int burnCount = 0;
        for(AbstractCard c : p.drawPile.group){
            if(c.name.equals("Burn"))
                burnCount++;
        }
        for(AbstractCard c : p.hand.group){
            if(c.name.equals("Burn"))
                burnCount++;
        }
        for(AbstractCard c : p.discardPile.group){
            if(c.name.equals("Burn"))
                burnCount++;
        }
        addToBot(new ApplyPowerAction(p,p,new VigorPower(p,this.magicNumber*burnCount), this.magicNumber*burnCount));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new OnYourMark();
    }
}
