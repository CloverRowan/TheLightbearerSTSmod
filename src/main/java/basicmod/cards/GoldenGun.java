package basicmod.cards;


import basemod.helpers.VfxBuilder;
import basicmod.character.MyCharacter;
import basicmod.patches.EnumPatch;
import basicmod.powers.ChargeOfLightPower;
import basicmod.util.CardStats;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

import static basicmod.util.CustomTags.SOLAR;
import static basicmod.util.CustomTags.SUPERSPELL;


public class GoldenGun extends BaseCard {

    public static final String ID = makeID("GoldenGun");
    private static final CardStats info = new CardStats(
            MyCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3
    );
    private static final int DAMAGE = 9 ;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC_NUMBER = 3;



    public GoldenGun() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC_NUMBER);
        tags.add(SUPERSPELL);
        tags.add(SOLAR);
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //fix sound and vfx

        for(int i = 0; i < this.magicNumber; i++){

            CardCrawlGame.sound.playV("GoldenGunSFX", 1.25f);


            AbstractGameEffect GoldenGunVFX = new VfxBuilder(ImageMaster.HORIZONTAL_LINE, p.drawX, m.hb.cY, 0.1f)
                    .setColor(Color.ORANGE)
                    .setScale(2.0f)
                    .moveX(p.drawX,m.drawX)
                    .build();

            //AbstractGameAction.AttackEffect gg = new AbstractGameAction.AttackEffect();

            addToBot(new VFXAction(GoldenGunVFX, 0.1F));
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), EnumPatch.GOLDEN_GUN));
        }




    }

    @Override
    public AbstractCard makeCopy() {
        return new GoldenGun();
    }
}

