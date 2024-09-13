package TheLightbearer.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TempStrengthAction extends AbstractGameAction {
    public AbstractPlayer p;
    public int magicNumber;

    public TempStrengthAction(AbstractPlayer setP, int setMagicNumber){
        p = setP;
        magicNumber = setMagicNumber;
    }

    public void applyPowers(){
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber), magicNumber));
    }

    @Override
    public void update() {
        applyPowers();
        this.isDone = true;
    }
}
