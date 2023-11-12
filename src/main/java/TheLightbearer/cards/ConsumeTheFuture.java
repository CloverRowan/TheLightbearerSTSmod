package TheLightbearer.cards;


import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RandomizeHandCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.VOID;

public class ConsumeTheFuture extends BaseCard {

    public static final String ID = makeID("ConsumeTheFuture");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0
    );

    public ConsumeTheFuture() {
        super(ID, info,"void");
        setExhaust(true);
        setSelfRetain(false, true);
        tags.add(VOID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i <p.drawPile.size(); i++){
            AbstractCard c = p.drawPile.group.get(i);
            addToBot(new ExhaustSpecificCardAction(c,p.drawPile));
            addToBot(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomCard()));
        }
        addToBot(new RandomizeHandCostAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new ConsumeTheFuture();
    }
}
