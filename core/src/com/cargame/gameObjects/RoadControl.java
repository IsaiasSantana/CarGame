package com.cargame.gameObjects;

import com.badlogic.gdx.Gdx;
import com.cargame.helpers.AssetLoader;

/**
 * Created by isaias on 19/11/16.
 */

public class RoadControl
{
    private static RoadControl roadControl;
    private static float velocity;
    private Road road1;

    private RoadControl()
    {
        velocity = 100.0f;
        this.road1 = new Road(0.0f, 0.0f, (float) Gdx.graphics.getHeight(), (float) AssetLoader.textureRoad.getWidth());
    }

    public static RoadControl getInstance() {
        if (roadControl == null) {
            roadControl = new RoadControl();
        }
        return roadControl;
    }

    public void dispose() {
        this.road1.dispose();
        this.road1 = null;
    }

    public void update(float delta) {
        this.road1.update(delta);
    }

    public Road getRoad1()
    {
        return this.road1;
    }

    public void setVelocity(float velocity)
    {
        this.velocity = velocity;
    }

    public static float getVelocity()
    {
        return velocity;
    }

    public static void incVelocity(float inc, float delta)
    {
        velocity += inc * delta;
    }

    public void reset()
    {
        this.road1.reset(this.road1.getPositionY());
        velocity = 100.0f;
        this.road1.setVelocity(100.0f);
    }
}
