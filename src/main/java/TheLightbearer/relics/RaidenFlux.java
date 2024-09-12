package TheLightbearer.relics;

import TheLightbearer.character.LightbearerCharacter;

import TheLightbearer.powers.ChargeOfLightPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import com.megacrit.cardcrawl.rewards.RewardItem;

import static TheLightbearer.TheLightbearer.makeID;
import static TheLightbearer.util.CustomTags.ARC;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class RaidenFlux extends BaseRelic {
    private static final String NAME = "RaidenFlux";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private Boolean isUsed = false;

    public RaidenFlux() {
        super(ID, NAME, LightbearerCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }
    public void onEquip() {
        for (RewardItem reward : AbstractDungeon.combatRewardScreen.rewards) {
            if (reward.cards != null) {
                for (AbstractCard c : reward.cards) {
                    onPreviewObtainCard(c);
                }
            }
        }
    }


    public void onPreviewObtainCard(AbstractCard c) {
        onObtainCard(c);
    }


    public void onObtainCard(AbstractCard c) {
        if (c.tags.contains(ARC) && c.canUpgrade() && !c.upgraded) {
            c.upgrade();
        }
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        super.onUseCard(card, useCardAction);
        //logger.info("using a card with orig");
        if(card.cardID.equals(makeID("ArcStaff")) && isUsed == false){
            //logger.info("passing logic gate");
            addToBot(new MakeTempCardInHandAction(card));
            addToBot(new ApplyPowerAction(player,player,new ChargeOfLightPower(player, 10)));
            addToBot(new GainEnergyAction(2));
            isUsed = true;
            //logger.info("orig done");
        }

    }

    @Override
    public void onVictory() {
        super.onVictory();
        isUsed = false;
    }
    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }

    public AbstractRelic makeCopy() {
        return new RaidenFlux();
    }
}