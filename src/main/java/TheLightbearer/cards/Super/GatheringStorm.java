package TheLightbearer.cards.Super;

import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.powers.GatheringStormPower;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.ARC;
import static TheLightbearer.util.CustomTags.SUPERSPELL;

public class GatheringStorm extends BaseCard {
    public static final String ID = makeID("GatheringStorm");


    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int DAMAGE = 22;
    private static final int UPG_DAMAGE = 8;

    private static final int MAGIC_NUMBER = 22;
    private static final int UPG_MAGIC_NUMBER = 8;

    public GatheringStorm() {
        super(ID, info, "arc"); //Pass the required information to the BaseCard constructor.
        tags.add(SUPERSPELL);
        tags.add(ARC);
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new WaitAction(0.8F));
        CardCrawlGame.sound.playV("GatheringStormCast", 16f);
        addToBot((AbstractGameAction)new WaitAction(0.5F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            addToBot(new ApplyPowerAction(mo, p, new GatheringStormPower(mo, this.magicNumber), this.magicNumber));
        }
    }
    @Override
    public void triggerOnGlowCheck() {
        glowColor = canPlay(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }
    @Override
    public AbstractCard makeCopy() { //Optional
        return new GatheringStorm();
    }
}
