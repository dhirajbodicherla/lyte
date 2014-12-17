package com.jsd.lightphysics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class LightPhysics extends Game{

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_NONE);
		setScreen(new SplashScreen(this));
	}

}
