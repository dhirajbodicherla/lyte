package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Constants;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	
	private AssetManager assetManager;
	private Vector2 VIEWPORT;
	private Vector2 SCREEN;

	// singleton: prevent instantiation from other classes
	private Assets() {}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		SCREEN = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		calcViewport();
		
	}
	
	private void calcViewport()
	{
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		float GCD = (int)(gcd(w, h));
		int aW = (int) (w/GCD);
		int aH = (int) (h/GCD);
		
		//if 4:3
		if(aW==4 && aH==3)
			VIEWPORT = new Vector2(640,480);
		
		//16:9
		if(aW==16 && aH==9)
			VIEWPORT = new Vector2(640,360);
		
		//5:3
		if(aW==5 && aH==3)
			VIEWPORT = new Vector2(800,480);
		
		//5:4
		if(aW==5 && aH==4)
			VIEWPORT = new Vector2(600, 480);
		
		//3:2
		if(aW==3 && aH==2)
			VIEWPORT = new Vector2(600, 480);
	}
	
	private int gcd (int a, int b) {
        return (b == 0) ? a : gcd (b, a%b);
    }
	
	public Vector2 queryViewport()
	{
		return VIEWPORT;
	}
	
	public Vector2 queryScreen()
	{
		return SCREEN;
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}


	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		// TODO Auto-generated method stub
		Gdx.app.error(TAG, "Couldnt load asset" + asset);
	}

	

}
