package TheLightbearer.cards.Void;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.brashmonkey.spriter.Player;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class BarrierOfVoid extends BaseCard {

    public static final String ID = makeID("BarrierOfVoid");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );

    private static final int MAGIC_NUMBER = 4;
    private static final int UPG_MAGIC_NUMBER = 1;

    public BarrierOfVoid() {
        super(ID, info,"void");
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setExhaust(true);
        tags.add(VOID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        this.rawDescription = (this.upgraded ? this.cardStrings.UPGRADE_DESCRIPTION: this.cardStrings.DESCRIPTION);
        this.initializeDescription();
    }

    public void applyPowers() {
        this.baseBlock = this.magicNumber * player.exhaustPile.size();
        super.applyPowers();
        this.rawDescription = (this.upgraded ? this.cardStrings.UPGRADE_DESCRIPTION : this.cardStrings.DESCRIPTION)
                + this.cardStrings.EXTENDED_DESCRIPTION[0] + this.block + this.cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = (this.upgraded ? this.cardStrings.UPGRADE_DESCRIPTION : this.cardStrings.DESCRIPTION);
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new BarrierOfVoid();
    }
}
