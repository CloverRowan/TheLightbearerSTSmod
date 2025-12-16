package TheLightbearer.relics;

import TheLightbearer.CustomActions.MakeTempTokenInHandAction;
import TheLightbearer.cards.BaseCard;
import TheLightbearer.cards.Super.Tokens.ShadowshotToken;
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

import static TheLightbearer.TheLightbearer.*;
import static TheLightbearer.util.CustomTags.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class OrpheusRig extends BaseRelic {
    private static final String NAME = "OrpheusRig";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private Boolean isUsed = false;

    public OrpheusRig() {
        super(ID, NAME, LightbearerCharacter.Enums.CARD_COLOR, RARITY, SOUND);}
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
            if (c.tags.contains(VOID) && c.canUpgrade() && !c.upgraded) {
                  c.upgrade();
               }
       }
    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
        super.onUseCard(card, useCardAction);
        //logger.info("using a card with orig");
        if(card.tags.contains(SUPERSPELL) && card.tags.contains(VOID) && isUsed == false){
            BaseCard c = (BaseCard)card.makeSameInstanceOf();
            if(card.upgraded){
                c.upgrade();
            }
            c.costForTurn = 0;
            addToBot(new MakeTempTokenInHandAction(c));
            addToBot(new ApplyPowerAction(player, player, new ChargeOfLightPower(player, ChargeOfLightPower.SUPER_COST)));
            isUsed = true;
            stopPulse();
        }

    }

    @Override
    public void atPreBattle() {
        isUsed = false; // Make sure usedThisTurn is set to false at the start of each combat.
        beginLongPulse();     // Pulse while the player can click on it.
    }
    @Override
    public void onVictory() {
        super.onVictory();
        isUsed = false;
        stopPulse();
    }

    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }

    public AbstractRelic makeCopy() {
        return new OrpheusRig();
    }
}