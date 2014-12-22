package com.jsd.lightphysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends AbstractGameScreen{
	
	private GameStage gameStage;
	private HUDStage  hud;
	public Music music;
	private Stage stage;
	private Skin skin;
	
	public GameScreen(LightPhysics g, int level)
	{
		super(g);
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		gameStage = new GameStage(level, g);
		skin = new Skin(Gdx.files.internal("data/ui/uimenuskin.json"));
		GamePreferences.instance.load();

		InputMultiplexer im = new InputMultiplexer();
		im.addProcessor(gameStage.gd);
		im.addProcessor(gameStage.hud);
		Gdx.input.setInputProcessor(im);
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
		
		gameStage.render();
		stage.act(delta);
		stage.draw();
		
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
		/* for github issue https://github.com/dhirajbodicherla/lyte/issues/20 */
//		gameStage.dispose();
		music.dispose();
	}
}
