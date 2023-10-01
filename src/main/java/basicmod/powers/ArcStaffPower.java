package basicmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static basicmod.TheLightbearer.makeID;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class ArcStaffPower extends BasePower implements CloneablePowerInterface, NonStackablePower {
    public static final String POWER_ID = makeID("ArcStaffPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;


    public ArcStaffPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ArcStaffPower(owner, amount);
    }

    public void atStartOfTurn(){
        addToBot(new GainEnergyAction(1));
        addToBot(new DrawCardAction(1));
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if(this.amount > 0){
            reducePower(1);
        }
        if(this.amount <= 0){
            addToBot(new ApplyPowerAction(player,player,new DexterityPower(player,-3)));
            addToBot(new RemoveSpecificPowerAction(player, player,this));
        }

    }

}
