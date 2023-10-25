package TheLightbearer.patches;

import TheLightbearer.cards.BaseCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.lang.reflect.Field;

@SpirePatch(clz=SingleCardViewPopup.class, method="renderTitle")
public class SingleCardViewBorderTextPatch {
    private static Field cardField;
    private static Field yField;
    private static float drawScale = 2.0F;
    private static float yOffsetBase = 690;

    static {
        try {
            cardField = SingleCardViewPopup.class.getDeclaredField("card");
            cardField.setAccessible(true);
            yField = SingleCardViewPopup.class.getDeclaredField("current_y");
            yField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
        try {
            AbstractCard card = (AbstractCard)cardField.get(__instance);
            ((BaseCard)card).renderBorderText(sb, Settings.WIDTH / 2.0F, (float)yField.get(__instance), yOffsetBase, drawScale);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}