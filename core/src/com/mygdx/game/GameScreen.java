package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;

public class GameScreen extends AbstractGameScreen{
	
	private GameStage gameStage;
	private HUDStage  hud;
	
	public GameScreen(LightPhysics g)
	{
		super(g);
		gameStage = new GameStage();
		hud = new HUDStage();
		InputMultiplexer im = new InputMultiplexer();
	    GestureDetector gd = new GestureDetector(gameStage);
		im.addProcessor(gameStage);
		im.addProcessor(gd);
		im.addProcessor(hud);
		Gdx.input.setInputProcessor(im);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); 	//Black Background
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameStage.draw();
		hud.render();
		
		
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
		gameStage.dispose();
	}

}
