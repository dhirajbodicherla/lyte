package com.mygdx.game;

import com.badlogic.gdx.Game;

public class LightPhysics extends Game{
	
	GameScreen gameScreen;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		gameScreen = new GameScreen(this);
		
		this.setScreen(gameScreen);
	}

}
