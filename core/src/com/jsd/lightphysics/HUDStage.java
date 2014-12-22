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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


public class HUDStage extends Stage
{	
	private Table top, bottom, leftTop, leftBottom, rightTop, rightBottom;
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
	private Skin helpSkin;
	private Window pauseWindow;
	private Window levelCompleteMessageWindow;
	private Window gameOverMessageWindow;
	private Window hintsWindow;
	private TextureAtlas menuAtlas;
	private Sound levelCompleteSound;
	private TextButton pauseVolumeBtn;
	private GamePreferences prefs;
	private float defaultVolume;
	private boolean isMuted = false;
	private Label hintsLabel;
	private Hints hints;
	
	public HUDStage(Level lv, LightPhysics g)
	{
		m_level = lv;
		game = g;
		hints = new Hints(this);
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
		leftTop = new Table();
		leftBottom = new Table();
		rightTop = new Table();
		rightBottom = new Table();
		
		String locRoot = "data/ui/uiskin.json";
		skin = new Skin(Gdx.files.internal(locRoot));
		menuAtlas = new TextureAtlas(Gdx.files.internal("data/ui/Menu.pack"));
		
		helpSkin = new Skin(Gdx.files.internal("data/ui/uimenuskin.json"));
		
		Vector2 SCREEN = Assets.instance.queryScreen();
		
		replay = AssetFactory.createButton(atlas, Constants.BTN_REPLAY_UP, Constants.BTN_REPLAY_DOWN, true);
		pause = AssetFactory.createButton(atlas, Constants.BTN_PAUSE_UP, Constants.BTN_PAUSE_DOWN, true);
		start = AssetFactory.createButton(atlas, Constants.BTN_START_UP, Constants.BTN_START_DOWN, true);
		left = AssetFactory.createButton(atlas, Constants.BTN_LEFT_UP, Constants.BTN_LEFT_DOWN, true);
		right = AssetFactory.createButton(atlas, Constants.BTN_RIGHT_UP, Constants.BTN_RIGHT_DOWN, true);
		
		start.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				hints.hideHint();
				m_level.launchPhoton();
			}
		});
		
		replay.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				m_level.replay();
				updateHints();
				pauseWindow.setVisible(false);
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
		
		leftTop.setBounds(0, 0, SCREEN.x, SCREEN.y);
		leftBottom.setBounds(0, 0, SCREEN.x, SCREEN.y);
		rightTop.setBounds(0, 0, SCREEN.x, SCREEN.y);
		rightBottom.setBounds(0, 0, SCREEN.x, SCREEN.y);
		
		leftTop.add(pause);
		leftTop.top().left();
		
		leftBottom.add(left);
		leftBottom.add(right);
		leftBottom.left().bottom();
		leftBottom.setVisible(false);
		
		rightBottom.add(start);
		rightBottom.right().bottom();
		rightBottom.setVisible(true);

		/* pause screen screen ::: need to change BackUp and BackDown */
		
		String pauseVolumeDefaultBtn = "SoundUp";
		String pauseVolumeActiveBtn = "SoundDown";
		if(!prefs.sound){
			pauseVolumeDefaultBtn = "SoundDown";
			pauseVolumeActiveBtn = "SoundUp";
			isMuted = true;
		}
		
		TextButton pauseGameCloseBtn = 
				AssetFactory.createButton(atlas, 
				"BackUp", "BackDown", false);
		TextButton levelGameRepeatBtn = 
				AssetFactory.createButton(atlas, 
				Constants.BTN_REPLAY_UP, Constants.BTN_REPLAY_DOWN, false);
		TextButton pauseCloseBtn = 
				AssetFactory.createButton(atlas, 
				"QuitUp", "QuitDown", false);
		pauseVolumeBtn = 
				AssetFactory.createButton(atlas, 
				pauseVolumeDefaultBtn, pauseVolumeActiveBtn, false);
		
		TextButton proceedToNextLevelBtn = 
				AssetFactory.createButton(atlas, 
				Constants.BTN_NEXT_UP, Constants.BTN_NEXT_DOWN, false);
		TextButton gameOverRestartBtn = 
				AssetFactory.createButton(menuAtlas, 
				Constants.BTN_QUIT_UP, Constants.BTN_QUIT_DOWN, false);
		
		pauseGameCloseBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.resume();
				pauseWindow.setVisible(false);
				return;
			}
		});
		levelGameRepeatBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
