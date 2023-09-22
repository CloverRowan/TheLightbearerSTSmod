package basicmod.cards;


import basemod.AutoAdd;
import basicmod.character.MyCharacter;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
@AutoAdd.Ignore
public class CompoundingExhaustion extends BaseCard {

    public static final String ID = makeID("CompoundingExhaustion");
    private static final CardStats info = new CardStats(
            MyCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 4;

    private static final int MAGIC_NUMBER = 1;


    public CompoundingExhaustion() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
    //todo fix the updating card numbers no working
    @Override
    public void triggerOnExhaust(){
    AbstractCard copyOfThis = this.makeCopy();
    addToBot(new AbstractGameAction() {
        @Override
        public void update() {

            copyOfThis.cost =  copyOfThis.cost + MAGIC_NUMBER;
            copyOfThis.damage = copyOfThis.baseDamage = (baseDamage * cost);
            this.isDone = true;
        }
    });

        addToBot(new MakeTempCardInHandAction(copyOfThis));
    }
    @Override
    public AbstractCard makeCopy() {
        return new CompoundingExhaustion();
    }
}
