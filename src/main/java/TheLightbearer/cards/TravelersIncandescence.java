package TheLightbearer.cards;


import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TravelersIncandescence extends BaseCard {
    //Named Traveler's Might in game
    public static final String ID = makeID("TravelersIncandescence");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3
    );
    private static  final int MAGIC_NUMBER = 3;
    private static final boolean baseEthereal = true;
    private static final boolean upgEthereal = true;
    private static  final int costUpgrade = 2;
    public TravelersIncandescence() {
        super(ID, info);
        setMagic(MAGIC_NUMBER);
        setEthereal(baseEthereal,upgEthereal);
        setCostUpgrade(costUpgrade);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p, new StrengthPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p,p, new DexterityPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TravelersIncandescence();
    }
}
