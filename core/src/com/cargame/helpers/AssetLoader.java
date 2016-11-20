package com.cargame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.net.HttpStatus;


public class AssetLoader
{

    public static Animation explosionAnimation;
    public static BitmapFont fontNewGame;
    public static BitmapFont fontAbout;
    public static BitmapFont fontLevelScore;
    public static BitmapFont fontMainMenu;
    public static BitmapFont shadow;
    public static BitmapFont shadow2;
    public static Music intro;
    public static Music musicGameOver;
    public static Music musicPlayGame;
    public static Preferences prefs;
    public static Rectangle about;
    public static Rectangle mainMenu;
    public static Rectangle newGame;
    public static ShapeRenderer background;
    public static Sound explosion;
    public static TextureRegion regionRoad;
    public static TextureRegion regionMenu;
    public static Texture textureRoad;
    public static TextureRegion texturePlayerCar;
    public static TextureRegion textureRivalCar;

    public static void load()
    {
        texturePlayerCar = new TextureRegion(new Texture(Gdx.files.internal("Car0.png")));
        textureRivalCar = new TextureRegion(new Texture(Gdx.files.internal("Car1.png")));
        regionMenu = new TextureRegion(new Texture(Gdx.files.internal("menu.png")));
        regionMenu.flip(false, true);
        regionMenu.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        textureRoad = new Texture(Gdx.files.internal("road.bmp"));
        regionRoad = new TextureRegion(textureRoad);
        background = new ShapeRenderer();
        explosion = Gdx.audio.newSound(Gdx.files.internal("explosao2.wav"));
        intro = Gdx.audio.newMusic(Gdx.files.internal("A Journey Awaits-Lemon42.mp3"));
        musicPlayGame = Gdx.audio.newMusic(Gdx.files.internal("I_Do_not_Understand.mp3"));
        musicGameOver = Gdx.audio.newMusic(Gdx.files.internal("8-bit-music.mp3"));
        Texture explosion = new Texture(Gdx.files.internal("explosao2.gif"));
        explosionAnimation = new Animation(0.06f, new TextureRegion(explosion, 0, 0, 100, 100), new TextureRegion(explosion, 100, 0, 100, 100), new TextureRegion(explosion, (int) HttpStatus.SC_OK, 0, 100, 100), new TextureRegion(explosion, (int) HttpStatus.SC_MULTIPLE_CHOICES, 0, 100, 100), new TextureRegion(explosion, (int) HttpStatus.SC_BAD_REQUEST, 0, 100, 100), new TextureRegion(explosion, (int) HttpStatus.SC_INTERNAL_SERVER_ERROR, 0, 100, 100));
        explosionAnimation.setPlayMode(PlayMode.NORMAL);

        fontNewGame = new BitmapFont(Gdx.files.internal("text.fnt"));
        fontNewGame.getData().setScale(0.25f, -0.25f);
        newGame = new Rectangle();
        about = new Rectangle();
        mainMenu = new Rectangle();
        fontAbout = new BitmapFont(Gdx.files.internal("text.fnt"));
        fontAbout.getData().setScale(0.25f,-0.25f);

        fontMainMenu = new BitmapFont(Gdx.files.internal("text.fnt"));
        fontMainMenu.getData().setScale(0.25f, -0.25f);
        fontLevelScore = new BitmapFont(Gdx.files.internal("text.fnt"));
        fontLevelScore.getData().setScale(0.1f, -0.1f);
        fontLevelScore.setColor(Color.CYAN);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(0.25f, -0.25f);
        shadow2 = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow2.getData().setScale(0.1f, -0.1f);

        prefs = Gdx.app.getPreferences("XGame");
        if (!prefs.contains("highScore"))
        {
            prefs.putInteger("highScore", 0);
        }
    }

    public static void dispose() {
        explosion.dispose();
        intro.dispose();
        musicGameOver.dispose();
        musicPlayGame.dispose();
        fontNewGame.dispose();
        shadow.dispose();
        shadow2.dispose();
        fontAbout.dispose();
        fontLevelScore.dispose();
        fontMainMenu.dispose();
        textureRoad.dispose();
        explosionAnimation = null;
        regionMenu = null;
        texturePlayerCar = null;
        textureRivalCar = null;
        newGame = null;
        about = null;
        mainMenu = null;
        prefs = null;
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }
}
