package TheLightbearer.cards.NonElemental;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static TheLightbearer.util.CustomTags.NONE;


public class Thorn extends BaseCard {

    public static final String ID = makeID("Thorn");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 // Card Cost
    );
    private static final int MAGIC_NUMBER = 2;
    private static final int UPG_MAGIC_NUMBER = 1;
    private static final int DAMAGE = 5;
    private static final int POISON = 2;
    public Thorn() {
        super(ID, info, "none");
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        setDamage(DAMAGE);
        tags.add(NONE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i;
        if(this.upgraded){
            CardCrawlGame.sound.playV("ThornTriple", 4f);
        }else{
            CardCrawlGame.sound.playV("ThornDouble", 4f);
        }
        for (i=0; i<this.magicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON));
            addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, POISON)));
            addToBot(new WaitAction(0.5F));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Thorn();
    }
}
