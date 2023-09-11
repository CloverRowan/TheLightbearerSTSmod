package basicmod.cards;


import basicmod.character.MyCharacter;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TravellersIncandescence extends BaseCard {

    public static final String ID = makeID("TravellersIncandescence");
    private static final CardStats info = new CardStats(
            MyCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3
    );
    private static  final int MAGIC_NUMBER = 3;
    private static final boolean baseEthereal = true;
    private static final boolean upgEthereal = true;
    private static  final int costUpgrade = 2;
    public TravellersIncandescence() {
        super(ID, info);
        setMagic(MAGIC_NUMBER);
        setEthereal(baseEthereal,upgEthereal);
        setCostUpgrade(costUpgrade);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ApplyPowerAction(p,p, (AbstractPower)new StrengthPower(p, this.magicNumber)));
        addToBot((AbstractGameAction)new ApplyPowerAction(p,p, (AbstractPower)new DexterityPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TravellersIncandescence();
    }
}
