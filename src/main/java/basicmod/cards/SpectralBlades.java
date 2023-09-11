package basicmod.cards;


import basicmod.character.MyCharacter;
import basicmod.powers.SpectralBladesPower;
import basicmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static basicmod.util.CustomTags.SUPERSPELL;
import static basicmod.util.CustomTags.VOID;

public class SpectralBlades extends BaseCard {

    public static final String ID = makeID("SpectralBlades");
    private static final CardStats info = new CardStats(
            MyCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3
    );

    private static int MAGIC_NUMBER = 2;
    private static int UPG_MAGIC_NUMBER = 3;

    public SpectralBlades() {
        super(ID, info);
        tags.add(SUPERSPELL);
        tags.add(VOID);
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new SpectralBladesPower(p,this.magicNumber),1));
        addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,3),1));

    }

    @Override
    public AbstractCard makeCopy() {
        return new SpectralBlades();
    }
}