//				game.getScreen().dispose();
//				game.setScreen(new MenuScreen(game));
				m_level.replay();
				game.resume();
				levelCompleteMessageWindow.setVisible(false);
				updateHints();
				return;
			}
		});
		
		pauseVolumeBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				Drawable temp = pauseVolumeBtn.getStyle().up;
				pauseVolumeBtn.getStyle().up = pauseVolumeBtn.getStyle().down;
				pauseVolumeBtn.getStyle().down = temp;
				
				isMuted = !isMuted;
				
				if(isMuted){
					((GameScreen)(game.getScreen())).music.stop();
				}else{
					((GameScreen)(game.getScreen())).music.play();
				}
				return;
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
					updateHints();
					game.resume();
				}else{
					gameOverMessageWindow.setVisible(true);
				}
				return;
			}
		});
		
		gameOverRestartBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.getScreen().dispose();
				game.setScreen(new MenuScreen(game));
			}
		});
		
		Stack pauseWindowStack = new Stack();
		Table pauseTable = new Table();
		Image heading = AssetFactory.createImage(atlas, "PauseText", false);
		pauseTable.add();
		pauseTable.add(heading).padBottom(0.05f * SCREEN.y);
		pauseTable.row();
		pauseTable.add(pauseGameCloseBtn);
		pauseTable.add(replay);
		pauseTable.add(pauseCloseBtn);
		pauseTable.add(pauseVolumeBtn);	
		pauseTable.setFillParent(true);
		
		Image bg = AssetFactory.createImage(atlas, "Panel", false);
		bg.setColor(1.0f, 1.0f, 1.0f, 1);
		pauseWindowStack.add(bg);
		pauseWindowStack.add(pauseTable);
		
		pauseWindow = new Window("", skin);
		pauseWindow.padTop(64);
		pauseWindow.add(pauseWindowStack);
		pauseWindow.setSize(SCREEN.x, SCREEN.y);
		pauseWindow.setColor(255, 255, 255, 1);
		//pauseWindow.setPosition(this.getWidth() / 2 - pause.getWidth()/2, this.getHeight() / 2 - pause.getHeight()/2);
		pauseWindow.setVisible(false);
		
		Stack levelCompleteWindowStack = new Stack();
		Table levelCompleteTable = new Table();
		Image headingLevelComplete = AssetFactory.createImage(atlas, "CompleteText", false);
		
		levelCompleteTable.add(headingLevelComplete).colspan(2).padBottom(0.1f * SCREEN.y);
		levelCompleteTable.row();
		levelCompleteTable.add(levelGameRepeatBtn);
		levelCompleteTable.add(proceedToNextLevelBtn);
		
		Image bgLevelComplete = AssetFactory.createImage(atlas, "Panel", false);
		levelCompleteWindowStack.add(bgLevelComplete);
		levelCompleteWindowStack.add(levelCompleteTable);
		
		levelCompleteMessageWindow = new Window("", skin);
		levelCompleteMessageWindow.padTop(0.01f * SCREEN.y);
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
		
		
//		this.addActor(top);
//		this.addActor(bottom);
		this.addActor(leftTop);
		this.addActor(leftBottom);
		this.addActor(rightTop);
		this.addActor(rightBottom);
		
		this.addActor(pauseWindow);
		this.addActor(levelCompleteMessageWindow);
		this.addActor(gameOverMessageWindow);
		
		levelCompleteSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/level_complete_sound.wav"));
		
//		hints.draw();
		updateHints();
	}
	
	public void render()
	{
		this.act();
		this.draw();
		
		if(m_level.isSolved)
		{
			if(m_level.isGameOver){
				game.pause();
				gameOverMessageWindow.setVisible(true);
			}else{
				game.pause();
				levelCompleteMessageWindow.setVisible(true);
			}
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
		if(!pauseWindow.isVisible()){
			game.pause();
			pauseWindow.setVisible(true);
		}
	}
	
	private void showHints(){
		switch(m_level.currentLevel){
			case 0:
				hints.showHint("Click the launch button to shoot towards target");
				break;
			case 1:
				hints.showHint("Bubble telescope helps you direct light to target");
				break;
			case 2:
				hints.showHint("Select the green bubble telescope and use the controls to rotate it");
				break;
			case 3:
				hints.showHint("Hold blue bubble telescope to move around");
				break;
			case 5:
				hints.showHint("This is a black hole. Light literally bends");
				break;
			case 6:
				hints.showHint("White bubble telescope can be moved around and rotated.");
				break;
			default:
				break;
		}
	}
	
	private void updateHints(){
		showHints();
		switch(m_level.currentLevel){
			case 0: 
			case 1:
				leftBottom.setVisible(false);
			break;
			case 2:
				leftBottom.setVisible(true);
				leftBottom.addAction( Actions.sequence( Actions.fadeOut( 0.0001f ), 
										Actions.fadeIn( 4f )));
				break;
			default:
				leftBottom.setVisible(true);
			break;
		}
	}
}