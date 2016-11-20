package com.cargame.gameWorld;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cargame.gameObjects.Car;
import com.cargame.gameObjects.EnemyCar;
import com.cargame.gameObjects.PlayerCar;
import com.cargame.gameObjects.RoadControl;
import com.cargame.helpers.AssetLoader;
import com.cargame.helpers.Constants;

import java.util.ArrayList;
import java.util.Iterator;

import static com.cargame.helpers.Constants.ABOUT;
import static com.cargame.helpers.Constants.GAME_OVER;
import static com.cargame.helpers.Constants.HIGH_SCORE;
import static com.cargame.helpers.Constants.MAIN_MENU;
import static com.cargame.helpers.Constants.NEW_GAME;
import static com.cargame.helpers.Constants.NEW_RECORD;
import static com.cargame.helpers.Constants.READY;


/**
 * Created by isaias on 19/11/16.
 * rendering objects game.
 */

public class GameRenderer
{


    private  GameWorld gameWorld;
    private  OrthographicCamera camera;
    private  Viewport viewport;
    private  SpriteBatch batch;
    private  RoadControl road;
    private  float virtualWidth;
    private  float virtualHeight;
    private  Vector2 positionNewGame;
    private  Vector2 positionAbout;
    private  Vector2 positionMainMenu;
    private  Vector2 positionReady;
    private Vector2 positionGameOver;

    private PlayerCar player;
    private ArrayList<EnemyCar> enimiesCars;
    private float runTime;

    private static GameRenderer gameRenderer;

    private GameRenderer(){}

    private GameRenderer(GameWorld gameWorld,float virtualWidth, float virtualHeight)
    {
        this.gameWorld = gameWorld;
        this.virtualHeight = virtualHeight;
        this.virtualWidth = virtualWidth;

        initCarsWorld(gameWorld,virtualWidth,virtualHeight);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(true,virtualWidth,virtualHeight);
        viewport = new StretchViewport(virtualWidth,virtualHeight,camera);
        batch.setProjectionMatrix(camera.combined);

        road = gameWorld.getRoad();
        road.getRoad1().setHeight(virtualHeight);
        road.getRoad1().setWidth(virtualWidth);

        float[] array = getWidthHeight(Constants.NEW_GAME,AssetLoader.fontNewGame);
        positionNewGame = new Vector2(virtualWidth/2 - array[0]/2,virtualHeight/2.5f);
        AssetLoader.newGame.set(positionNewGame.x,positionNewGame.y,array[0],-array[1]);

        array = getWidthHeight(ABOUT,AssetLoader.fontAbout);
        positionAbout = new Vector2(virtualWidth/2 - array[0]/2,virtualHeight/2.0f + 15);
        AssetLoader.about.set(positionAbout.x,positionAbout.y,array[0],-array[1]);

        array =  getWidthHeight(MAIN_MENU,AssetLoader.fontMainMenu);
        positionMainMenu = new Vector2(virtualWidth/2 - array[0]/2,virtualHeight/2 + array[1]);
        AssetLoader.mainMenu.set(positionMainMenu.x,positionMainMenu.y,array[0],-array[1]);

        array =  getWidthHeight(READY,AssetLoader.fontMainMenu);
        positionReady = new Vector2(virtualWidth/2 - array[0]/2,virtualHeight/2 );

        array =  getWidthHeight(GAME_OVER,AssetLoader.fontMainMenu);
        positionGameOver = new Vector2(virtualWidth/2 - array[0]/2,virtualHeight/2 );
    
        player = gameWorld.getPlayerCar();
        enimiesCars = gameWorld.getRivalsCars();
        

    }



    public  GameRenderer createGameRenderer(GameWorld gameWorld,float virtualWidth, float virtualHeight)
    {
        gameRenderer = new GameRenderer(gameWorld,virtualWidth,virtualHeight);
        return gameRenderer;
    }

    public static GameRenderer getInstance()
    {
        if(gameRenderer == null)
        {
            gameRenderer = new GameRenderer();
            return gameRenderer;
        }
        return gameRenderer;
    }

    private static float[] getWidthHeight(String fontName,BitmapFont font)
    {
        if(fontName == null) return null;

        GlyphLayout  layout = new GlyphLayout();
        float array[] = new float[2];
        layout.setText(font,fontName);
        array[0] = layout.width;
        array[1] = layout.height;

        return array;
    }

    public void render(float delta)
    {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        update(delta);

    }


    public void resize(float width, float height)
    {

        //gameHeight =  height / (width / gameWidth);
        viewport.update((int)width,(int)height);
    }

    public void dispose()
    {
        this.camera = null;
        this.viewport = null;
        batch.dispose();
        this.road.dispose();
        this.player.dispose();
        this.enimiesCars.clear();
        this.enimiesCars = null;
        positionNewGame = null;
        positionMainMenu = null;
        positionAbout = null;
    }

