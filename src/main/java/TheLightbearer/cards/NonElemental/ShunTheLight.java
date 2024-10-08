package TheLightbearer.cards.NonElemental;


import TheLightbearer.CustomActions.ConsumePower;
import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.NONE;


public class ShunTheLight extends BaseCard {

    public static final String ID = makeID("ShunTheLight");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.BASIC, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC_NUMBER = 4;
    private static final int UPG_MAGIC_NUMBER = 0;
    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 2;


    public ShunTheLight() {
        super(ID, info, "none");
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        setBlock(BLOCK,UPG_BLOCK);
        tags.add(NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
      if(new ConsumePower(p,"TheLightbearer:ChargeOfLightPower",magicNumber,false).ConsumePowerAction())
                    addToBot(new GainBlockAction(p, p, block));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShunTheLight();
    }
}
