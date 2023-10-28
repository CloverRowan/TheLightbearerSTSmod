package TheLightbearer.cards;

import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.SOLAR;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class WhisperOfTheWorm extends BaseCard {
    public static final String ID = makeID("WhisperOfTheWorm");


    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 6;

    private int counter;

    public WhisperOfTheWorm() {
        super(ID, info, "solar"); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        tags.add(SOLAR);
        this.counter = 3;
        this.rawDescription = this.cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public float getTitleFontSize() {
        return 18;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.counter--;
        this.rawDescription = cardStrings.DESCRIPTION;
        switch(this.counter){
            case 2:
                this.rawDescription = this.rawDescription + " NL (2 " + cardStrings.EXTENDED_DESCRIPTION[1];
                break;
            case 1:
                this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[2];
                break;
            case 0:
                addToBot(new GainEnergyAction(4));
                counter = 3;
                this.rawDescription = this.cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.EXTENDED_DESCRIPTION[1];
                break;
        }
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.rawDescription = this.cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void triggerOnGlowCheck() {
        boolean inHand = false;
        for(AbstractCard c : player.hand.group){
            if(c == this){
                inHand = true;
                break;
            }
        }
        if(inHand)
            glowColor = counter == 1 ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }
    @Override
    public AbstractCard makeCopy() { //Optional
        return new WhisperOfTheWorm();
    }
}
