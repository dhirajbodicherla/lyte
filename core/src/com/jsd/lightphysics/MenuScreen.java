package com.jsd.lightphysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class MenuScreen extends AbstractGameScreen {

	private Stage stage;
	private Music music;
	private Window winOptions;
	TextureAtlas menuAtlas;
	private Skin skin;
	private CheckBox chkSound;
	private Slider sldSound;
	private CheckBox chkMusic;
	private Slider sldMusic;
	private SelectBox selCharSkin;
	private Image imgCharSkin;
	private CheckBox chkShowFpsCounter;

	public MenuScreen(LightPhysics game) {
		super(game);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		init();
	}

	public void init() {
		music = Gdx.audio.newMusic(Gdx.files
				.internal("data/sounds/menu_screen.wav"));
		music.play();
		music.setLooping(true);
		music.setVolume(0.1f);
		menuAtlas = new TextureAtlas(Gdx.files.internal("data/ui/Menu.pack"));
		String locRoot = "data/ui/uimenuskin.json";
		switch (Gdx.app.getType()) {
		case Desktop:
			locRoot = Gdx.files.getLocalStoragePath() + locRoot;
			break;
		case Android:
			break;
		}
		skin = new Skin(Gdx.files.internal(locRoot));
		skin.getFont("default").setScale(0.25f, 0.25f);
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
		Table center = new Table();
		Table bottomStrip = new Table();
		TextButton playBtn = AssetFactory.createButton(menuAtlas,
				Constants.BTN_PLAY_UP, Constants.BTN_PLAY_DOWN, false);
		TextButton optBtn = AssetFactory.createButton(menuAtlas,
				Constants.BTN_OPT_UP, Constants.BTN_OPT_DOWN, false);
		TextButton creditsBtn = AssetFactory.createButton(menuAtlas,
				Constants.BTN_CREDITS_UP, Constants.BTN_CREDITS_DOWN, false);
		TextButton helpBtn = AssetFactory.createButton(menuAtlas,
				Constants.BTN_HELP_UP, Constants.BTN_HELP_DOWN, false);
		TextButton quitBtn = AssetFactory.createButton(menuAtlas,
				Constants.BTN_QUIT_UP, Constants.BTN_QUIT_DOWN, false);
		playBtn.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new LevelSelectScreen(game));
			}
		});

		optBtn.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				winOptions.setVisible(true);
				return;
			}
		});

		quitBtn.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		
		Image logo = AssetFactory.createImage(menuAtlas, 
											  Constants.TEXT_GAME_LOGO, false);

		float h = quitBtn.getMinHeight();
		center.setBounds(0, 0, SCREEN.x, SCREEN.y);
		bottomStrip.setBounds(0, 0, SCREEN.x, h);

		center.add(logo);
		center.row();

		center.add(playBtn).padTop(0.1f*SCREEN.y);
		center.row();

		center.add(optBtn);
		center.row();

		center.add(creditsBtn);
		center.row();

		bottomStrip.add(quitBtn);
		bottomStrip.add();
		bottomStrip.add(helpBtn).padLeft(0.84f * SCREEN.x);

		layer.align(Align.center);
		layer.add(center);
		layer.row();
		layer.add(bottomStrip).padTop(0.10f * SCREEN.y);
		return layer;
	}

	private void buildStage() {

		Table foreground = buildForeground();
		Table background = buildBackground();
		Table layerOptionsWindow = buildOptionsWindowLayer();
		Vector2 SCREEN = Assets.instance.queryScreen();

		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(SCREEN.x, SCREEN.y);
		stack.add(background);
		stack.add(foreground);
		stage.addActor(layerOptionsWindow);
	}

	private Table buildBackground() {
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

	private Table buildOptionsWindowLayer() {
		winOptions = new Window("Options", skin);
		// + Audio Settings: Sound/Music CheckBox and Volume Slider
		winOptions.add(buildOptWinAudioSettings()).row();
		// + Character Skin: Selection Box (White, Gray, Brown)
		// winOptions.add(buildOptWinSkinSelection()).row();
		// + Debug: Show FPS Counter
		// winOptions.add(buildOptWinDebug()).row();
		// + Separator and Buttons (Save, Cancel)
		// winOptions.add(buildOptWinButtons()).pad(10, 0, 10, 0);
		// Make options window slightly transparent
		winOptions.setColor(255, 255, 255, 1f);
		// Hide options window by default
		winOptions.setVisible(false);
		// Let TableLayout recalculate widget sizes and positions
		winOptions.pack();
		// Move options window to bottom right corner
		// winOptions.setPosition(Constants.VIEWPORT_GUI_WIDTH -
		// winOptions.getWidth() - 50, 50);
		winOptions.setSize(Gdx.graphics.getWidth() / 2f,
				Gdx.graphics.getHeight() / 2f);
		winOptions.setPosition(
				Gdx.graphics.getWidth() / 2f - winOptions.getWidth() / 2f,
				Gdx.graphics.getHeight() / 2f - winOptions.getWidth() / 2f);
		loadSettings();
		return winOptions;
	}

	private Table buildOptWinAudioSettings() {
		Table tbl = new Table();
//		tbl.pad(10, 10, 0, 10);
		// + Checkbox, "Sound" label, sound volume slider
		chkSound = new CheckBox(" Sound", skin);
		tbl.add(chkSound).padBottom(20.0f);

		sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skin);
		sldSound.setValue(0.6f);
		tbl.add(sldSound).padBottom(20.0f);
		tbl.row();

		// + Separator
		Label lbl = null;
		lbl = new Label("", skin);
		lbl.setColor(0.75f, 0.75f, 0.75f, 1);
		// lbl.setStyle(new LabelStyle(lbl.getStyle()));
		lbl.getStyle().background = skin.newDrawable("white");
		tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 0, 0, 1);
		tbl.row();
		lbl = new Label("", skin);
		lbl.setColor(0.5f, 0.5f, 0.5f, 1);
		// lbl.setStyle(new LabelStyle(lbl.getStyle()));
		lbl.getStyle().background = skin.newDrawable("white");
		tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 1, 5, 0);
		tbl.row();

		TextButton btnWinOptSave = new TextButton("Save", skin);
		tbl.add(btnWinOptSave).padRight(30).padTop(10f);
		btnWinOptSave.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				onSaveClicked();
				return;
			}
		});

		// + Cancel Button with event handler
		TextButton btnWinOptCancel = new TextButton("Cancel", skin);
		tbl.add(btnWinOptCancel).padTop(10f);
		btnWinOptCancel.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				onCancelClicked();
				return;
			}
		});

		return tbl;
	}

	private void loadSettings() {
		GamePreferences prefs = GamePreferences.instance;
		prefs.load();
		chkSound.setChecked(prefs.sound);
		sldSound.setValue(prefs.volSound);
//		chkMusic.setChecked(prefs.music);
//		sldMusic.setValue(prefs.volMusic);
		// selCharSkin.setse.setSelection(prefs.charSkin);
		// onCharSkinSelected(prefs.charSkin);
//		chkShowFpsCounter.setChecked(prefs.showFpsCounter);
	}

	private void saveSettings() {
		GamePreferences prefs = GamePreferences.instance;
		prefs.sound = chkSound.isChecked();
		prefs.volSound = sldSound.getValue();
//		prefs.music = chkMusic.isChecked();
//		prefs.volMusic = sldMusic.getValue();
//		// prefs.charSkin = selCharSkin.getSelectionIndex();
//		prefs.showFpsCounter = chkShowFpsCounter.isChecked();
		prefs.save();
	}
	
	private void onSaveClicked(){
		saveSettings();
		winOptions.setVisible(false);
	}

	private void onCancelClicked() {
		// btnMenuPlay.setVisible(true);
		// btnMenuOptions.setVisible(true);
		winOptions.setVisible(false);
		// AudioManager.instance.onSettingsUpdated();
	}

}