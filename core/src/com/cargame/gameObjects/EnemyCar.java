package com.cargame.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.cargame.gameWorld.GameWorld;

/**
 * Created by isaias on 19/11/16.
 */

public class EnemyCar extends Car
{
    private boolean collide;
    private float halfHeight;

    public EnemyCar(float posX, float posY, float velocity, float width, float height)
    {
        super(posX, posY);

        float screenWidth = (float) Gdx.graphics.getWidth();
        float screenHeight = (float) Gdx.graphics.getHeight();
        float gameWidth = 209.0f;
        float gameHeight =  screenHeight / (screenWidth / gameWidth);

        this.halfHeight = (gameHeight ) + 60;
        this.velocity.y = velocity;
        this.velocity.x = 0.0f;
        this.bounds.height = height;
        this.bounds.width = width;
        this.bounds.x = posX;
        this.bounds.y = posY;
        this.collide = false;
    }

    public void update(float delta)
    {
        Vector2 vector2 = this.position;
        vector2.y += RoadControl.getVelocity() * delta;
        setBounds(getPositionX(), getPositionY());
        int cont = GameWorld.getTotalRivalCars() - 1;

        if (isNotVisible())
        {
            int cont2;
            if (cont >= 0)
            {
                cont2 = cont - 1;
                reset((GameWorld.getDistances().get(cont)).floatValue() + 200.0f);
                GameWorld.countHighScore();
                cont = cont2;
            }
            else
            {
                cont = GameWorld.getTotalRivalCars() - 1;
                cont2 = cont - 1;
                reset((float) cont);
                cont = cont2;
            }
            if (GameWorld.getCountLevel() > 10)
            {
                RoadControl.incVelocity(25.0f / delta, delta);
                GameWorld.setCountLevel(1);
                GameWorld.setLevel(GameWorld.getLevel() + 1);
                return;
            }
            GameWorld.setCountLevel(GameWorld.getCountLevel() + 1);
        }
    }

//    public void updateOnlyOneOnce(float delta)
//    {
//        int cont = GameWorld.getTotalRivalCars() - 1;
//        if (!isNotVisible()) {
//            return;
//        }
//
//        if (cont >= 0)
//        {
//            int cont2 = cont - 1;
//            reset(((Float) GameWorld.getDistances().get(cont)).floatValue() + 200.0f);
//            GameWorld.countHighScore();
//            cont = cont2;
//            return;
//        }
//        cont = GameWorld.getTotalRivalCars() - 1;
//        int cont2 = cont - 1;
//        reset((float) cont);
//        cont = cont2;
//    }

    protected void direction(float delta)
    {
    }

    private boolean isNotVisible()
    {
        return this.position.y + ((float) this.height) > ( this.halfHeight);
    }

    public float getPositionY()
    {
        return this.position.y;
    }

    public float getPositionX()
    {
        return this.position.x;
    }

    public void reset(float newY)
    {
        this.position.y = newY;
        this.collide = false;
    }

//    public float getTail()
//    {
//        return this.position.y - ((float) this.height);
//    }

    public void setCollide(boolean collide)
    {
        this.collide = collide;
    }

    public boolean isCollide()
    {
        return this.collide;
    }
}
