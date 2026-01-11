package TheLightbearer.cards.Arc;


import TheLightbearer.CustomActions.ElectroPlatingAction;
import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.ARC;

public class ElectroPlating extends BaseCard {

    public static final String ID = makeID("ElectroPlating");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1
    );
    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 0;
    private boolean freeToPlayOnce = false;

    public ElectroPlating() {
        super(ID, info, "arc");
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        tags.add(ARC);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ElectroPlatingAction(p, this.magicNumber, this.upgraded, this.freeToPlayOnce, energyOnUse));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ElectroPlating();
    }
}
