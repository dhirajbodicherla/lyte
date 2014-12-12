package com.jsd.lyte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;

public class GameScreen extends AbstractGameScreen{
	
	private GameStage gameStage;
	private HUDStage  hud;
	private LightPhysics game;
	
	public GameScreen(LightPhysics g, int level)
	{
		super(g);
		gameStage = new GameStage(level, g);
		game = g;
		hud = new HUDStage(gameStage.m_level, g);
		MyGdxGame mg = new MyGdxGame();

		InputMultiplexer im = new InputMultiplexer();
		im.addProcessor(gameStage.gd);
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
		gameStage.resize(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.input.setCatchBackKey(false);
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
