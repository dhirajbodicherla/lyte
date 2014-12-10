package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class LightPhysics extends Game{

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		//Assets.instance.init(new AssetManager());
		setScreen(new MenuScreen(this));
	}

}
