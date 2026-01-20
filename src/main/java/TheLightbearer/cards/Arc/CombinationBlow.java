package TheLightbearer.cards.Arc;


import TheLightbearer.CustomActions.CombinationBlowAction;
import TheLightbearer.CustomActions.HeavyKnifeAction;
import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static TheLightbearer.util.CustomTags.ARC;

public class CombinationBlow extends BaseCard {

    public static final String ID = makeID("CombinationBlow");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 1;

    //private int hits = 1;

    public CombinationBlow() {
        super(ID, info, "arc");
        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(ARC);
        this.misc = 1;
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; i++) {
            addToBot(new CombinationBlowAction(p, m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), this.magicNumber, this.uuid));
            addToBot(new WaitAction(0.05f));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CombinationBlow();
    }

    @Override
    public AbstractCard makeSameInstanceOf(){
        AbstractCard card = makeStatEquivalentCopy();
        card.uuid = this.uuid;
        card.misc = this.misc;
        card.magicNumber = card.baseMagicNumber = this.baseMagicNumber;
        return card;
    }

    public void applyPowers(){
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.baseMagicNumber;
        super.applyPowers();
        initializeDescription();
    }

    public void triggerAtStartOfTurn() {
        //applyPowers();
    }

    public void increaseHits(){
        //this.hits += 1;
    }

}
