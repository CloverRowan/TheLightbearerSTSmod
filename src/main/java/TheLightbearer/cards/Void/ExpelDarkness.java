package TheLightbearer.cards.Void;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.powers.MonsterFrailPower;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

import static TheLightbearer.util.CustomTags.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ExpelDarkness extends BaseCard {

    public static final String ID = makeID("ExpelDarkness");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );

    public ExpelDarkness() {
        super(ID, info,"void");
        setSelfRetain(true);
        setExhaust(true, false);
        tags.add(VOID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractPower> powers = p.powers;
        for (int i = 0; i < powers.size(); i++) {
            if(powers.get(i).ID.equals("Vulnerable")){
                addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, powers.get(i).amount, false)));
                addToBot(new RemoveSpecificPowerAction(p, p, powers.get(i)));
            }else if(powers.get(i).ID.equals("Weakened")){
                addToBot(new ApplyPowerAction(m, p, new WeakPower(m, powers.get(i).amount, false)));
                addToBot(new RemoveSpecificPowerAction(p, p, powers.get(i)));
            }else if(powers.get(i).ID.equals("Frail")){
            addToBot(new ApplyPowerAction(m, p, new MonsterFrailPower(m, powers.get(i).amount)));
            addToBot(new RemoveSpecificPowerAction(p, p, powers.get(i)));
        }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExpelDarkness();
    }
}
