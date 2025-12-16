package TheLightbearer.cards.Solar;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.powers.TripminePower;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static TheLightbearer.util.CustomTags.SOLAR;

public class TripmineGrenade extends BaseCard {

    public static final String ID = makeID("TripmineGrenade");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2
    );
    private static final int DAMAGE = 17;
    private static final int UPG_DAMAGE = 8;

    private static final int MAGIC_NUMBER = 15;
    private static final int UPG_MAGIC_NUMBER = 0;


    public TripmineGrenade() {
        super(ID, info, "solar");
        //setDamage(DAMAGE, UPG_DAMAGE);
        setExhaust(true);
        setSelfRetain(false, true);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        tags.add(SOLAR);
        //this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        //addToBot(new DamageAllEnemiesAction(p,this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(p, p, new TripminePower(p, this.magicNumber), this.magicNumber));

    }

    @Override
    public AbstractCard makeCopy() {
        return new TripmineGrenade();
    }
}
