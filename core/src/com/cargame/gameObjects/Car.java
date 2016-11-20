package com.cargame.gameObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by isaias on 19/11/16.
 */

public abstract class Car
{
    public static final float INI_POSITION_X = 73.0f;
    public static final float INI_POSITION_X_2 = 30.0f;
    public static final float INI_POSITION_Y = 220.0f;
    public static final int STANDARD_WIDTH = 30;
    public static final int STANDART_HEIGHT = 30;

    protected Vector2 acceleration;
    protected Rectangle bounds;
    protected int height;
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;

    protected abstract void direction(float f);

    public abstract void update(float f);

    public Car(float posX, float posY) {
        this.position = new Vector2(posX, posY);
        this.acceleration = new Vector2();
        this.velocity = new Vector2();
        this.bounds = new Rectangle();
    }

    public void dispose() {
        this.bounds = null;
        this.position = null;
        this.acceleration = null;
        this.velocity = null;
    }

    public Rectangle getBounds()
    {
        return this.bounds;
    }

    public void setBounds(float posX, float posY)
    {
        this.bounds.x = posX;
        this.bounds.y = posY;
    }

    public void setVelocity(float velocity)
    {
        this.velocity.y = velocity;
    }

    public static boolean collision(Car car1, Car car2)
    {
        return  car1.position.x <= car2.position.x + car2.getBounds().width  &&
                car1.position.y <= car2.position.y + car2.getBounds().height &&
                car1.position.x + car1.getBounds().width >= car2.position.x  &&
                car1.position.y + car1.getBounds().height >= car2.position.y;
    }
}
