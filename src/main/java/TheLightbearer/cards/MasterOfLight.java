package TheLightbearer.cards;

import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class MasterOfLight extends BaseCard {
    public static final String ID = makeID("MasterOfLight");


    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 1;

    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 1;

    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 0;

    public MasterOfLight() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setBlock(BLOCK, UPG_BLOCK); //Sets the card's Block and how much it changes when upgraded.
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        tags.add(SOLAR);
        tags.add(VOID);
        tags.add(ARC);
        setExhaust(true);
        this.rawDescription = "Deal !D! damage. NL Gain !B! Block. NL Draw !M! card" + (this.magicNumber > 1 ? "s." : ".")
                + " NL Improved for each *Solar-Infused, *Arc-Infused, and *Void-Infused card in your hand.";
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        countCards();
        calculateCardDamage(null);
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new DamageAction(m, new DamageInfo(p,this.damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new DrawCardAction(this.magicNumber));
    }

        //this.rawDescription = "Deal " + this.damage + " damage. NL Gain " + this.block + " Block. NL Draw "+ this.magicNumber + " card" + (this.magicNumber > 1 ? "s." : ".") ;



    public void applyPowers(){
        countCards();
        super.applyPowers();
        this.rawDescription = "Deal !D! damage. NL Gain !B! Block. NL Draw !M! card" + (this.magicNumber > 1 ? "s." : ".")
                + " NL Improved for each *Solar-Infused, *Arc-Infused, and *Void-Infused card in your hand.";
        initializeDescription();
    }

    public void onMoveToDiscard(){
        this.rawDescription = "Deal !D! damage. NL Gain !B! Block. NL Draw !M! card" + (this.magicNumber > 1 ? "s." : ".")
                + " NL Improved for each *Solar-Infused, *Arc-Infused, and *Void-Infused card in your hand.";
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo){
        super.calculateCardDamage(mo);
        this.rawDescription = "Deal !D! damage. NL Gain !B! Block. NL Draw !M! card" + (this.magicNumber > 1 ? "s." : ".")
                + " NL Improved for each *Solar-Infused, *Arc-Infused, and *Void-Infused card in your hand.";
        initializeDescription();
    }

    private void countCards(){
        int solarCount = 0;
        int voidCount = 0;
        int arcCount = 0;
        for(AbstractCard c : player.hand.group){
            if(c.tags.contains(SOLAR)){
                solarCount++;
            }
            if(c.tags.contains(VOID)){
                voidCount++;
            }
            if(c.tags.contains(ARC)){
                arcCount++;
            }
        }
        /*for(AbstractCard c : player.discardPile.group){
            if(c.tags.contains(SOLAR)){
                solarCount++;
            }
            if(c.tags.contains(VOID)){
                voidCount++;
            }
            if(c.tags.contains(ARC)){
                arcCount++;
            }
        }
        for(AbstractCard c : player.drawPile.group){
            if(c.tags.contains(SOLAR)){
                solarCount++;
            }
            if(c.tags.contains(VOID)){
                voidCount++;
            }
            if(c.tags.contains(ARC)){
                arcCount++;
            }
        }*/
        this.baseDamage = solarCount * (this.upgraded ? 4 : 3);
        this.baseBlock = voidCount * (this.upgraded ? 4 : 3);
        this.baseMagicNumber = this.magicNumber = arcCount;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MasterOfLight();
    }
}
