package TheLightbearer.cards.Solar;

import TheLightbearer.CustomActions.ComfortingFlamesAction;
import TheLightbearer.CustomActions.HeavyKnifeAction;
import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.SOLAR;

public class ComfortingFlames extends BaseCard {
    public static final String ID = makeID("ComfortingFlames");


    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 2;
    private static final int DAMAGE = 1;

    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 0;

    public ComfortingFlames() {
        super(ID, info, "solar"); //Pass the required information to the BaseCard constructor.
        setBlock(BLOCK, UPG_BLOCK); //Sets the card's Block and how much it changes when upgraded.
        setDamage(DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        tags.add(SOLAR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ComfortingFlamesAction(p, m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), this.block));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ComfortingFlames();
    }
}
