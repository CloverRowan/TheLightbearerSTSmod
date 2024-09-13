package TheLightbearer.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.brashmonkey.spriter.Player;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class ComfortingFlamesEffect extends AbstractGameEffect {
    private float x;

    private float y;

    private float vY;

    private float vX;

    private float scaleY;

    private int frame = 0;

    private float animTimer = 0.05F;

    private static final int W = 32;

    public ComfortingFlamesEffect(float playerX, float playerY) {
        this.x = playerX; //MathUtils.random(100.0F * Settings.scale, 1820.0F * Settings.scale);
        this.y = playerY; //Settings.HEIGHT + MathUtils.random(20.0F, 300.0F) * Settings.scale;
        this.frame = MathUtils.random(8);
        this.rotation = MathUtils.random(-10.0F, 10.0F);
        this.scale = MathUtils.random(1.0F, 2.5F);
        this.scaleY = MathUtils.random(1.0F, 1.2F);
        if (this.scale < 1.5F)
            this.renderBehind = true;
      this.vY = MathUtils.random(100.0F, 75.0F) * this.scale * Settings.scale;
        this.vX = MathUtils.random(-100.0F, 100.0F) * this.scale * Settings.scale;
        this.scale *= Settings.scale;
        if (MathUtils.randomBoolean())
            this.rotation += 180.0F;
        this.color = Color.SCARLET; //new Color(1.0F, MathUtils.random(0.7F, 0.9F), MathUtils.random(0.7F, 0.9F), 1.0F);
        this.duration = 2.0F;
    }

    // public ComfortingFlamesEffect(float x, float y, float fAngle) {
   /*     this.img = ImageMaster.DAGGER_STREAK;
        this.x = x - 320.0F - this.img.packedWidth / 2.0F;
        this.destY = y;
        this.y = this.destY - this.img.packedHeight / 2.0F;
        this.startingDuration = 0.4F;
        this.duration = 0.4F;
        this.scale = Settings.scale;
        this.rotation = fAngle;
        this.color = Color.ORANGE.cpy();
        this.forcedAngle = true;*/

    //}

    /*public ComfortingFlamesEffect(float x, float y, float fAngle, float length) {
        this.img = ImageMaster.DAGGER_STREAK;
        this.x = x - length - this.img.packedWidth / 2.0F;
        this.destY = y;
        this.y = this.destY - this.img.packedHeight / 2.0F;
        this.startingDuration = 0.4F;
        this.duration = 0.4F;
        this.scale = Settings.scale;
        this.rotation = fAngle;
        this.color = Color.ORANGE.cpy();
        this.forcedAngle = true;
    }*/

    private void playRandomSfX() {
        int roll = MathUtils.random(5);
        switch (roll) {
            case 0:
                CardCrawlGame.sound.play("ATTACK_DAGGER_1");
                return;
            case 1:
                CardCrawlGame.sound.play("ATTACK_DAGGER_2");
                return;
            case 2:
                CardCrawlGame.sound.play("ATTACK_DAGGER_3");
                return;
            case 3:
                CardCrawlGame.sound.play("ATTACK_DAGGER_4");
                return;
            case 4:
                CardCrawlGame.sound.play("ATTACK_DAGGER_5");
                return;
        }
        CardCrawlGame.sound.play("ATTACK_DAGGER_6");
    }

    public void update() {
        this.y -= this.vY * Gdx.graphics.getDeltaTime();
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        this.animTimer -= Gdx.graphics.getDeltaTime() / this.scale;
        if (this.animTimer < 0.0F) {
            this.animTimer += 0.05F;
            this.frame++;
            if (this.frame > 11)
                this.frame = 0;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        } else if (this.duration < 1.0F) {
            this.color.a = this.duration;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        switch (this.frame) {
            case 0:
                renderImg(sb, ImageMaster.PETAL_VFX[0], false, false);
                break;
            case 1:
                renderImg(sb, ImageMaster.PETAL_VFX[1], false, false);
                break;
            case 2:
                renderImg(sb, ImageMaster.PETAL_VFX[2], false, false);
                break;
            case 3:
                renderImg(sb, ImageMaster.PETAL_VFX[3], false, false);
                break;
            case 4:
                renderImg(sb, ImageMaster.PETAL_VFX[2], true, true);
                break;
            case 5:
                renderImg(sb, ImageMaster.PETAL_VFX[1], true, true);
                break;
            case 6:
                renderImg(sb, ImageMaster.PETAL_VFX[0], true, true);
                break;
            case 7:
                renderImg(sb, ImageMaster.PETAL_VFX[1], true, true);
                break;
            case 8:
                renderImg(sb, ImageMaster.PETAL_VFX[2], true, true);
                break;
            case 9:
                renderImg(sb, ImageMaster.PETAL_VFX[3], true, true);
                break;
            case 10:
                renderImg(sb, ImageMaster.PETAL_VFX[2], false, false);
                break;
            case 11:
                renderImg(sb, ImageMaster.PETAL_VFX[1], false, false);
                break;
        }

    }
    private void renderImg(SpriteBatch sb, Texture img, boolean flipH, boolean flipV) {
        sb.setBlendFunction(770, 1);
        sb.draw(img, this.x, this.y, 16.0F, 16.0F, 32.0F, 32.0F, this.scale, this.scale * this.scaleY, this.rotation, 0, 0, 32, 32, flipH, flipV);
        sb.setBlendFunction(770, 771);
    }
    @Override
    public void dispose() {

    }
}
