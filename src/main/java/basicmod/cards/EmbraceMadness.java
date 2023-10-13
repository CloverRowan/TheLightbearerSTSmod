package basicmod.cards;


import basicmod.character.MyCharacter;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static basicmod.util.CustomTags.*;

public class EmbraceMadness extends BaseCard {

    public static final String ID = makeID("EmbraceMadness");
    private static final CardStats info = new CardStats(
            MyCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2
    );

    private static final int BLOCK = 13;
    private static final int UPG_BLOCK = 4;

    private static int MAGIC_NUMBER = 2;
    private static int UPG_MAGIC_NUMBER = 1;

    public EmbraceMadness() {
        super(ID, info,"void");
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        tags.add(VOID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        if (m != null && m.getIntentBaseDmg() >= 0) {
        addToBot(new ApplyPowerAction(m,p, new VulnerablePower(p, this.magicNumber, false)));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new EmbraceMadness();
    }
}
