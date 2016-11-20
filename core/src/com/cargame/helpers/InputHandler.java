package com.cargame.helpers;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.cargame.gameObjects.PlayerCar;
import com.cargame.gameWorld.GameRenderer;
import com.cargame.gameWorld.GameWorld;



/**
 * Created by isaiasSantan on 20/11/16.
 */

public class InputHandler implements InputProcessor
{
    private PlayerCar player;
    private float scaleFactorX;
    private float scaleFactorY;
    private GameWorld world;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY)
    {
        this.player = world.getPlayerCar();
        this.world = world;
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode){
            case Input.Keys.LEFT:
                player.Left();
                return true;
            case Input.Keys.RIGHT:
                player.Right();
                return true;

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float XScale = scaleX(screenX);
        float YScale = scaleY(screenY);
        if (this.world.isOpening())
        {
            if (AssetLoader.newGame.contains( XScale, YScale))
            {
                this.world.setCurrentState(GameWorld.States.READY);
                return true;
            }
            AssetLoader.fontNewGame.setColor(Color.WHITE);

            if (AssetLoader.about.contains( XScale, YScale))
            {
                AssetLoader.fontAbout.setColor(Color.CYAN);
                this.world.setCurrentState(GameWorld.States.ABOUT);
                return true;
            }
            AssetLoader.fontAbout.setColor(Color.WHITE);
        }
        else if (this.world.isReady())
        {
            this.world.setCurrentState(GameWorld.States.RUNNING);
        }
        else if (this.world.isHighScore())
        {
            AssetLoader.fontNewGame.setColor(Color.WHITE);
            AssetLoader.musicGameOver.stop();
            this.world.setCurrentState(GameWorld.States.OPENING);
        }
        else if (this.world.isRunning())
        {
            if (XScale >= GameRenderer.getInstance().getVirtualWidth() / 2)
            {
                this.player.Right();
                return true;
            }
            else if (XScale <= GameRenderer.getInstance().getVirtualWidth() / 2)
            {
                this.player.Left();
                return true;
            }
        }
        else if (this.world.isAbout())
        {
            if (AssetLoader.mainMenu.contains(XScale,  YScale))
            {
                AssetLoader.fontMainMenu.setColor(Color.CYAN);
                AssetLoader.fontAbout.setColor(Color.WHITE);
                this.world.setCurrentState(GameWorld.States.OPENING);
                return true;
            }
            AssetLoader.fontMainMenu.setColor(Color.WHITE);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        if (this.world.isOpening())
        {
            if (AssetLoader.newGame.contains((float) screenX, (float) screenY))
            {
                AssetLoader.fontNewGame.setColor(Color.CYAN);
                return true;
            }
            AssetLoader.fontNewGame.setColor(Color.WHITE);
            if (AssetLoader.about.contains((float) screenX, (float) screenY))
            {
                AssetLoader.fontAbout.setColor(Color.CYAN);
                return true;
            }
            AssetLoader.fontAbout.setColor(Color.WHITE);
        }
        else if (this.world.isAbout())
        {
            if (AssetLoader.mainMenu.contains((float) screenX, (float) screenY))
            {
                AssetLoader.fontMainMenu.setColor(Color.CYAN);
                return true;
            }
            AssetLoader.fontMainMenu.setColor(Color.WHITE);
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    private int scaleX(int screenX) {
        return (int) (((float) screenX) / this.scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (((float) screenY) / this.scaleFactorY);
    }
}
