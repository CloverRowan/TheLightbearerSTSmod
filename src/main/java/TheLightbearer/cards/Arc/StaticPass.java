package TheLightbearer.cards.Arc;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.ARC;

public class StaticPass extends BaseCard {

    public static final String ID = makeID("StaticPass");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 4;
    private static final int MAGIC_NUMBER = 1;




    public StaticPass() {
        super(ID, info, "arc");
        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(ARC);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p,this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        for (AbstractMonster mo: AbstractDungeon.getCurrRoom().monsters.monsters) {
           if(!mo.isDead && !mo.isDying && !mo.isDeadOrEscaped())
            addToBot(new DrawCardAction(MAGIC_NUMBER));
        }
        if(this.upgraded){
            addToBot(new DrawCardAction(MAGIC_NUMBER));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new StaticPass();
    }
}
