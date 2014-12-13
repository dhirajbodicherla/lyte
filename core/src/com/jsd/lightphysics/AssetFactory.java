package com.jsd.lightphysics;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


public class AssetFactory {

	
	public static TextButton createButton(TextureAtlas atlas, String up, String down, boolean isAspEqual)
	{
		Vector2 SCREEN = Assets.instance.queryScreen();
		Skin skin = new Skin(atlas);
		Drawable upDraw = skin.getDrawable(up);
		Drawable downDraw= skin.getDrawable(down);
		
		float w = (skin.getDrawable(up).getMinWidth() / 640) * SCREEN.x;
		float h;
		//is width and height equal? 
		if(!isAspEqual)
			h = (skin.getDrawable(up).getMinHeight() / 480) * SCREEN.y;
		else
			h = w; 
		
		upDraw.setMinWidth(w);
		upDraw.setMinHeight(h);
		downDraw.setMinWidth(w);
		downDraw.setMinHeight(h);
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = upDraw;
		textButtonStyle.down = downDraw;
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = new BitmapFont();
		
		TextButton btn = new TextButton("", textButtonStyle);
		
		return btn;
	}
	
	public static Image createImage(TextureAtlas atlas, String regionName)
	{
		Skin skin = new Skin(atlas);
		return new Image(skin, regionName);
	}
	
	//returns the scale value necessary to attain the new value
	public static float setToScale(float oldValue, float newValue)
	{
		float scale=0;;
		if(newValue > oldValue)
		{
			float increase = newValue - oldValue;
			scale = increase/oldValue;
			scale += 1;
		}
		if(oldValue > newValue)
		{
			float decrease = oldValue - newValue;
			scale = 1-(decrease/oldValue);
		}
		return scale;
	}
	
}
