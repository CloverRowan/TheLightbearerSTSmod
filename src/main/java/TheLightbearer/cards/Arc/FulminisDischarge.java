package TheLightbearer.cards.Arc;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.ARC;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class FulminisDischarge extends BaseCard {

    public static final String ID = makeID("FulminisDischarge");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC_NUMBER = 5;

    public FulminisDischarge() {
        super(ID, info, "arc");
        setMagic(MAGIC_NUMBER);
        setDamage(DAMAGE, UPG_DAMAGE);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        setSelfRetain(false,true);
        tags.add(ARC);
        ExhaustiveVariable.setBaseValue(this, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.drawPile.size() <= 5 && player.hasPower("Weakened")){
            //logger.info("passed logic");
            /*new ConsumePower(p,"Flex",this.magicNumber,true).ConsumePowerAction();
            //logger.info("Strength should reduce");
            for (AbstractPower FindPower : player.powers) {
                if (FindPower.ID.equals("Flex") && FindPower.amount <= 0) {
                    addToBot(new RemoveSpecificPowerAction(p,p,"Flex"));
                }
            }*/

        addToBot(new RemoveSpecificPowerAction(p,p,"Weakened"));

        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }

    @Override
    public AbstractCard makeCopy() {
        return new FulminisDischarge();
    }
}
