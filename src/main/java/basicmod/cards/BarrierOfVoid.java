package basicmod.cards;


import basicmod.character.MyCharacter;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basicmod.util.CustomTags.*;

public class BarrierOfVoid extends BaseCard {

    public static final String ID = makeID("BarrierOfVoid");
    private static final CardStats info = new CardStats(
            MyCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );

    private static final int BLOCK = 2;
    private static final int UPG_BLOCK = 3;

    public BarrierOfVoid() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        setExhaust(true);
        tags.add(VOID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    for(int i = 0; i< p.exhaustPile.size(); i++ ){
        addToBot(new GainBlockAction(p,block));
    }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BarrierOfVoid();
    }
}
