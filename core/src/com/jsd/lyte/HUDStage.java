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
		init();
//		Gdx.input.setInputProcessor(this);
	}
	
	public void init()
	{

		atlas = new TextureAtlas(Gdx.files.internal("data/ui/HUD.pack"));
		skin = new Skin(atlas);
		top = new Table(skin);
		bottom = new Table(skin);
		BitmapFont black = new BitmapFont();
		float w = (skin.getDrawable("PauseUp").getMinWidth() / 640) * SCREEN.x;
		float h = w;
		
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
		
		
		pauseUp.setMinWidth(w);
		pauseUp.setMinHeight(h);
		pauseDown.setMinWidth(w);
		pauseDown.setMinHeight(h);
		replayUp.setMinWidth(w);
		replayUp.setMinHeight(h);
		replayDown.setMinWidth(w);
		replayDown.setMinHeight(h);
		
		startUp.setMinWidth(w);
		startUp.setMinHeight(h);
		startDown.setMinWidth(w);
		startDown.setMinHeight(h);
		
		leftUp.setMinWidth(w);
		leftUp.setMinHeight(h);
		leftDown.setMinWidth(w);
		leftDown.setMinHeight(h);
		
		rightUp.setMinWidth(w);
		rightUp.setMinHeight(h);
		rightDown.setMinWidth(w);
		rightDown.setMinHeight(h);
		
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
		
//		left.addListener(new InputListener() {
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
//                return true;
//            }
//        });
		
		right.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				m_level.clockwiseRotate();
			}
		});
		
		
		replay.align(Align.right);
		pause.align(Align.left);
		start.align(Align.right);
		right.align(Align.right);
		left.align(Align.left);
		
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
