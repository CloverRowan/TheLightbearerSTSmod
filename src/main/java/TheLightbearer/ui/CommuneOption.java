package TheLightbearer.ui;

import TheLightbearer.TheLightbearer;
import TheLightbearer.util.SuperReward;
import TheLightbearer.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireTokeEffect;

public class CommuneOption extends AbstractCampfireOption {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(TheLightbearer.makeID("CommuneOption"));

    public static final String[] TEXT = uiStrings.TEXT;

    private static final Texture IMG = TextureLoader.getTexture("TheLightbearer/images/cards/skill/CallOnLight.png");

    public CommuneOption(boolean active){
        this.label = TEXT[0];
        this.usable = active;
        this.description = TEXT[1];
        this.img = IMG;
    }

    @Override
    public void useOption() {
        if(this.usable){
            AbstractDungeon.effectList.add(new CampfireCommuneEffect());



        }
    }
}
