package TheLightbearer.potions;

import TheLightbearer.TheLightbearer;
import TheLightbearer.powers.ChargeOfLightPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BottleOfLight extends BasePotion {

    private static final String ID = TheLightbearer.makeID("BottleOfLight");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);
    public BottleOfLight() {
        super(ID, 5 , PotionRarity.COMMON, PotionSize.JAR, PotionColor.WHITE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.GRAY.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));

    }
    public void use(AbstractCreature target) {
        if(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new ChargeOfLightPower(AbstractDungeon.player,this.potency)));}

    }
    public int getPotency(int ascensionLevel) {
        return 5;
    }

    @Override
    public String getDescription() {
        return potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
    }

    public BasePotion makeCopy() {
        return new BottleOfLight();
    }
}
