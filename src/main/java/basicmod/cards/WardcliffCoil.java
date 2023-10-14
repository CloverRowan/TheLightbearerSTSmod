package basicmod.cards;


import basicmod.CustomActions.WardcliffCoilAction;
import basicmod.character.MyCharacter;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basicmod.util.CustomTags.ARC;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class WardcliffCoil extends BaseCard {

    public static final String ID = makeID("WardcliffCoil");
    private static final CardStats info = new CardStats(
            MyCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            -1
    );
    private static final int DAMAGE = 2;
    private static final int UPG_DAMAGE = 2;
    int arcCount = 0;


    public WardcliffCoil() {
        super(ID, info, "arc");
        setDamage(DAMAGE, UPG_DAMAGE);
        this.isMultiDamage = true;
        tags.add(ARC);
        this.arcCount = 0;
        this.rawDescription = this.cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new WardcliffCoilAction(p, this.multiDamage, this.damageTypeForTurn, this.freeToPlayOnce, this.upgraded, this.energyOnUse));
    }
    private void countCards() {
        for (AbstractCard c : player.hand.group) {
            if (c.tags.contains(ARC)) {
                arcCount++;
            }
        }
        for (AbstractCard c : player.discardPile.group) {
            if (c.tags.contains(ARC)) {
                arcCount++;
            }
        }
        for (AbstractCard c : player.drawPile.group) {
            if (c.tags.contains(ARC)) {
                arcCount++;
            }
        }

    }

   public void applyPowers() {
        countCards();
        setMagic(arcCount);
       super.applyPowers();
       this.rawDescription = this.cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = this.cardStrings.DESCRIPTION;
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo) {
    super.calculateCardDamage(mo);
        this.rawDescription = this.cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
        @Override
        public AbstractCard makeCopy() {
            return new WardcliffCoil();
        }
}
