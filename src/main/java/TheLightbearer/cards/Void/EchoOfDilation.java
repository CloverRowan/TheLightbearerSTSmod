package TheLightbearer.cards.Void;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;

import static TheLightbearer.util.CustomTags.VOID;

public class EchoOfDilation extends BaseCard {

    public static final String ID = makeID("EchoOfDilation");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2
    );
    private static final int DAMAGE = 5;
    private static final int costUpgrade = 1;
    private static final int MAGIC_NUMBER = 1;
    public EchoOfDilation() {
        super(ID, info,"void");
       setEthereal(true);
       //setDamage(DAMAGE);
       setCostUpgrade(costUpgrade);
       setMagic(MAGIC_NUMBER);
       setExhaust(true);
       tags.add(VOID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new SlowPower(m,1)));
        //addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EchoOfDilation();
    }
}
