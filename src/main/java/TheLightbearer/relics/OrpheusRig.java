package TheLightbearer.relics;

import TheLightbearer.character.LightbearerCharacter;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;


import com.megacrit.cardcrawl.rewards.RewardItem;

import static TheLightbearer.TheLightbearer.makeID;
import static TheLightbearer.util.CustomTags.VOID;
@AutoAdd.Ignore
public class OrpheusRig extends BaseRelic {
    private static final String NAME = "OrpheusRig";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public OrpheusRig() {
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
            if (c.tags.contains(VOID) && c.canUpgrade() && !c.upgraded) {
                  c.upgrade();
               }
       }
    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new OrpheusRig();
    }
}