package basicmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basicmod.util.GeneralUtils;
import basicmod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import javax.management.ObjectName;

import static basicmod.TheLightbearer.makeID;


public class SuperCharged extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SuperCharged");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public SuperCharged(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SuperCharged(owner, amount);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        /* 36 */     if (card.tags.contains(SUPERSPELL) && !card.purgeOnUse && this.amount > 0) {
            /* 37 */       flash();
            /* 38 */       this.amount--;
            /* 39 */       if (this.amount == 0)
                /* 40 */         addToTop((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, "FreeAttackPower"));
            /*    */     }
        /*    */   }

}
