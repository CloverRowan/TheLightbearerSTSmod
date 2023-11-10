package TheLightbearer.cards;


import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.powers.RagingCurrentPower;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.ARC;

public class RagingCurrent extends BaseCard {

    public static final String ID = makeID("RagingCurrent");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0
    );
    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUM = 1;

    private static final int BLOCK = 2;
    private static final int UPG_BLOCK = 2;

    public RagingCurrent() {
        super(ID, info, "arc");
        //setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUM);
        tags.add(ARC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p ,p , new RagingCurrentPower(p,this.magicNumber),this.magicNumber));

    }

    @Override
    public AbstractCard makeCopy() {
        return new RagingCurrent();
    }
}
