package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class HUDStage extends Stage
{
	
	private OrthographicCamera guiCamera;
	private SpriteBatch batch;
	
	private Table table; 
	private TextButton pause, replay;
	private Skin skin; 
	private TextureAtlas atlas;
	
	public HUDStage()
	{
		init();
	}
	
	public void init()
	{

		atlas = new TextureAtlas(Gdx.files.internal("data/ui/HUD.pack"));
		skin = new Skin(atlas);
		table = new Table(skin);
		BitmapFont black = new BitmapFont();
		
		TextButtonStyle pauseButtonStyle = new TextButtonStyle();
		float w = (skin.getDrawable("PauseNormal").getMinWidth() / 640) * Gdx.graphics.getWidth() ;
		float h = (skin.getDrawable("PauseNormal").getMinHeight() / 480) * Gdx.graphics.getHeight();
		
		Drawable pauseUp = skin.getDrawable("PauseNormal");
		Drawable pauseDown = skin.getDrawable("PauseDown");
		Drawable replayUp = skin.getDrawable("ReplayNormal");
		Drawable replayDown= skin.getDrawable("ReplayDown");
		
		pauseUp.setMinWidth(w*0.5f);
		pauseUp.setMinHeight(h*0.5f);
		pauseDown.setMinWidth(w*0.5f);
		pauseDown.setMinHeight(h*0.5f);
		replayUp.setMinWidth(w*0.5f);
		replayUp.setMinHeight(h*0.5f);
		replayDown.setMinWidth(w*0.5f);
		replayDown.setMinHeight(h*0.5f);
		
		
		pauseButtonStyle.up = pauseUp;
		pauseButtonStyle.down = pauseDown;
		pauseButtonStyle.pressedOffsetX = 1;
		pauseButtonStyle.pressedOffsetY = -1;
		pauseButtonStyle.font = black;
		
		pause = new TextButton("", pauseButtonStyle);
		
		TextButtonStyle replayButtonStyle = new TextButtonStyle();
		replayButtonStyle.up = replayUp;
		replayButtonStyle.down = replayDown;
		replayButtonStyle.pressedOffsetX = 1;
		replayButtonStyle.pressedOffsetY = -1;
		replayButtonStyle.font = black;
		
		
		replay = new TextButton("", replayButtonStyle);
		
		
		table.setBounds(0,  0.85f*Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 0.15f*Gdx.graphics.getHeight());
		table.align(Align.right);
		//table.debug();
		table.row();
		table.add(replay);
		table.add(pause);
		this.addActor(table);
	}
	
	public void render()
	{
		this.act();
		this.draw();
	}
	 
}
