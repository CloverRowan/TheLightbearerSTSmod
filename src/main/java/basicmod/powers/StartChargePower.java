package basicmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static basicmod.TheLightbearer.makeID;
import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


public class StartChargePower implements OnStartBattleSubscriber{

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

    }
}
