package com.jsd.lightphysics;


import java.awt.Label;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


public class HUDStage extends Stage
{	
	private Table top, bottom; 
	private TextButton pause, replay, start, left, right;
	private TextureAtlas atlas;
	private Level m_level;
	
	// next level popup box
	private Window winOptions;
	private TextButton btnWinOptSave;
	private TextButton btnWinOptCancel;
	private Vector2 SCREEN;
	private LightPhysics game;
	private Skin skin;
	private Window pauseWindow;
	private Window levelCompleteMessageWindow;
	private TextureAtlas menuaAtlas;
	private Window gameOverMessageWindow;
	private Sound levelCompleteSound;
	private TextButton pauseVolumeBtn;
	private GamePreferences prefs;
	private float defaultVolume;
	
	public HUDStage(Level lv, LightPhysics g)
	{
		m_level = lv;
		game = g;
		SCREEN = Assets.instance.queryScreen();
		atlas = Assets.instance.getHUDAtlas();
		prefs = GamePreferences.instance;
		prefs.load();
		defaultVolume = prefs.volSound;
		init();
		
		Gdx.input.setCatchBackKey(true);
	}
	
	public void init()
	{
		top = new Table();
		bottom = new Table();
		String locRoot = "data/ui/uiskin.json";
		switch(Gdx.app.getType())
		{
			case Desktop:
				locRoot = Gdx.files.getLocalStoragePath() + locRoot;
				break;
			case Android:
				break;
		}
		skin = new Skin(Gdx.files.internal(locRoot));
		menuaAtlas = Assets.instance.getMenuAtlas();
		
		replay = AssetFactory.createButton(atlas, Constants.BTN_REPLAY_UP, Constants.BTN_REPLAY_DOWN, true);
		pause = AssetFactory.createButton(atlas, Constants.BTN_PAUSE_UP, Constants.BTN_PAUSE_DOWN, true);
		start = AssetFactory.createButton(atlas, Constants.BTN_START_UP, Constants.BTN_START_DOWN, true);
		left = AssetFactory.createButton(atlas, Constants.BTN_LEFT_UP, Constants.BTN_LEFT_DOWN, true);
		right = AssetFactory.createButton(atlas, Constants.BTN_RIGHT_UP, Constants.BTN_RIGHT_DOWN, true);
		
		start.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				m_level.launchPhoton();
			}
		});
		
		left.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				m_level.antiClockWiseRotate();
				return;
			}
		});
		
		
		right.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				m_level.clockwiseRotate();
				return;
			}
		});
		
		pause.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				game.pause();
				pauseWindow.setVisible(true);
				return;
			}
		});
		
		
		float h = start.getMinWidth();
		top.setBounds(0, SCREEN.y-h, SCREEN.x, h);
		bottom.setBounds(0, 0, SCREEN.x, h*2);

		top.add(pause).padRight(0.84f*SCREEN.x);
		top.add(replay);
		bottom.add();
		bottom.add();
		bottom.add(start).padLeft(0.84f*SCREEN.x);
		bottom.row();
		bottom.add(left);
		bottom.add();
		bottom.add(right).padLeft(0.84f*SCREEN.x);
		
		/* pause screen screen ::: need to change BackUp and BackDown */
		
		String pauseVolumeDefaultBtn = "SoundUp";
		String pauseVolumeActiveBtn = "SoundDown";
		if(!prefs.sound){
			pauseVolumeDefaultBtn = "SoundDown";
			pauseVolumeActiveBtn = "SoundUp";
		}
		
		TextButton pauseGameCloseBtn = 
				AssetFactory.createButton(atlas, 
				"BackUp", "BackDown", false);
		TextButton pauseCloseBtn = 
				AssetFactory.createButton(atlas, 
				"QuitUp", "QuitDown", false);
		pauseVolumeBtn = 
				AssetFactory.createButton(atlas, 
				pauseVolumeDefaultBtn, pauseVolumeActiveBtn, false);
		
		TextButton proceedToNextLevelBtn = 
				AssetFactory.createButton(menuaAtlas, 
				Constants.BTN_OPT_UP, Constants.BTN_OPT_DOWN, false);
		TextButton gameOverRestartBtn = 
				AssetFactory.createButton(menuaAtlas, 
				Constants.BTN_OPT_UP, Constants.BTN_OPT_DOWN, false);
		
		pauseGameCloseBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.resume();
				pauseWindow.setVisible(false);
			}
		});
		
		pauseVolumeBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				Drawable temp = pauseVolumeBtn.getStyle().up;
				pauseVolumeBtn.getStyle().up = pauseVolumeBtn.getStyle().down;
				pauseVolumeBtn.getStyle().down = temp;
				if(prefs.sound){
					
				}else{
					
				}
			}
		});
		
		pauseCloseBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.getScreen().dispose();
				game.setScreen(new MenuScreen(game));
			}
		});
		
		proceedToNextLevelBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				levelCompleteMessageWindow.setVisible(false);
				if(!m_level.isGameOver){
					m_level.nextLevel();
					game.resume();
				}else{
					gameOverMessageWindow.setVisible(true);
				}
			}
		});
		
		gameOverRestartBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MenuScreen(game));
			}
		});
		
		Stack pauseWindowStack = new Stack();
		Table pauseTable = new Table();
		Image heading = AssetFactory.createImage(atlas, "PauseText");
		pauseTable.add(heading).padBottom(75.0f).colspan(3);
		pauseTable.row();
		pauseTable.add(pauseGameCloseBtn);
		pauseTable.add(pauseCloseBtn);
		pauseTable.add(pauseVolumeBtn);
		
		Image bg = AssetFactory.createImage(atlas, "Panel");
		pauseWindowStack.add(bg);
		pauseWindowStack.add(pauseTable);
		
		pauseWindow = new Window("", skin);
		pauseWindow.padTop(64);
		pauseWindow.setColor(1, 1, 1, 1);
		pauseWindow.add(pauseWindowStack);
		pauseWindow.setSize(this.getWidth(), this.getHeight());
		//pauseWindow.setPosition(this.getWidth() / 2 - pause.getWidth()/2, this.getHeight() / 2 - pause.getHeight()/2);
		pauseWindow.setVisible(false);
		
		Stack levelCompleteWindowStack = new Stack();
		Table levelCompleteTable = new Table();
		Image headingLevelComplete = AssetFactory.createImage(atlas, "CompleteText");
		
		levelCompleteTable.add(headingLevelComplete).padBottom(75.0f).colspan(2);
		levelCompleteTable.row();
		levelCompleteTable.add(proceedToNextLevelBtn);
		
		Image bgLevelComplete = AssetFactory.createImage(atlas, "Panel");
		levelCompleteWindowStack.add(bgLevelComplete);
		levelCompleteWindowStack.add(levelCompleteTable);
		
		levelCompleteMessageWindow = new Window("", skin);
		levelCompleteMessageWindow.padTop(64);
		levelCompleteMessageWindow.add(levelCompleteWindowStack);
		levelCompleteMessageWindow.setSize(this.getWidth(), this.getHeight());
