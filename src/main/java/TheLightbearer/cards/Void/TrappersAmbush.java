package TheLightbearer.cards.Void;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.powers.ChargeOfLightPower;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static TheLightbearer.util.CustomTags.VOID;

public class TrappersAmbush extends BaseCard {

    public static final String ID = makeID("TrappersAmbush");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2
    );
    private static final int MAGIC_NUMBER= 3;
    private static final int MAGIC_NUMBER_UPGRADE= 2;

    private static final boolean baseInnate = false;
    private static final boolean upgInnate = true;

    public TrappersAmbush() {
        super(ID, info,"void");
        setMagic(MAGIC_NUMBER, MAGIC_NUMBER_UPGRADE);
        tags.add(VOID);
        setInnate(baseInnate,upgInnate);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m,p, new  WeakPower(m, this.magicNumber, false)));
        addToBot(new ApplyPowerAction(p,p, new ChargeOfLightPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TrappersAmbush();
    }
}
