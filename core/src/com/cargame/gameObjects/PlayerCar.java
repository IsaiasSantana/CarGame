package com.cargame.gameObjects;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by isaias on 19/11/16.
 * @author Isaías Santana.
 */

public class PlayerCar extends Car
{
    private boolean isAlive;
    private float positionIniX;
    private float positionIniX2;

    public PlayerCar(float posX, float posY, int width, int height, float velX, float velY)
    {
        super(posX, posY);
        this.positionIniX = posX;
        this.velocity.x = velX;
        this.velocity.y = velY;
        this.positionIniX2 = this.velocity.y;
        super.getBounds().width = (float) width;
        super.getBounds().height = (float) height;
        this.isAlive = true;
    }

    public void setPositionIniX(float positionIniX)
    {
        this.positionIniX = positionIniX;
    }

    public void update(float delta)
    {
        setBounds(getPositionX(), getPositionY());
    }

    protected void direction(float delta) {
    }

    public float getPositionX()
    {
        return this.position.x;
    }

    public float getPositionY()
    {
        return this.position.y;
    }

    public void setPositionY(float position)
    {
        this.position.y = position;
    }

    public void setPositionX(float position)
    {
        this.position.x = position;
    }

    public void Left()
    {
        if (this.isAlive)
        {
            this.position.x = this.velocity.y;
        }
    }

    public void Right()
    {
        if (this.isAlive)
        {
            this.position.x = this.velocity.x;
        }
    }

    public void stop()
    {
        this.velocity.x = 0.0f;
        this.velocity.y = 0.0f;
    }

    public boolean isAlive()
    {
        return this.isAlive;
    }

    public void setIsAlive(boolean isAlive)
    {
        this.isAlive = isAlive;
    }

    public void setPositionIniX2(float positionIniX2)
    {
        this.positionIniX2 = positionIniX2;
    }

    public void reset()
    {
        this.position.x = this.positionIniX;
        this.isAlive = true;
        this.velocity.x = this.positionIniX;
        this.velocity.y = this.positionIniX2;
    }

    public Vector2 getVelocity()
    {
        return this.velocity;
    }
}
