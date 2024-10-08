package TheLightbearer.cards.Super;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CorpseExplosionPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static TheLightbearer.util.CustomTags.*;

public class Shadowshot extends BaseCard {

    public static final String ID = makeID("Shadowshot");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2
    );
    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 5;
    private static final int MAGIC_NUMBER = 3;


    public Shadowshot() {
        super(ID, info,"void");
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER);
        tags.add(SUPERSPELL);
        tags.add(VOID);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new WaitAction(0.8F));
        CardCrawlGame.sound.playV("ShadowshotCast", 8f);
        addToBot(new ApplyPowerAction(m, p, new CorpseExplosionPower(m),1,AbstractGameAction.AttackEffect.POISON));
        addToBot(new DamageAllEnemiesAction(p,this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false)));
        }
    }
    @Override
    public void triggerOnGlowCheck() {
        glowColor = canPlay(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }
    @Override
    public AbstractCard makeCopy() {
        return new Shadowshot();
    }
}
