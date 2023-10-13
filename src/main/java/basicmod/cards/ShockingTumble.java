package basicmod.cards;


import basicmod.character.MyCharacter;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basicmod.util.CustomTags.ARC;
import static basicmod.util.CustomTags.SOLAR;

public class ShockingTumble extends BaseCard {

    public static final String ID = makeID("ShockingTumble");
    private static final CardStats info = new CardStats(
            MyCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int MAGIC_NUMBER = 0;
    private static final int UPG_MAGIC_NUMBER = 0;

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 2;

    public ShockingTumble() {
        super(ID, info, "arc");
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        setBlock(BLOCK, UPG_BLOCK);
        tags.add(ARC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        if(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && (AbstractDungeon.actionManager.cardsPlayedThisCombat
                .get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).type.equals(CardType.ATTACK)) {
            addToBot(new GainBlockAction(p, p, block));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        if(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 1 && (AbstractDungeon.actionManager.cardsPlayedThisCombat
                .get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type.equals(CardType.ATTACK)) {
            glowColor = GOLD_BORDER_GLOW_COLOR;
        }
        else {
            glowColor = BLUE_BORDER_GLOW_COLOR;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ShockingTumble();
    }
}
