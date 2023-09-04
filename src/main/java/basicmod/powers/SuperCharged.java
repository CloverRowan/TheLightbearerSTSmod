package basicmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static basicmod.TheLightbearer.makeID;
import static basicmod.util.CustomTags.SUPERSPELL;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


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
        if (card.tags.contains(SUPERSPELL) && !card.purgeOnUse && this.amount > 0) {
                flash();
                this.amount--;
                if (this.amount == 0){
                    addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "SuperCharged"));
                }
        }
    }

    @Override
    public void onInitialApplication() {
        //Set cost of all supers in hand, draw, and discard pile to 0
        reduceSuperCost(player.hand);
        reduceSuperCost(player.drawPile);
        reduceSuperCost(player.discardPile);
    }

    @Override
    public void onRemove(){
        //set cost of all supers back to default
        returnSuperCost(player.hand);
        returnSuperCost(player.drawPile);
        returnSuperCost(player.discardPile);
    }

    public void reduceSuperCost(CardGroup cg){
        for(int i = 0; i < cg.size(); i++){
            AbstractCard c = cg.group.get(i);
            if(c.tags.contains(SUPERSPELL)){
                c.costForTurn = 0;
                c.isCostModified = true;
                c.isGlowing = true;
            }
        }
    }

    public void returnSuperCost(CardGroup cg){
        for(int i = 0; i < cg.size(); i++){
            AbstractCard c = cg.group.get(i);
            if(c.tags.contains(SUPERSPELL)){
                c.costForTurn = c.cost;
                c.isCostModified = false;
                c.isGlowing = false;
            }
        }
    }
    }
