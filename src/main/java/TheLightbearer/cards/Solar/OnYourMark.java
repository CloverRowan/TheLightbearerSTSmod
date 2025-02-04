package TheLightbearer.cards.Solar;

import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static TheLightbearer.util.CustomTags.SOLAR;

public class OnYourMark extends BaseCard {
    public static final String ID = makeID("OnYourMark");


    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 0;

    public OnYourMark() {
        super(ID, info, "solar"); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        tags.add(SOLAR);
        this.rawDescription = this.cardStrings.DESCRIPTION + this.cardStrings.EXTENDED_DESCRIPTION[0] + 0 + this.cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
        setCustomVar("ONYOURMARKCOUNT",0);
        setExhaust(true,false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int solarCount = countSolar();
        if(solarCount > 0){
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p,this.magicNumber*solarCount), this.magicNumber*solarCount));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p,this.magicNumber*solarCount), this.magicNumber*solarCount));
            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, this.magicNumber*solarCount), this.magicNumber*solarCount));
        }

    }

    private int countSolar(){
        int count = 0;
        for(AbstractCard c : AbstractDungeon.player.drawPile.group){
            if(c.tags.contains(SOLAR))
                count++;
        }
        for(AbstractCard c : AbstractDungeon.player.hand.group){
            if(c.tags.contains(SOLAR))
                count++;
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group){
            if(c.tags.contains(SOLAR))
                count++;
        }
        setCustomVar("ONYOURMARKCOUNT", count);
        return count;
    }

    public void applyPowers() {
        super.applyPowers();
        updateDescription();
    }

    public void onMoveToDiscard() {
        updateDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        updateDescription();
    }

    @Override
    public void upgrade() {
        super.upgrade();
        updateDescription();
    }


    private void updateDescription(){
        if(!this.upgraded){
            this.rawDescription = this.cardStrings.DESCRIPTION + this.cardStrings.EXTENDED_DESCRIPTION[0] + countSolar() + this.cardStrings.EXTENDED_DESCRIPTION[1];
        }else{
            this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION + this.cardStrings.EXTENDED_DESCRIPTION[0] + countSolar() + this.cardStrings.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new OnYourMark();
    }
}
