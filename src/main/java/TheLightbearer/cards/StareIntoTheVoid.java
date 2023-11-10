package TheLightbearer.cards;


import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.LosePercentHPAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.VOID;


public class StareIntoTheVoid extends BaseCard {

    public static final String ID = makeID("StareIntoTheVoid");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );

    private static int MAGIC_NUMBER = 10;
    private static int UPG_MAGIC_NUMBER = -3;

    public StareIntoTheVoid() {
        super(ID, info,"void");
        tags.add(VOID);
        setEthereal(true);
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        this.rawDescription = this.cardStrings.DESCRIPTION;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LosePercentHPAction(this.magicNumber));
        addToBot(new ExhumeAction(false));
    }

    //Everything for dynamic description
    private int calcHPLoss(){
        float percentConversion = (float)this.magicNumber / 100.0F;
        return (int)((float) AbstractDungeon.player.currentHealth * percentConversion);
    }

    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = this.cardStrings.DESCRIPTION + this.cardStrings.EXTENDED_DESCRIPTION[0] +  calcHPLoss() + this.cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = this.cardStrings.DESCRIPTION + this.cardStrings.EXTENDED_DESCRIPTION[0] +  calcHPLoss() + this.cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = this.cardStrings.DESCRIPTION + this.cardStrings.EXTENDED_DESCRIPTION[0] +  calcHPLoss() + this.cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void upgrade(){
        super.upgrade();
        this.rawDescription = this.cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new StareIntoTheVoid();
    }
}
