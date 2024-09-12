package TheLightbearer.cards.Void;


import TheLightbearer.CustomActions.CheckPowerStacks;
import TheLightbearer.cards.BaseCard;
import TheLightbearer.character.LightbearerCharacter;
import TheLightbearer.powers.ChargeOfLightPower;
import TheLightbearer.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheLightbearer.util.CustomTags.*;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class Equalize extends BaseCard {

    public static final String ID = makeID("Equalize");
    private static final CardStats info = new CardStats(
            LightbearerCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2
    );
    private static final int UPG_MAGIC_NUMBER = 0;
    private static int MAGIC_NUMBER =0;


    public Equalize() {
        super(ID, info,"void");
        setExhaust(true);
        setCostUpgrade(1);
        tags.add(VOID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        MAGIC_NUMBER =  (new CheckPowerStacks(player,"TheLightbearer:ChargeOfLightPower").CheckPowerStacksAction());
        addToBot(new ApplyPowerAction(p,p, new ChargeOfLightPower(p,-MAGIC_NUMBER)));
            addToBot(new GainEnergyAction(MAGIC_NUMBER/2));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Equalize();
    }
}
