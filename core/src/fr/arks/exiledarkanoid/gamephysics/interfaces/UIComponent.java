package fr.arks.exiledarkanoid.gamephysics.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * UIComponent interface
 *
 * This interface is used to create a UI component
 */
public interface UIComponent {
    void render(SpriteBatch batch);
    void dispose();
}
