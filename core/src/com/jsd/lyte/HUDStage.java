package com.jsd.lyte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class HUDStage extends Stage
{	
	private Table top, bottom; 
	private TextButton pause, replay, start, left, right;
	private Skin skin; 
	private TextureAtlas atlas;
	private Level m_level;
	
	// next level popup box
	private Window winOptions;
	private TextButton btnWinOptSave;
	private TextButton btnWinOptCancel;
	private Vector2 SCREEN;
	
	public HUDStage(Level lv)
	{
		m_level = lv;
		SCREEN = Assets.instance.queryScreen();
		atlas = Assets.instance.getHUDAtlas();
		init();
//		Gdx.input.setInputProcessor(this);
	}
	
	public void init()
	{
		top = new Table();
		bottom = new Table();
		
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
			}
		});
		
		
		right.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				m_level.clockwiseRotate();
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
		
		this.addActor(top);
		this.addActor(bottom);
	}
	
	public void render()
	{
		this.act();
		this.draw();
		
		if(m_level.isSolved)
		{
			m_level.nextLevel();
		}
	}
	 
}
