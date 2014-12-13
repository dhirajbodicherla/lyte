package com.jsd.lyte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class MenuScreen extends AbstractGameScreen {

	private Stage stage;
	private Music music;
	TextureAtlas menuaAtlas;

	public MenuScreen(LightPhysics game) {
		super(game);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		init();
	}
	
	public void init()
	{
		music = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/menu_screen.wav"));
		music.play();
		music.setLooping(true);
		music.setVolume(0.6f);
		menuaAtlas = Assets.instance.getMenuAtlas();
		buildStage();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(deltaTime);
		stage.draw();
		
	}

	private Table buildForeground() {
		Vector2 SCREEN = Assets.instance.queryScreen();
		Table layer = new Table();
		TextButton playBtn = 
				AssetFactory.createButton(menuaAtlas, 
				Constants.BTN_PLAY_UP, Constants.BTN_PLAY_DOWN, false);
		TextButton optBtn = 
				AssetFactory.createButton(menuaAtlas, 
				Constants.BTN_OPT_UP, Constants.BTN_OPT_DOWN, false);
		TextButton creditsBtn = 
				AssetFactory.createButton(menuaAtlas, 
				Constants.BTN_OPT_UP, Constants.BTN_OPT_DOWN, false);
		TextButton helpBtn = 
				AssetFactory.createButton(menuaAtlas, 
				Constants.BTN_OPT_UP, Constants.BTN_OPT_DOWN, false);
		TextButton quitBtn = 
				AssetFactory.createButton(menuaAtlas, 
				Constants.BTN_OPT_UP, Constants.BTN_OPT_DOWN, false);
		playBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new LevelSelectScreen(game));
			}
		});
		
		quitBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		
		Image logo = AssetFactory.createImage(menuaAtlas, 
											  Constants.IMG_GAME_LOGO);
//		layer.debug();
		layer.setBounds(0, 0, SCREEN.x, SCREEN.y);
//		layer.align(Align.center);
		layer.add(logo);//.padTop(100.0f);
		layer.row();
		layer.add(playBtn);//.padTop(150.0f);
		layer.row();
		layer.add(optBtn);//.padTop(200.0f);
		layer.row();
		layer.add(creditsBtn);//.padTop(250.0f);
		layer.row();
		layer.add(helpBtn);//.padTop(300.0f);
		layer.row();
		layer.add(quitBtn);//.padTop(350.0f);
		
		
		return layer;
	}
	
	private void buildStage() {

		Table foreground = buildForeground();
		Table background = buildBackground();
		Vector2 SCREEN = Assets.instance.queryScreen();

		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(SCREEN.x, SCREEN.y);
		stack.add(background);
		stack.add(foreground);
		
	}

	private Table buildBackground() {
		Vector2 SCREEN = Assets.instance.queryScreen();
		Table layer = new Table();
		Image imgBackground =  AssetFactory.createImage(menuaAtlas, 
				 								   Constants.IMG_GAME_MENU);
		imgBackground.setBounds(0, 0, SCREEN.x, SCREEN.y);
		layer.add(imgBackground);
		
		return layer;
	}

	

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		stage.dispose();
		music.dispose();
	}

	@Override
	public void pause() {
		
	}

}