package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MenuScreen extends AbstractGameScreen {

	private Stage stage;
	private Skin skinCanyonBunny;

	private static final String TAG = MenuScreen.class.getName();
	private Button btnMenuPlay;

	private Image imgBackground;
	private Image imgLogo;

	// debug
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;
	private boolean debugEnabled = false;
	private float debugRebuildStage;

	public MenuScreen(Game game) {
		super(game);
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// if (Gdx.input.isTouched())
		// game.setScreen(new GameScreen(game));
		if (debugEnabled) {
			debugRebuildStage -= deltaTime;
			if (debugRebuildStage <= 0) {
				debugRebuildStage = DEBUG_REBUILD_INTERVAL;
				rebuildStage();
			}
		}
		stage.act(deltaTime);
		stage.draw();
		// Table.drawDebug(stage);
	}

	private void rebuildStage() {

//		int width = Gdx.graphics.getWidth();
//		int height = Gdx.graphics.getHeight(); 
		skinCanyonBunny = new Skin( Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		//
		Table layerBackground = buildBackgroundLayer();
		Table layerControls = buildControlsLayer();
		// Table layerLogo = buildLogosLayer();
		// Table layerOptionsWindow = buildOptionsWindowLayer();
		//
		// // assemble stage for menu screen
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(layerBackground);
		stack.add(layerControls);
		// stack.add(layerLogo);
		// stack.add(layerObjects);
		// stack.add(layerLogos);
		// stage.addActor(layerOptionsWindow);
	}

	private Table buildBackgroundLayer() {
		Table layer = new Table();
		// + Background
		 imgBackground = new Image(skinCanyonBunny, "bg");
		 layer.add(imgBackground);
		return layer;
	}

	private Table buildControlsLayer() {
		Table layer = new Table();
		layer.right().top();
		// + Play Button
		btnMenuPlay = new Button(skinCanyonBunny, "play");
		layer.add(btnMenuPlay);
//		btnMenuPlay.addListener(new InputListener() {
//			public void changed(ChangeEvent event, Actor actor) {
//				onPlayClicked();
//			}
//		});
		btnMenuPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
			}
		});
		layer.row();

		// if (debugEnabled)
//		layer.debug();
		return layer;
	}

	private Table buildOptionsWindowLayer() {
		Table layer = new Table();
		return layer;
	}

	private Table buildLogosLayer() {
		/* make logo better later */
		Table layer = new Table();
		layer.left().top();
		// + Game Logo
		imgLogo = new Image(skinCanyonBunny, "logo");
		layer.add(imgLogo);
		layer.row().expandY();

		return layer;
	}

	@Override
	public void resize(int width, int height) {
//		stage.setViewport(new StretchViewport(68, 40));
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}

	@Override
	public void hide() {
		stage.dispose();
		skinCanyonBunny.dispose();
	}

	@Override
	public void pause() {
	}

}