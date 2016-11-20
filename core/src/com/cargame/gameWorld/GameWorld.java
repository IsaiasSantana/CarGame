package com.cargame.gameWorld;

import com.cargame.gameObjects.Car;
import com.cargame.gameObjects.EnemyCar;
import com.cargame.gameObjects.PlayerCar;
import com.cargame.gameObjects.RoadControl;
import com.cargame.helpers.AssetLoader;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by isaias on 19/11/16.
 * updating objects game.
 */

public class GameWorld
{

    private static int countLevel;
    private static ArrayList<Float> distance;
    private static int highScore;
    private static int level;
    private static ArrayList<EnemyCar> rival;
    private static GameWorld world;
    private States currentState;
    private PlayerCar player;
    private RoadControl roadControl;
    private float runTime;

    public enum States
    {
        OPENING,
        READY,
        RUNNING,
        GAME_OVER,
        HIGH_SCORE,
        ABOUT,
        CREDITS
    }



    private GameWorld()
    {
        this.player = new PlayerCar(Car.INI_POSITION_X, Car.INI_POSITION_Y, 30, 30, 0.0f, 0.0f);
        rival = new ArrayList();
        distance = new ArrayList();
        this.roadControl = RoadControl.getInstance();
        this.currentState = States.OPENING;
        this.runTime = 0.0f;
        highScore = 0;
        level = 1;
    }

    public void dispose()
    {
        this.player.dispose();
        rival.clear();
        rival = null;
        distance.clear();
        distance = null;
        this.currentState = null;
        this.roadControl.dispose();
    }

    public void update(float delta)
    {
        switch (currentState)
        {
            case OPENING:
                opening();
                break;
            case READY:
                ready(delta);
                break;
            case RUNNING:
                running(delta);
                break;
            case GAME_OVER:
                gameOver();
                break;
        }
    }

    private void opening() {
        if (!AssetLoader.intro.isPlaying()) {
            AssetLoader.intro.play();
            AssetLoader.intro.setLooping(true);
            AssetLoader.intro.setVolume(0.2f);
        }
    }

    public boolean isAbout() {
        return this.currentState == States.ABOUT;
    }

    public boolean isOpening() {
        return this.currentState == States.OPENING;
    }

    public boolean isReady() {
        return this.currentState == States.READY;
    }

    public boolean isRunning() {
        return this.currentState == States.RUNNING;
    }

    public boolean isGameOver() {
        return this.currentState == States.GAME_OVER;
    }

    public boolean isHighScore() {
        return this.currentState == States.HIGH_SCORE;
    }

    public void setCurrentState(States state) {
        this.currentState = state;
    }

    public States getCurrentState() {
        return this.currentState;
    }

    private void ready(float delta)
    {
        if (AssetLoader.intro.isPlaying())
        {
            AssetLoader.intro.stop();
        }
        else if (!AssetLoader.musicPlayGame.isPlaying())
        {
            AssetLoader.musicPlayGame.play();
            AssetLoader.musicPlayGame.setLooping(true);
            AssetLoader.musicPlayGame.setVolume(0.2f);
        }
        this.roadControl.update(delta);
    }

    private void running(float delta)
    {
        this.player.update(delta);
        this.roadControl.update(delta);
        Iterator it = rival.iterator();
        while (it.hasNext())
        {
            EnemyCar car = (EnemyCar) it.next();
            car.update(delta);
            if (testCollision(car) && this.player.isAlive())
            {
                this.player.setIsAlive(false);
                this.player.stop();
                this.roadControl.setVelocity(0.0f);
                AssetLoader.explosion.play();
                break;
            }
        }
        if (!this.player.isAlive() && this.runTime < 1.03f)
        {
            this.runTime =  this.runTime + 0.005f;
        }
        if (this.runTime > 1.03f)
        {
            this.runTime = 0.0f;
            if (AssetLoader.musicPlayGame.isPlaying())
            {
                AssetLoader.musicPlayGame.stop();
            }
            this.currentState = States.GAME_OVER;
        }
    }

    private void gameOver()
    {
        if (AssetLoader.musicPlayGame.isPlaying())
        {
            AssetLoader.musicPlayGame.stop();
        }
        else if (!AssetLoader.musicGameOver.isPlaying())
        {
            AssetLoader.musicGameOver.play();
            AssetLoader.musicGameOver.setVolume(0.2f);
            AssetLoader.musicGameOver.setLooping(false);
        }
        this.runTime =  this.runTime + 0.005f;
        if (this.runTime > 0.5f)
        {
            this.runTime = 0.0f;
            this.currentState = States.HIGH_SCORE;
        }
        this.roadControl.reset();
        this.player.reset();
        for (int i = 0; i < rival.size(); i++)
        {
             rival.get(i).reset(distance.get(i));
        }
        level = 1;
        countLevel = 1;
    }

    public static GameWorld getInstanceWorld()
    {
        if (world == null) {
            world = new GameWorld();
        }
        return world;
    }

    public PlayerCar getPlayerCar()
    {
        return this.player;
    }

    public RoadControl getRoad()
    {
        return this.roadControl;
    }

    public ArrayList<EnemyCar> getRivalsCars()
    {
        return rival;
    }

    public void initCarsRivals(float posX1, float posX2, float width, float height)
    {
        float positionY = -90.0f;
        for (int i = 0; i < 30; i++)
        {
            if (i % 2 == 0)
            {
                distance.add(positionY);
                rival.add(new EnemyCar(posX1, positionY, RoadControl.getVelocity(), width, height));
            }
            else
            {
                distance.add(positionY);
                rival.add(new EnemyCar(posX2, positionY, RoadControl.getVelocity(), width, height));
            }
            positionY -= 70.0f;
        }
    }

    public boolean testCollision(EnemyCar car) {

        if (!Car.collision(this.player, car))
        {
            return false;
        }
        car.setCollide(true);
        return true;
    }

    public static void countHighScore() {
        highScore++;
    }

    public static void setHighScore(int highScore) {
        highScore = highScore;
    }

    public static int getHighScore() {
        return highScore;
    }

    public static void setLevel(int level) {
        level = level;
    }

    public static int getCountLevel() {
        return countLevel;
    }

    public static void setCountLevel(int level) {
        countLevel = level;
    }

    public static int getLevel() {
        return level;
    }

    public static int getTotalRivalCars() {
        return rival.size();
    }

    public static ArrayList<Float> getDistances()
    {
        return distance;
    }
}
