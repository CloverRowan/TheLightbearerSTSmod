package TheLightbearer.cards.Solar;

import TheLightbearer.CustomActions.CheckPowerStacks;
import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.powers.WhisperPower;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static TheLightbearer.TheLightbearer.makeID;
import static TheLightbearer.util.CustomTags.SOLAR;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static TheLightbearer.TheLightbearer.logger;

public class WhisperOfTheWorm extends BaseCard {
    public static final String ID = makeID("WhisperOfTheWorm");


    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC_NUMBER = 3;
    private static final int UPG_MAGIC_NUMBER = 0;

    private int counter;

    public WhisperOfTheWorm() {
        super(ID, info, "solar"); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        tags.add(SOLAR);
        //this.counter = 3;
        this.rawDescription = this.cardStrings.DESCRIPTION; // + cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
        this.setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);

    }

    @Override
    public float getTitleFontSize() {
        return 18;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(p, p, new WhisperPower(p, 1)));
        /*
        //Update every whisper's counter
        for(AbstractCard c : getSame()){
            c.baseMagicNumber--;
            if(c.baseMagicNumber == 0){
                if(c == this)
                    addToBot(new GainEnergyAction(2));
                c.baseMagicNumber = 3;
                c.applyPowers();
            }
            c.applyPowers();
        }
        updateAllDescriptions();
        */
    }
    /*
    private void updateAllDescriptions(){
        //Sets the description for every whisper based on its own counter
        for(AbstractCard c : getSame()){
            switch(c.baseMagicNumber){
                case 3:
                    c.rawDescription = this.cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.EXTENDED_DESCRIPTION[1];
                    break;
                case 2:
                    c.rawDescription = this.cardStrings.DESCRIPTION + " NL (2 " + cardStrings.EXTENDED_DESCRIPTION[1];
                    break;
                case 1:
                    c.rawDescription = this.cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[2];
                    break;
                default:
                    logger.info("Something went wrong with the counter in WhisperOfTheWorm");
            }
            c.initializeDescription();
        }
    }
    private ArrayList<AbstractCard> getSame(){
        ArrayList<AbstractCard> group = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.cardID.equals(this.cardID)) {
                group.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(this.cardID)) {
                group.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID.equals(this.cardID)) {
                group.add(c);
            }
        }
        return group;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.rawDescription = this.cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }
    */

    @Override
    public void triggerOnGlowCheck() {
        boolean inHand = false;
        for(AbstractCard c : player.hand.group){
            if(c == this){
                inHand = true;
                break;
            }
        }
        if(inHand){
            CheckPowerStacks c = new CheckPowerStacks(player, makeID("WhisperPower"));
            glowColor = c.CheckPowerStacksAction() == 2 ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
        }

    }
    @Override
    public AbstractCard makeCopy() { //Optional
        return new WhisperOfTheWorm();
    }
}
