package TheLightbearer.relics;

import TheLightbearer.character.LightbearerCharacter;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static TheLightbearer.TheLightbearer.makeID;

public class SagirasShell extends BaseRelic implements ClickableRelic {
    private static final String NAME = "SagirasShell";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private boolean usedThisTurn = false; // You can also have a relic be only usable once per combat. Check out Hubris for more examples, including other StSlib things.
    private boolean isPlayerTurn = false;
    private boolean used = false;

    public SagirasShell() {
        super(ID, NAME, LightbearerCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }
    public void onRightClick() {
        if (!isObtained || used || !isPlayerTurn) {
            // If it has been used this turn, the player doesn't actually have the relic (i.e. it's on display in the shop room), or it's the enemy's turn
            return; // Don't do anything.
        }

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) { // Only if you're in combat
            usedThisTurn = true; // Set relic as "Used this turn"
            flash(); // Flash
            stopPulse(); // And stop the pulsing animation (which is started in atPreBattle() below)
            addToBot(new HealAction(AbstractDungeon.player,AbstractDungeon.player, 10));
            this.used = true;
        }
    }
        @Override
        public void atPreBattle() {
        usedThisTurn = false; // Make sure usedThisTurn is set to false at the start of each combat.
        beginLongPulse();     // Pulse while the player can click on it.
    }

    public void atTurnStart() {
        usedThisTurn = false;  // Resets the used this turn. You can remove this to use a relic only once per combat rather than per turn.
        isPlayerTurn = true; // It's our turn!
        beginLongPulse(); // Pulse while the player can click on it.
    }
    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(LittleLight.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(LittleLight.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("LittleLight");
    }

    @Override
    public void onPlayerEndTurn() {
        isPlayerTurn = false; // Not our turn now.
        stopPulse();
    }


    @Override
    public void onVictory() {
        stopPulse(); // Don't keep pulsing past the victory screen/outside of combat.
        used = false;
    }

    public AbstractRelic makeCopy() {
        return new SagirasShell();
    }
}