//		levelCompleteMessageWindow.setPosition(this.getWidth() / 2 - levelCompleteMessageWindow.getWidth()/2, this.getHeight() / 2 - pause.getHeight()/2);
		levelCompleteMessageWindow.setVisible(false);
		
		gameOverMessageWindow = new Window("Game over", skin);
		gameOverMessageWindow.padTop(64);
		gameOverMessageWindow.add(gameOverRestartBtn);
		gameOverMessageWindow.setSize(this.getWidth(), this.getHeight());
//		gameOverMessageWindow.setPosition(this.getWidth() / 2 - levelCompleteMessageWindow.getWidth()/2, this.getHeight() / 2 - pause.getHeight()/2);
		gameOverMessageWindow.setVisible(false);
		
		
		this.addActor(top);
		this.addActor(bottom);
		this.addActor(pauseWindow);
		this.addActor(levelCompleteMessageWindow);
		this.addActor(gameOverMessageWindow);
		
		levelCompleteSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/level_complete_sound.wav"));
	}
	
	public void render()
	{
		this.act();
		this.draw();
		
		if(m_level.isSolved)
		{
			game.pause();
			levelCompleteMessageWindow.setVisible(true);
		}
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			backToMenu();
		}

		return false;
	}
	private void backToMenu() {
		// switch to menu screen
//		game.setScreen(new MenuScreen(game));
		game.pause();
		pauseWindow.setVisible(true);
	}	 
}