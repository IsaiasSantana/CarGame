package com.cargame.gameObjects;


import com.badlogic.gdx.math.Vector2;


/**
 * Created by isaias on 19/11/16.
 */

public class Road
{
    private float altura;
    private float height;
    private boolean isOutWindow;
    private Vector2 position;
    private float width;

    public Road(float posX, float posY, float height, float width)
    {
        this.position = new Vector2(posX, posY);
        this.height = height;
        this.width = width;
        this.altura = (-height)/2;
        this.isOutWindow = false;
    }

    public void dispose()
    {
        this.position = null;
    }

    public float getPositionX()
    {
        return this.position.x;
    }

    public float getPositionY() {
        return this.position.y;
    }

    public void setPositionX(float x)
    {
        this.position.x = x;
    }

    public void setPositionY(float y)
    {
        this.position.y = y;
    }

    public void update(float delta)
    {
        final float velocity = RoadControl.getVelocity() * delta;
        this.position.add(0.0f, velocity);
        this.altura += velocity;
        if (this.position.y  > height/2)
        {
            this.position.y = 0.0f;
           this.altura = ((-this.height));
        }
    }

    public void setVelocity(float velocity) {
        this.position.y =velocity;
    }

    public void stop(){
        this.setVelocity(0.0f);
    }

    public float getWidth()
    {
        return this.width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight()
    {
        return this.height;
    }

    public float getTailY()
    {
        return this.altura;
    }

    public void reset(float newY)
    {
        this.position.y = newY;
        this.isOutWindow = false;
    }

    public boolean isOutWindow()
    {
        return this.isOutWindow;
    }
}
