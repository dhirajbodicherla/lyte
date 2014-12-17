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

public class HelpScreen extends AbstractGameScreen {

	private Stage stage;
	private Image imgBackground;
	private Skin skin;
	private TextureAtlas atlas;
	private TextureAtlas menuAtlas; 
	private Music music;

	public HelpScreen(LightPhysics game) {
		super(game);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		init();
	}

	public void init() {
		music = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/menu_screen.wav"));
		music.play();
		music.setLooping(true);
		music.setVolume(0.2f);
		atlas = new TextureAtlas(Gdx.files.internal("data/ui/Menu.pack"));
		skin = new Skin(atlas);
		Vector2 SCREEN = Assets.instance.queryScreen();
		menuAtlas = Assets.instance.getHUDAtlas();
		buildStage(SCREEN.x, SCREEN.y);
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(deltaTime);
		stage.draw();

	}
	
	private Table buildForeground(float stageW, float stageH){
		TextButton backToMenuButton = AssetFactory.createButton(menuAtlas, "BackUp", "BackDown", false);
		Table table = new Table();
		
		backToMenuButton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MenuScreen(game));
				return;
			}
		});
		
		table.add(backToMenuButton);
		table.bottom().left();
		
		return table;
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