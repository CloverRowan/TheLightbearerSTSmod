package basicmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basicmod.util.GeneralUtils;
import basicmod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.brashmonkey.spriter.Player;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import javax.management.ObjectName;

import static basicmod.TheLightbearer.makeID;


public class EchoOfHarvestPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("EchoOfHarvestPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static int magicNumber = 2;
    public EchoOfHarvestPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + magicNumber + DESCRIPTIONS[1];
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        addToBot(new AddTemporaryHPAction(AbstractDungeon.player,AbstractDungeon.player, this.amount));
    }

    public AbstractPower makeCopy() {
        return new EchoOfHarvestPower(owner, amount);
    }

}
