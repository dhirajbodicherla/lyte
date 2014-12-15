package com.jsd.lightphysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.jsd.lightphysics.Constants;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	
	private AssetManager manager;
	private Vector2 VIEWPORT;
	private Vector2 SCREEN;
	
	private TextureAtlas menuAtlas;
	private TextureAtlas hudAtlas;
	private TextureAtlas spriteAtlas;
	private Texture menuScreen;
	private Texture gameScreen;

	// singleton: prevent instantiation from other classes
	private Assets() {}

	public void init(AssetManager assetManager) {
		this.manager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		//calculate viewport
		calcViewport();
	}
	
	public void load()
	{
		manager.load(Constants.TEXTURE_ATLAS_UI, TextureAtlas.class);
		manager.load(Constants.TEXTURE_ATLAS_SPRITE, TextureAtlas.class);
		manager.load(Constants.TEXTURE_ATLAS_HUD, TextureAtlas.class);
		manager.load(Constants.IMG_GAME_MENU, Texture.class);
		manager.load(Constants.IMG_GAME_SCREEN, Texture.class);
	}
	
	//call this after asset manager finishes loading everything
	public void setupAssets()
	{
		menuAtlas = manager.get(Constants.TEXTURE_ATLAS_UI);
		spriteAtlas = manager.get(Constants.TEXTURE_ATLAS_SPRITE);
		hudAtlas = manager.get(Constants.TEXTURE_ATLAS_HUD);
		menuScreen = manager.get(Constants.IMG_GAME_MENU);
		gameScreen = manager.get(Constants.IMG_GAME_SCREEN);
	}
	
	public boolean update()
	{
		return manager.update();
	}
	
	private void calcViewport()
	{
		SCREEN = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		VIEWPORT = null;
		
		//Screen Size
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		float GCD = (int)(gcd(w, h));
		
		//Aspect Ratio
		int aW = (int) (w/GCD);
		int aH = (int) (h/GCD);
		
		//if 4:3
		if(aW==4 && aH==3)
			VIEWPORT = new Vector2(640,480);
		
		//16:9
		if(aW==16 && aH==9)
			VIEWPORT = new Vector2(640,360);
		
		//8:5
		if(aW==8 && aH==5)
			VIEWPORT = new Vector2(640,400);
		
		//5:3
		if(aW==5 && aH==3)
			VIEWPORT = new Vector2(800,480);
		
		//5:4
		if(aW==5 && aH==4)
			VIEWPORT = new Vector2(600, 480);
		
		//3:2
		if(aW==3 && aH==2)
			VIEWPORT = new Vector2(960, 640);
		
		//if no suitable match reverting to 640x480
		if(VIEWPORT==null)
			VIEWPORT = new Vector2(640,480);
		
		Gdx.app.debug("VIEWPORT","Selected Viewport:"+VIEWPORT);
		Gdx.app.debug("SCREEN", "W: "+w+" H: "+h);
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
	
	public TextureAtlas getMenuAtlas()
	{
		return menuAtlas;
	}
	
	public TextureAtlas getHUDAtlas()
	{
		return hudAtlas;
	}
	
	public TextureAtlas getSpriteAtlas()
	{
		return spriteAtlas;
	}
	
	public Texture getMenuScreen()
	{
		return menuScreen;
	}
	
	public Texture getGameScreen()
	{
		return gameScreen;
	}

	@Override
	public void dispose() {
		manager.dispose();
	}


	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		// TODO Auto-generated method stub
		Gdx.app.error(TAG, "Couldnt load asset" + asset);
	}

	

}
