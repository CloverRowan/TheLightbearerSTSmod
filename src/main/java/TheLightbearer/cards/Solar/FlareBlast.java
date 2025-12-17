package TheLightbearer.cards.Solar;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.SOLAR;
import static TheLightbearer.util.CustomTags.VOID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

//Called Endless Entropy in game
public class FlareBlast extends BaseCard {

    public static final String ID = makeID("FlareBlast");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int DAMAGE = 2;
    private static final int UPG_DAMAGE = 1;

    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 0;


    public FlareBlast() {
        super(ID, info, "solar");
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        tags.add(VOID);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new DrawCardAction(this.magicNumber));
    }

    public void triggerWhenDrawn() {
        addToTop(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlareBlast();
    }
}
