package basicmod.potions;


import basemod.abstracts.CustomPotion;
import basicmod.TheLightbearer;
import basicmod.cards.ChooseArc;
import basicmod.cards.ChooseSolar;
import basicmod.cards.ChooseVoid;
import basicmod.powers.ChargeOfLightPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class LiquidFate extends BasePotion {

    private static final String ID = TheLightbearer.makeID("LiquidFate");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(ID);

    public LiquidFate() {
        super(ID, 1, PotionRarity.UNCOMMON, PotionSize.HEART, PotionColor.WEAK);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = Color.GRAY.cpy();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));

    }

    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            ArrayList<AbstractCard> elementChoices = new ArrayList<>();
            elementChoices.add(new ChooseArc());
            elementChoices.add(new ChooseSolar());
            elementChoices.add(new ChooseVoid());
            addToBot(new ChooseOneAction(elementChoices));
        }

    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public String getDescription() {
        return potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
    }

    public BasePotion makeCopy() {
        return new LiquidFate();
    }
}