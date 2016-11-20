package com.cargame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.cargame.helpers.AssetLoader;
import com.cargame.screens.GameScreen;

public class Car extends Game {

	@Override
	public void create () {
		Gdx.app.log("Car","Car started!");
        AssetLoader.load();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose ()
	{
        super.dispose();
        AssetLoader.dispose();
	}
}
