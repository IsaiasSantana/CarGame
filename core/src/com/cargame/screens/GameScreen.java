package com.cargame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.cargame.gameWorld.GameRenderer;
import com.cargame.gameWorld.GameWorld;
import com.cargame.helpers.InputHandler;

/**
 * Created by isaias on 19/11/16.
 */

public class GameScreen implements Screen
{
    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    public GameScreen()
    {
        Gdx.app.log("GameScreen","Attached");
        gameWorld =  GameWorld.getInstanceWorld();

        float screenWidth = (float) Gdx.graphics.getWidth();
        float screenHeight = (float) Gdx.graphics.getHeight();
        float gameWidth = 209.0f;
        float gameHeight =  screenHeight / (screenWidth / gameWidth);

        gameRenderer =  GameRenderer.getInstance().createGameRenderer(gameWorld,gameWidth,gameHeight);
        Gdx.input.setInputProcessor(new InputHandler(gameWorld,screenWidth/gameWidth,screenHeight/gameHeight));
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void render(float delta)
    {
        // Sets a Color to Fill the Screen with (RGB = 10, 15, 230), Opacity of 1 (100%)
        Gdx.gl.glClearColor(10/255.0f, 15/255.0f, 230/255.0f, 1f);
        // Fills the screen with the selected color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       // Gdx.app.log("GameScreen FPS",((int) (1/delta)) + "");
        gameRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height)
    {
        Gdx.app.log("GameScreen", "resizing");
        gameRenderer.resize(width,height);

    }

    @Override
    public void pause()
    {
        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume()
    {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void hide()
    {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void dispose() {

    }
}
