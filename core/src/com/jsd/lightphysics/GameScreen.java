package com.jsd.lightphysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;

public class GameScreen extends AbstractGameScreen{
	
	private GameStage gameStage;
	private HUDStage  hud;
	private LightPhysics game;
	private Music music;
	
	public GameScreen(LightPhysics g, int level)
	{
		super(g);
		gameStage = new GameStage(level, g);
		game = g;
		hud = new HUDStage(gameStage.m_level, g);
		GamePreferences.instance.load();

		InputMultiplexer im = new InputMultiplexer();
		im.addProcessor(gameStage.gd);
		im.addProcessor(hud);
		Gdx.input.setInputProcessor(im);
		Gdx.app.debug("Gamescreen", String.valueOf(GamePreferences.instance.sound));
		Gdx.app.debug("Gamescreen", String.valueOf(GamePreferences.instance.volSound));
		if(GamePreferences.instance.sound){
			music = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/level_1.wav"));
			music.play();
			music.setLooping(true);
			music.setVolume(GamePreferences.instance.volSound);
		}
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
		gameStage.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		gameStage.resume();
	}

	@Override
	public void dispose() {
		gameStage.dispose();
		music.dispose();
	}
}
