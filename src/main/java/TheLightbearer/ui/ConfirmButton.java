package TheLightbearer.ui;

import basemod.IUIElement;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ConfirmButton implements IUIElement {
    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void update() {

    }

    @Override
    public int renderLayer() {
        return 0;
    }

    @Override
    public int updateOrder() {
        return 0;
    }

    @Override
    public void set(float xPos, float yPos) {
        IUIElement.super.set(xPos, yPos);
    }

    @Override
    public void setX(float xPos) {
        IUIElement.super.setX(xPos);
    }

    @Override
    public void setY(float yPos) {
        IUIElement.super.setY(yPos);
    }

    @Override
    public float getX() {
        return IUIElement.super.getX();
    }

    @Override
    public float getY() {
        return IUIElement.super.getY();
    }
}
