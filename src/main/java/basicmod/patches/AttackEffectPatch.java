package basicmod.patches;

import basicmod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class AttackEffectPatch {
    private static Texture GoldenGunTexture = TextureLoader.getTexture("hermitResources/images/vfx/HermitGhostFire.png");


    @SpirePatch(clz = FlashAtkImgEffect.class, method = "loadImage")
    public static class vfx{
        @SpirePrefixPatch
        public static SpireReturn Prefix(FlashAtkImgEffect e, AbstractGameAction.AttackEffect ___effect){
            if(___effect == EnumPatch.GOLDEN_GUN){
                return SpireReturn.Return(ImageMaster.ATK_BLUNT_LIGHT);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = FlashAtkImgEffect.class, method = "playSound")
    public static class sfx{
        @SpirePrefixPatch
        public static SpireReturn Prexix(FlashAtkImgEffect e, AbstractGameAction.AttackEffect effect){
            if(effect == EnumPatch.GOLDEN_GUN){
                //CardCrawlGame.sound.playV("GoldenGunSFX", 1.25f);
            }else{
                return SpireReturn.Continue();
            }
            return SpireReturn.Return();
        }
    }
}