    private void initCarsWorld(GameWorld world, float gameWidth, float gameHeight)
    {
        float positionX = (gameWidth / 2) + (AssetLoader.textureRoad.getWidth() / 6);
        float positionY = ( (gameWidth / 2)) - (((float) AssetLoader.textureRoad.getWidth()) / 6.0f);
        float delta = (gameWidth/2) - (positionX - positionY);

        world.getPlayerCar().setPositionY(gameHeight - 32.0f);
        world.getPlayerCar().setPositionX(positionX);
        world.getPlayerCar().getVelocity().x =  (positionX);
        world.getPlayerCar().getVelocity().y = delta;
        world.getPlayerCar().setPositionIniX(world.getPlayerCar().getPositionX());
        world.getPlayerCar().setPositionIniX2(world.getPlayerCar().getVelocity().y);
        world.getPlayerCar().getBounds().width = (float) (AssetLoader.textureRoad.getWidth() / 8);
        world.getPlayerCar().getBounds().height = Car.INI_POSITION_X_2;
        world.initCarsRivals(world.getPlayerCar().getVelocity().x, world.getPlayerCar().getVelocity().y, (float) (AssetLoader.textureRoad.getWidth() / 8), Car.INI_POSITION_X_2);
    }

    private  void update(float delta)
    {
        gameWorld.update(delta);
        switch (gameWorld.getCurrentState())
        {
            case OPENING:
                opennig();
                road.update(delta);
                break;
            case ABOUT:
                about();
                break;
            case READY:
                road.update(delta);
                ready();
                break;
            case RUNNING:
                drawRunning(delta);
                break;
            case GAME_OVER:
                gameOver();
                break;
            case HIGH_SCORE:
                drawHighScore();
                break;
        }
    }

    private void ready()
    {
        GameWorld.setHighScore(0);
        AssetLoader.background.begin(ShapeRenderer.ShapeType.Filled);
        AssetLoader.background.setColor(0.21568628f, 0.3137255f, 0.39215687f, 1.0f);
        AssetLoader.background.rect(0.0f, 0.0f,(virtualWidth), (virtualHeight));
        AssetLoader.background.end();
        batch.begin();
            batch.draw(AssetLoader.regionRoad,  ((virtualWidth) - ((AssetLoader.textureRoad.getWidth()))), this.road.getRoad1().getPositionY(), ((float) AssetLoader.textureRoad.getWidth()), this.camera.viewportHeight);
            batch.draw(AssetLoader.regionRoad,  ((virtualWidth) - ((AssetLoader.textureRoad.getWidth()))), this.road.getRoad1().getTailY(), ((float) AssetLoader.textureRoad.getWidth()), this.camera.viewportHeight);

           batch.draw(AssetLoader.texturePlayerCar, this.player.getPositionX(), this.player.getPositionY(), this.player.getBounds().width, (float) Car.INI_POSITION_X_2);
            AssetLoader.shadow.draw(batch,READY,positionReady.x,positionReady.y);
            AssetLoader.fontNewGame.draw(batch,READY,positionReady.x,positionReady.y);
        batch.end();
    }

    public float getVirtualWidth()
    {
        return virtualWidth;
    }


    private void opennig()
    {
        batch.begin();
            batch.draw(AssetLoader.regionMenu, 0.0f, 0.0f,virtualWidth, virtualHeight);
            AssetLoader.shadow.draw(batch, Constants.NEW_GAME, positionNewGame.x, positionNewGame.y);
            AssetLoader.fontNewGame.draw(batch,Constants.NEW_GAME,positionNewGame.x,positionNewGame.y);
            AssetLoader.shadow.draw(batch, ABOUT, positionAbout.x, positionAbout.y);
            AssetLoader.fontAbout.draw(batch, ABOUT,positionAbout.x,positionAbout.y);
        batch.end();
    }

    private void about()
    {
        batch.begin();
            AssetLoader.shadow.draw(batch, ABOUT,positionAbout.x, 2.0f);
            AssetLoader.fontAbout.draw(batch, ABOUT, positionAbout.x, 1.0f);
            AssetLoader.shadow2.draw(batch, "Developer: Isaias Santana \n Musics Credits:\n - A Journey Awaits by lemon42.\n- 8 bit music by Alexander Blu\n- I Don\u00b4t Understand by Snabisch", 0.0f, 20.0f);
            AssetLoader.fontLevelScore.draw(batch,"Developer: Isaias Santana \n Musics Credits:\n - A Journey Awaits by lemon42.\n- 8 bit music by Alexander Blu\n- I Don\u00b4t Understand by Snabisch", 0.0f, 19.0f);
            AssetLoader.shadow.draw(batch, MAIN_MENU, positionMainMenu.x, positionMainMenu.y);
            AssetLoader.fontMainMenu.draw(batch, MAIN_MENU, positionMainMenu.x, positionMainMenu.y);
        batch.end();
    }

