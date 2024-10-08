package TheLightbearer.cards.Solar;

import TheLightbearer.CustomActions.CheckPowerStacks;
import TheLightbearer.CustomActions.TempStrengthAction;
import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static TheLightbearer.util.CustomTags.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class AcrobatsDodge extends BaseCard {
    public static final String ID = makeID("AcrobatsDodge");


    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );


    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 0;
    private String descriptionString = "Gain !B! Block. NL Gain !M! Temporary Strength. NL This card's Block is affected by Strength." ;
    private String upgradedDescriptionString = "Gain !M! Temporary Strength. NL Gain !B! Block. NL This card's Block is affected by Strength.";

    public AcrobatsDodge() {
        super(ID, info, "solar"); //Pass the required information to the BaseCard constructor.
        tags.add(SOLAR);
        setBlock(BLOCK, UPG_BLOCK); //Sets the card's Block and how much it changes when upgraded.
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.upgraded){
            addToBot(new TempStrengthAction(p,magicNumber));
            addToBot(new GainBlockAction(p, p, block));
        }else{
            addToBot(new GainBlockAction(p, p, block));
            addToBot(new TempStrengthAction(p,magicNumber));
        }

    }
    public void applyPowers(){
        this.baseBlock =(this.upgraded ? UPG_BLOCK : BLOCK) + (new CheckPowerStacks(player,"Strength")).CheckPowerStacksAction();
        super.applyPowers();
        this.rawDescription = (this.upgraded ? upgradedDescriptionString : descriptionString);
        initializeDescription();
    }

    public void onMoveToDiscard(){
        this.rawDescription = descriptionString;
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo){
        super.calculateCardDamage(mo);
        this.rawDescription = descriptionString;
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AcrobatsDodge();
    }
}
