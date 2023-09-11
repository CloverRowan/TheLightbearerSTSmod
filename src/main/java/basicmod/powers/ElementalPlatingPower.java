package basicmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basicmod.util.GeneralUtils;
import basicmod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import javax.management.ObjectName;

import static basicmod.TheLightbearer.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class ElementalPlatingPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("ElementalPlatingPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public ElementalPlatingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ElementalPlatingPower(owner, amount);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if(card.cardID.equals("Burn")){
            flash();
            addToBot(new DiscardSpecificCardAction(card, player.hand));
        }
    }
}
