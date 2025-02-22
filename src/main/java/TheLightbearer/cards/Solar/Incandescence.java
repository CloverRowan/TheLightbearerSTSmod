package TheLightbearer.cards.Solar;

import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.powers.ChargeOfLightPower;
import TheLightbearer.powers.IncandescencePower;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;

import static TheLightbearer.util.CustomTags.SOLAR;



public class Incandescence extends BaseCard {
    public static final String ID = makeID("Incandescence");


    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );


    private static final int MAGIC_NUMBER= 2;
    private static final int UPG_MAGIC_NUMBER = 0;

    private static final int SECOND_MAGIC_NUMBER = 3;
    private static final int UPG_SECOND_MAGIC_NUMBER = 2;

    public Incandescence() {
        super(ID, info, "solar"); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        tags.add(SOLAR);
        setCustomVar("INCANDESCENCEM2", SECOND_MAGIC_NUMBER, UPG_SECOND_MAGIC_NUMBER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, magicNumber));
        addToBot(new GainEnergyAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new ChargeOfLightPower(p, this.customVar("INCANDESCENCEM2"))));


        //addToBot(new ApplyPowerAction(p, p, new IncandescencePower(p,this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Incandescence();
    }
}
