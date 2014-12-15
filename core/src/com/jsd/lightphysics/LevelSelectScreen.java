package com.jsd.lightphysics;

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

public class LevelSelectScreen extends AbstractGameScreen {

	private Stage stage;
	private Image imgBackground;
	private Skin skin;
	private TextureAtlas atlas;
	private Music music;

	public LevelSelectScreen(LightPhysics game) {
		super(game);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		init();
	}

	public void init() {
		music = Gdx.audio.newMusic(Gdx.files
				.internal("data/sounds/level_select_sound.mp3"));
		music.play();
		music.setLooping(true);
		music.setVolume(0.6f);
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

		TextButton level1 = AssetFactory.createButton(atlas, Constants.BTN_1_UP, Constants.BTN_1_UP, false);
		TextButton level2 = AssetFactory.createButton(atlas, Constants.BTN_2_UP, Constants.BTN_2_UP, false);
		TextButton level3 = AssetFactory.createButton(atlas, Constants.BTN_3_UP, Constants.BTN_3_UP, false);
		TextButton level4 = AssetFactory.createButton(atlas, Constants.BTN_4_UP, Constants.BTN_4_UP, false);
		TextButton level5 = AssetFactory.createButton(atlas, Constants.BTN_5_UP, Constants.BTN_5_UP, false);
		TextButton level6 = AssetFactory.createButton(atlas, Constants.BTN_6_UP, Constants.BTN_6_UP, false);
		TextButton level7 = AssetFactory.createButton(atlas, Constants.BTN_7_UP, Constants.BTN_7_UP, false);
		TextButton level8 = AssetFactory.createButton(atlas, Constants.BTN_8_UP, Constants.BTN_8_UP, false);
		TextButton level9 = AssetFactory.createButton(atlas, Constants.BTN_9_UP, Constants.BTN_9_UP, false);
		TextButton level10 = AssetFactory.createButton(atlas, Constants.BTN_10_UP, Constants.BTN_10_UP, false);
		
		
		

		level1.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 1));
			}
		});
		level2.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 2));
			}
		});
		level3.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 1));
			}
		});
		level4.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 2));
			}
		});

		layer.top().left();
		Image logo = new Image(skin, "Logo");
		// layer.setDebug(true);
		layer.setBounds(0, 0, stageW, stageH);
		// layer.align(Align.center);

		// layer.add(logo).padBottom(stageH);
		// layer.row();
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
		Vector2 SCREEN = Assets.instance.queryScreen();
		Vector2 VIEWPORT = Assets.instance.queryViewport();
		String suf = Assets.instance.getSuffix();
		String ext = ".pack";
		Table layer = new Table();
		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(Constants.TEXTURE_ATLAS_BG+suf+ext));
		Skin skin = new Skin(atlas);
		Drawable background = skin.getDrawable(Constants.IMG_MENU_SCREEN+suf);
		
		float w = (background.getMinWidth()/VIEWPORT.x) * SCREEN.x;
		float h = (background.getMinHeight()/VIEWPORT.y) * SCREEN.y;
		
		background.setMinWidth(w);
		background.setMinHeight(h);
		
		Image imgBackground = new Image(background);
		
		layer.setBounds(0, 0, SCREEN.x, SCREEN.y);
		layer.add(imgBackground);


		return layer;
	}

	@Override
	public void resize(int width, int height) {
		// stage.setViewport(new StretchViewport(68, 40));
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {
		stage.dispose();
		skin.dispose();
		music.dispose();
	}

	@Override
	public void pause() {
	}

}