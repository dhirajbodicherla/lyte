package com.jsd.lyte;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


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
	
	public HUDStage(Level lv, LightPhysics g)
	{
		m_level = lv;
		SCREEN = Assets.instance.queryScreen();
		atlas = Assets.instance.getHUDAtlas();
		init();
		game = g;
//		Gdx.input.setInputProcessor(this);
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
		
		/* pause screen screen */
		
		TextButton pauseCloseBtn = 
				AssetFactory.createButton(menuaAtlas, 
				Constants.BTN_OPT_UP, Constants.BTN_OPT_DOWN, false);
		TextButton proceedToNextLevelBtn = 
				AssetFactory.createButton(menuaAtlas, 
				Constants.BTN_OPT_UP, Constants.BTN_OPT_DOWN, false);
		
		pauseCloseBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				pauseWindow.setVisible(false);
			}
		});
		
		proceedToNextLevelBtn.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				levelCompleteMessageWindow.setVisible(false);
				m_level.nextLevel();
			}
		});
		
		pauseWindow = new Window("PAUSE", skin);
		pauseWindow.padTop(64);
		pauseWindow.add(pauseCloseBtn);
		pauseWindow.setSize(this.getWidth() / 1.5f, this.getHeight() / 1.5f);
		pauseWindow.setPosition(this.getWidth() / 2 - pause.getWidth()/2, 
						  this.getHeight() / 2 - pause.getHeight()/2);
		pauseWindow.setVisible(false);
		
		levelCompleteMessageWindow = new Window("Level complete", skin);
		levelCompleteMessageWindow.padTop(64);
		levelCompleteMessageWindow.add(proceedToNextLevelBtn);
		levelCompleteMessageWindow.setSize(this.getWidth() / 1.5f, this.getHeight() / 1.5f);
		levelCompleteMessageWindow.setPosition(this.getWidth() / 2 - levelCompleteMessageWindow.getWidth()/2, 
						  this.getHeight() / 2 - pause.getHeight()/2);
		levelCompleteMessageWindow.setVisible(false);
		
		
		this.addActor(top);
		this.addActor(bottom);
		this.addActor(pauseWindow);
		this.addActor(levelCompleteMessageWindow);
	}
	
	public void render()
	{
		this.act();
		this.draw();
		
		if(m_level.isSolved)
		{
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
		game.setScreen(new MenuScreen(game));
	}

	 
}
