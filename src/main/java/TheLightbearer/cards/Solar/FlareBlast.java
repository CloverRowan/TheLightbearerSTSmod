package TheLightbearer.cards.Solar;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.SOLAR;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class FlareBlast extends BaseCard {

    public static final String ID = makeID("FlareBlast");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1
    );
    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 0;


    public FlareBlast() {
        super(ID, info, "solar");
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER, UPG_MAGIC_NUMBER);
        tags.add(SOLAR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p,this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)) ;
        int aliveMonsters = 0;
        for(AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!monster.isDead)
                aliveMonsters++;
        }


        if(!AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
            addToBot(new AddTemporaryHPAction(AbstractDungeon.player,AbstractDungeon.player, this.magicNumber * aliveMonsters));
        }

        /*addToBot(new DamageAllEnemiesAction(p,this.damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.LIGHTNING)) ;
        for (int i = 0; i < player.hand.size(); i++) {
            AbstractCard c = player.hand.group.get(i);
            if(c.cardID == "Burn" ){
                addToBot(new DiscardSpecificCardAction(c));
                break;
            }

        }
        */
    }

    @Override
    public AbstractCard makeCopy() {
        return new FlareBlast();
    }
}
