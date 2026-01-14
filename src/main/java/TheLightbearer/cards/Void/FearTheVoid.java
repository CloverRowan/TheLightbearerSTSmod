package TheLightbearer.cards.Void;


import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static TheLightbearer.util.CustomTags.*;

public class FearTheVoid extends BaseCard {

    public static final String ID = makeID("FearTheVoid");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2
    );
    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 5;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPG_MAGIC_NUMBER = 0;



    public FearTheVoid() {
        super(ID, info,"void");
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER,UPG_MAGIC_NUMBER);
        tags.add(VOID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        int voidCount = countVoid();
        if(voidCount > 0){
            addToBot(new ScryAction(voidCount));
        }
        this.rawDescription = (this.upgraded ? this.cardStrings.UPGRADE_DESCRIPTION: this.cardStrings.DESCRIPTION);
        this.initializeDescription();
    }

    private int countVoid(){
        int count = 0;
        if(AbstractDungeon.isPlayerInDungeon()) {
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.tags.contains(VOID))
                    count++;
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.tags.contains(VOID))
                    count++;
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c.tags.contains(VOID))
                    count++;
            }
        }
        return count;
    }

    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = (this.upgraded ? this.cardStrings.UPGRADE_DESCRIPTION: this.cardStrings.DESCRIPTION)
                + this.cardStrings.EXTENDED_DESCRIPTION[0] + countVoid();
        if(countVoid() == 1){
            this.rawDescription +=  this.cardStrings.EXTENDED_DESCRIPTION[1];
        }else{
            this.rawDescription += this.cardStrings.EXTENDED_DESCRIPTION[2];
        }
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = (this.upgraded ? this.cardStrings.UPGRADE_DESCRIPTION: this.cardStrings.DESCRIPTION);
        this.initializeDescription();
    }


    @Override
    public AbstractCard makeCopy() {
        return new FearTheVoid();
    }
}
