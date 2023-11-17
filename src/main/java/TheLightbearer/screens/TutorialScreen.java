package TheLightbearer.screens;

import basemod.ModButton;
import basemod.ModPanel;
import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import static TheLightbearer.TheLightbearer.makeID;
//Deprecated
public class TutorialScreen extends CustomScreen {
    private static ModButton confirm;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("SplashText"));
    private static final String[] TEXT = uiStrings.TEXT;

    public static class Enum{
        @SpireEnum
        public static AbstractDungeon.CurrentScreen TUTORIAL_SCREEN;
    }


    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.TUTORIAL_SCREEN;
    }


    private void open(){
        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
        confirm = new ModButton(Settings.WIDTH * 0.025f, Settings.HEIGHT * 0.05f,
                new Texture("TheLightbearer/images/relics/LittleLight.png"),
                new ModPanel(),
                b -> close()
        );

    }


    @Override
    public void reopen() {
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
    }

    @Override
    public void close() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void openingSettings() {
        AbstractDungeon.previousScreen = curScreen();
    }
}
