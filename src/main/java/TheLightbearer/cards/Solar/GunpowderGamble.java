package TheLightbearer.cards.Solar;

import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.SOLAR;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class GunpowderGamble extends BaseCard {
    public static final String ID = makeID("GunpowderGamble");


    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC_NUMBER = 2;


    public GunpowderGamble() {
        super(ID, info, "solar"); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE,UPG_DAMAGE ); //Sets the card's damage and how much it changes when upgraded.
        setMagic(MAGIC_NUMBER);
        tags.add(SOLAR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; i++){
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.FIRE));
        }
        if(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && (AbstractDungeon.actionManager.cardsPlayedThisCombat
                .get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).tags.contains(SOLAR)){
            addToBot(new DamageRandomEnemyAction(new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.FIRE));
        }
    }
    @Override
    public void triggerOnGlowCheck() {
        boolean inHand = false;
        for(AbstractCard c : player.hand.group){
            if(c == this){
                inHand = true;
                break;
            }
        }
        if(inHand)
            glowColor =  AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 1 && (AbstractDungeon.actionManager.cardsPlayedThisCombat
                .get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).tags.contains(SOLAR) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GunpowderGamble();
    }
}
