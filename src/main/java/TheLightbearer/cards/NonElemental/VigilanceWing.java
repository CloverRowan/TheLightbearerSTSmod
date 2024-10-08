package TheLightbearer.cards.NonElemental;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.NONE;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class VigilanceWing extends BaseCard {

    public static final String ID = makeID("VigilanceWing");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;


    public VigilanceWing() {
        super(ID, info, "none");
        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(player.hand.size() <= 4) {
            addToBot(new DamageAction(m, new DamageInfo(p,this.damage,DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
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
            glowColor = player.hand.size() <= 4 ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public AbstractCard makeCopy() {
        return new VigilanceWing();
    }
}
