package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen{
	
	private GameStage gameStage;
	private HUDStage  hud;
	private LightPhysics game;		//app instance
	
	public GameScreen(LightPhysics g)
	{
		gameStage = new GameStage();
		hud = new HUDStage();
		InputMultiplexer im = new InputMultiplexer();
		im.addProcessor(gameStage);
		im.addProcessor(hud);
		Gdx.input.setInputProcessor(im);
		game = g;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
