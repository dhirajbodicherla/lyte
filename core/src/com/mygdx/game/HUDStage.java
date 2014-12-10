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
	private TextButton pause, replay, start, left, right;
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
		float w = (skin.getDrawable("PauseUp").getMinWidth() / 640) * Gdx.graphics.getWidth() ;
		float h = (skin.getDrawable("PauseUp").getMinHeight() / 480) * Gdx.graphics.getHeight();
		
		TextButtonStyle pauseButtonStyle = new TextButtonStyle();
		TextButtonStyle replayButtonStyle = new TextButtonStyle();
		TextButtonStyle startButtonStyle = new TextButtonStyle();
		TextButtonStyle rightButtonStyle = new TextButtonStyle();
		TextButtonStyle leftButtonStyle = new TextButtonStyle();
		
		
		Drawable pauseUp = skin.getDrawable("PauseUp");
		Drawable pauseDown = skin.getDrawable("PauseDown");
		Drawable replayUp = skin.getDrawable("ReplayUp");
		Drawable replayDown= skin.getDrawable("ReplayDown");
		Drawable startUp = skin.getDrawable("StartUp");
		Drawable startDown = skin.getDrawable("StartDown");
		Drawable leftUp = skin.getDrawable("LeftUp");
		Drawable leftDown= skin.getDrawable("LeftDown");
		Drawable rightUp = skin.getDrawable("RightUp");
		Drawable rightDown = skin.getDrawable("RightDown");
		
		
		pauseButtonStyle.up = pauseUp;
		pauseButtonStyle.down = pauseDown;
		pauseButtonStyle.pressedOffsetX = 1;
		pauseButtonStyle.pressedOffsetY = -1;
		pauseButtonStyle.font = black;
		
		replayButtonStyle.up = replayUp;
		replayButtonStyle.down = replayDown;
		replayButtonStyle.pressedOffsetX = 1;
		replayButtonStyle.pressedOffsetY = -1;
		replayButtonStyle.font = black;
		
		startButtonStyle.up = startUp;
		startButtonStyle.down = startDown;
		startButtonStyle.pressedOffsetX = 1;
		startButtonStyle.pressedOffsetY = -1;
		startButtonStyle.font = black;
		
		leftButtonStyle.up = leftUp;
		leftButtonStyle.down = leftDown;
		leftButtonStyle.pressedOffsetX = 1;
		leftButtonStyle.pressedOffsetY = -1;
		leftButtonStyle.font = black;
		
		rightButtonStyle.up = rightUp;
		rightButtonStyle.down = rightDown;
		rightButtonStyle.pressedOffsetX = 1;
		rightButtonStyle.pressedOffsetY = -1;
		rightButtonStyle.font = black;
		
		
		replay = new TextButton("", replayButtonStyle);
		pause = new TextButton("", pauseButtonStyle);
		start = new TextButton("", startButtonStyle);
		left = new TextButton("", leftButtonStyle);
		right = new TextButton("", rightButtonStyle);
		
		
		replay.align(Align.right);
		pause.align(Align.left);
		start.align(Align.right);
		right.align(Align.right);
		left.align(Align.left);
		
		//table.setBounds(0,  0.85f*Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 0.15f*Gdx.graphics.getHeight());
		//table.setBounds(0, Gdx.graphics.getHeight()-h, Gdx.graphics.getWidth(), h);
		table.setFillParent(true);
		//table.align(Align.right);
		//table.debug();
		table.add(pause);
		table.add(replay);
		table.add(start);
		
		table.add(left);

		table.add(right);
		table.debug();
		this.addActor(table);
	}
	
	public void render()
	{
		this.act();
		this.draw();
	}
	 
}
