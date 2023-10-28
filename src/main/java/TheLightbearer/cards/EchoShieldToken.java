package TheLightbearer.cards;


import basemod.AutoAdd;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

@AutoAdd.Ignore
@AutoAdd.NotSeen
public class EchoShieldToken extends BaseCard {

    public static final String ID = makeID("EchoShieldToken");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );


    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    public EchoShieldToken() {
        super(ID, info,"void");
        setBlock(BLOCK, UPG_BLOCK);
        setEthereal(true);
        setExhaust(true);
        tags.add(VOID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        if(this.upgraded){
            AbstractCard c = new  EchoShieldToken();
            c.upgrade();
            addToBot(new MakeTempCardInHandAction( c,1));
        }
        else {
            addToBot(new MakeTempCardInHandAction(new EchoShieldToken(), 1));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EchoShieldToken();
    }
}
