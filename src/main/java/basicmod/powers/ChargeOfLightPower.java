package basicmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basicmod.util.GeneralUtils;
import basicmod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static basicmod.TheLightbearer.makeID;


public class ChargeOfLightPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("ChargeOfLight");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    //final int MAX_STACKS = 5;

    public ChargeOfLightPower(AbstractCreature owner, int amount){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }
    public void updateDescription(){
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2];
    }
    @Override
    public AbstractPower makeCopy() {
        return new ChargeOfLightPower(owner, amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        /*
        int newAmount = this.amount + stackAmount;
        if(newAmount >= MAX_STACKS){
            newAmount = MAX_STACKS;
        }
        */
        this.amount += stackAmount;
    }
}
