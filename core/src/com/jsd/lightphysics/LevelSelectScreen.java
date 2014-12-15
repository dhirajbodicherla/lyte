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
		Vector2 SCREEN = Assets.instance.queryScreen();
		buildStage(SCREEN.x, SCREEN.y);
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(deltaTime);
		stage.draw();

	}

	private Table buildForeground(float stageW, float stageH) {
		Table main = new Table();
		Table table = new Table();
		main.debug();
		table.debug();
		table.setFillParent(true);

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
				game.setScreen(new GameScreen(game, 3));
			}
		});
		level4.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 4));
			}
		});
		level5.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 5));
			}
		});
		level6.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 6));
			}
		});
		level7.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 7));
			}
		});
		level8.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 8));
			}
		});
		level9.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 9));
			}
		});
		level10.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, 10));
			}
		});
		
		Image logo = AssetFactory.createImage(atlas, 
				  Constants.TEXT_LEVEL_SELECT, false);

		main.setBounds(0, 0, stageW, stageH);
		main.align(Align.center);
		main.add(logo).fill().expand().row();
		
		
		// layer.setDebug(true);
//		table.setBounds(0, 0, stageW, stageH);
		// layer.align(Align.center);

//		table.add(logo).fill().colspan(4).row(); //.padBottom(0.05f * stageH);
		// layer.row();
		table.add(level1);
		table.add(level2);
		table.add(level3);
		table.add(level4).row();
		table.add(level5);
		table.add(level6);
		table.add(level7);
		table.add(level8).row().padBottom(0.01f * stageH);
		table.add(level9).colspan(2);
		table.add(level10).colspan(2);
		
		main.add(table);

		return main;
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