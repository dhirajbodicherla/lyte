package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

public class LevelSelectScreen extends AbstractGameScreen {

	private Stage stage;
	private Image imgBackground;
	private Skin skin; 
	private TextureAtlas atlas;

	public LevelSelectScreen(LightPhysics game) {
		super(game);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		init();
	}
	
	public void init()
	{
		atlas = new TextureAtlas(Gdx.files.internal("data/ui/Menu.pack"));
		skin = new Skin(atlas);
		buildStage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(deltaTime);
		stage.draw();
		
	}

	private Table buildForeground(float stageW, float stageH) {
		Table layer = new Table();
		layer.setFillParent(true);
		float w = (skin.getDrawable("PlayUp").getMinWidth() / 640) * stageW;
		float h = (skin.getDrawable("PlayUp").getMinHeight() / 480) * stageH;
		
		Drawable PlayUp = skin.getDrawable("PlayUp");
		Drawable PlayDown = skin.getDrawable("PlayDown");
		Drawable OptUp = skin.getDrawable("OptUp");
		Drawable OptDown= skin.getDrawable("OptDown");
		BitmapFont black = new BitmapFont();
		
		PlayUp.setMinWidth(w);
		PlayUp.setMinHeight(h);
		PlayDown.setMinWidth(w);
		PlayDown.setMinHeight(h);
		OptUp.setMinWidth(w);
		OptUp.setMinHeight(h);
		OptDown.setMinWidth(w);
		OptDown.setMinHeight(h);
		
		TextButtonStyle playButtonStyle = new TextButtonStyle();
		playButtonStyle.up = PlayUp;
		playButtonStyle.down = PlayDown;
		playButtonStyle.pressedOffsetX = 1;
		playButtonStyle.pressedOffsetY = -1;
		playButtonStyle.font = black;
		
		TextButtonStyle optButtonStyle = new TextButtonStyle();
		optButtonStyle.up = OptUp;
		optButtonStyle.down = OptDown;
		optButtonStyle.pressedOffsetX = 1;
		optButtonStyle.pressedOffsetY = -1;
		optButtonStyle.font = black;
		
		TextButton level1 = new TextButton("Level 1", playButtonStyle);
		TextButton level2 = new TextButton("Level 2", optButtonStyle);
		TextButton level3 = new TextButton("Level 3", optButtonStyle);
		TextButton level4 = new TextButton("Level 4", optButtonStyle);
		
		level1.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 1));
			}
		});
		level2.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 2));
			}
		});
		level3.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 1));
			}
		});
		level4.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 2));
			}
		});
		
		layer.top().left();
		Image logo = new Image(skin, "Logo");
//		layer.setDebug(true);
		layer.setBounds(0, 0, stageW, stageH);
//		layer.align(Align.center);
		
//		layer.add(logo).padBottom(stageH);
//		layer.row();
		layer.add(level1);
		layer.add(level2).padTop(100.0f);
		layer.add(level3).padTop(150.0f);
		layer.add(level4).padTop(200.0f);
		
		return layer;
	}
	
	private void buildStage(float stageW, float stageH) {

		Table foreground = buildForeground(stageW, stageH);
		Table background = buildBackground(stageW, stageH);

		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(stageW, stageH);
		stack.add(background);
		stack.add(foreground);
		
	}

	private Table buildBackground(float stageW, float stageH) {
		Table layer = new Table();
		 imgBackground = new Image(skin, "MenuBackground");
		 imgBackground.setBounds(0, 0, stageW, stageH);
		 layer.add(imgBackground);
		
		 return layer;
	}

	

	@Override
	public void resize(int width, int height) {
//		stage.setViewport(new StretchViewport(68, 40));
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void pause() {
	}

}