    private void drawRunning(float delta)
    {
        AssetLoader.background.begin(ShapeRenderer.ShapeType.Filled);
        AssetLoader.background.setColor(0.21568628f, 0.3137255f, 0.39215687f, 1.0f);
        AssetLoader.background.rect(0.0f, 0.0f, (virtualWidth * 2),  (virtualHeight * 2));
        AssetLoader.background.end();
        batch.begin();
            batch.draw(AssetLoader.textureRoad,  ((virtualWidth) - ((AssetLoader.textureRoad.getWidth()))), this.road.getRoad1().getPositionY(), ((float) AssetLoader.textureRoad.getWidth()), this.camera.viewportHeight);
            batch.draw(AssetLoader.textureRoad,  ((virtualWidth) - ((AssetLoader.textureRoad.getWidth()))), this.road.getRoad1().getTailY(), ((float) AssetLoader.textureRoad.getWidth()), this.camera.viewportHeight);
            if (this.player.isAlive())
            {
                batch.draw(AssetLoader.texturePlayerCar, this.player.getPositionX(), this.player.getPositionY(), this.player.getBounds().width, Car.INI_POSITION_X_2);
            }
            else
            {
                this.runTime =   this.runTime + 0.005f;
                batch.draw(AssetLoader.explosionAnimation.getKeyFrame(this.runTime), this.player.getPositionX(), this.player.getPositionY(), this.player.getBounds().width, Car.INI_POSITION_X_2);
                if (this.runTime > 1.03f)
                {
                    this.runTime = 1.03f;
                }
            }
            Iterator it = this.enimiesCars.iterator();
            while (it.hasNext())
            {
                EnemyCar car = (EnemyCar) it.next();
                if (car.isCollide())
                {
                    batch.draw(AssetLoader.explosionAnimation.getKeyFrame(this.runTime), car.getPositionX(), car.getPositionY(), car.getBounds().width, Car.INI_POSITION_X_2);
                }
                else
                {
                    batch.draw(AssetLoader.textureRivalCar, car.getPositionX(), car.getPositionY(), car.getBounds().width, Car.INI_POSITION_X_2);
                }
            }
            drawScore();
            drawLevel();
        batch.end();
    }

    private void gameOver()
    {
        batch.begin();
        AssetLoader.shadow.draw(batch,GAME_OVER,positionGameOver.x,positionGameOver.y);
        AssetLoader.fontNewGame.draw(batch,GAME_OVER,positionGameOver.x,positionGameOver.y);
        batch.end();
        this.runTime = 0.0f;
    }

    private void drawScore() {
        AssetLoader.shadow2.draw(batch, "Score: " + GameWorld.getHighScore(), 2.0f, 10.0f);
        AssetLoader.fontLevelScore.draw(batch, "Score: " + GameWorld.getHighScore(), 3.0f, 9.0f);
    }

    private void drawLevel() {
        AssetLoader.shadow2.draw(batch, "Level: " + GameWorld.getLevel(), 2.0f, 20.0f);
        AssetLoader.fontLevelScore.draw(batch, "Level: " + GameWorld.getLevel(), 3.0f, 19.0f);
    }

    public void drawHighScore()
    {
        float posNewRecord[] = getWidthHeight(NEW_RECORD,AssetLoader.fontNewGame);
        float posHighScore[] = getWidthHeight(HIGH_SCORE,AssetLoader.fontNewGame);
        int ant = AssetLoader.getHighScore();

        if (ant < GameWorld.getHighScore())
        {
            AssetLoader.setHighScore(GameWorld.getHighScore());
        }
        batch.begin();
            if (GameWorld.getHighScore() > ant)
            {
                AssetLoader.shadow.draw(batch, NEW_RECORD, (virtualWidth/2) - posNewRecord[0]/2, (virtualHeight/2) - posNewRecord[1]/2);
                AssetLoader.fontNewGame.draw(batch,NEW_RECORD,(virtualWidth/2) - posNewRecord[0]/2,(virtualHeight/2) - posNewRecord[1]/2);
                AssetLoader.shadow.draw(batch, HIGH_SCORE + GameWorld.getHighScore(), (virtualWidth/2) - posHighScore[0]/2,(virtualHeight/2) - posHighScore[1]/3);
                AssetLoader.fontNewGame.draw(batch, HIGH_SCORE+ GameWorld.getHighScore(), (virtualWidth/2) - posHighScore[0]/2,(virtualHeight/2) - posHighScore[1]/3);
            }
            else
            {
                AssetLoader.shadow.draw(batch, HIGH_SCORE+ GameWorld.getHighScore(), (virtualWidth/2) - posHighScore[0]/2,(virtualHeight/2) - posHighScore[1]/2);
                AssetLoader.fontNewGame.draw(batch, HIGH_SCORE + GameWorld.getHighScore(), (virtualWidth/2) - posHighScore[0]/2,(virtualHeight/2) - posHighScore[1]/2);
            }
        batch.end();
    }

}
