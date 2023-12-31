package TheLightbearer.cards;

import basemod.AutoAdd;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static TheLightbearer.util.CustomTags.VOID;
@AutoAdd.Ignore
@AutoAdd.NotSeen
public class ChooseVoid extends BaseCard {

    public static final String ID = makeID("ChooseVoid");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.STATUS, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.NONE, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );


    public ChooseVoid() {
        super(ID, info,"void");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public AbstractCard pickVoidCard() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.tags.contains(VOID)) {
                list.add(c.makeCopy());
            }
        }
        return list.get(AbstractDungeon.cardRng.random(list.size() - 1));
    }
    public void onChoseThisOption () {
        AbstractCard c = pickVoidCard();
        addToBot(new MakeTempCardInHandAction(c));
        c.setCostForTurn(0);

    }
    @Override
    public AbstractCard makeCopy() {
        return new ChooseVoid();
    }
}

