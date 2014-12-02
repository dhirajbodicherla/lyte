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

public class Assets implements Disposable, AssetErrorListener {
	public AssetFonts fonts;
	public AssetMirror mirror;
	public AssetLaser laser;
	public AssetAsteroid asteroid;
	public AssetBlackHole blackhole;
	public AssetPhoton photon;
	public AssetEarth earth;
	public AssetSpace space;
	// public AssetRock rock;
	// public AssetGoldCoin goldCoin;
	// public AssetFeather feather;
	// public AssetLevelDecoration levelDecoration;

	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;

	// singleton: prevent instantiation from other classes
	private Assets() {}

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		// start loading assets and wait until finished
		assetManager.finishLoading();
		
//		for (String a : assetManager.getAssetNames())
//			Gdx.app.debug(TAG, "asset: " + a);

		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		// create game resource objects
		fonts = new AssetFonts();
		mirror = new AssetMirror(atlas);
		laser = new AssetLaser(atlas);
		asteroid = new AssetAsteroid(atlas);
		blackhole = new AssetBlackHole(atlas);
		photon = new AssetPhoton(atlas);
		earth = new AssetEarth(atlas);
		space = new AssetSpace(atlas);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
	}

	public class AssetMirror {
		public final AtlasRegion mirror;

		public AssetMirror(TextureAtlas atlas) {
			mirror = atlas.findRegion("mirror");
		}
	}
	
	public class AssetLaser{
		public final AtlasRegion laser;
		public final Animation animLaser;
		
		public AssetLaser(TextureAtlas atlas) {
			laser = atlas.findRegion("laser");
			Array<AtlasRegion> regions = atlas.findRegions("star");
			AtlasRegion region = regions.first();
			regions.insert(0, region);
			animLaser = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
		}
	}
	
	public class AssetAsteroid{
		public final AtlasRegion asteroid;
		public final Animation animAsteroid;
		
		public AssetAsteroid(TextureAtlas atlas){
			asteroid = atlas.findRegion("asteroid");
			Array<AtlasRegion> regions = atlas.findRegions("asteroid");
			AtlasRegion region = regions.first();
			regions.insert(0, region);
			animAsteroid = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
		}
	}
	
	public class AssetBlackHole{
		public final AtlasRegion blackhole;
		public final Animation animBlackhole;
		
		public AssetBlackHole(TextureAtlas atlas){
			blackhole = atlas.findRegion("blackhole");
			Array<AtlasRegion> regions = atlas.findRegions("blackhole");
			AtlasRegion region = regions.first();
			regions.insert(0, region);
			animBlackhole = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
		}
	}
	
	public class AssetPhoton{
		public final AtlasRegion photon;
		
		public AssetPhoton(TextureAtlas atlas){
			photon = atlas.findRegion("photon");
		}
	}
	
	public class AssetEarth{
		public final AtlasRegion earth;
		public final Animation animEarth;
		
		public AssetEarth(TextureAtlas atlas){
			earth = atlas.findRegion("earth");
			Array<AtlasRegion> regions = atlas.findRegions("earth");
			AtlasRegion region = regions.first();
			regions.insert(0, region);
			animEarth = new Animation(1.0f / 10.0f, regions, Animation.PlayMode.LOOP);
		}
	}
	
	public class AssetSpace{
		public final AtlasRegion space;
		
		public AssetSpace(TextureAtlas atlas){
			space = atlas.findRegion("bg");
		}
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		// TODO Auto-generated method stub
		Gdx.app.error(TAG, "Couldnt load asset" + asset);
	}

	public class AssetLevelDecoration {
		public final AtlasRegion mirror;
		public final AtlasRegion laser;
		// public final AtlasRegion cloud02;
		// public final AtlasRegion cloud03;
		// public final AtlasRegion mountainLeft;
		// public final AtlasRegion mountainRight;
		// public final AtlasRegion waterOverlay;

		public AssetLevelDecoration(TextureAtlas atlas) {

			 mirror = atlas.findRegion("mirror");
			 laser = atlas.findRegion("laser");
			// cloud02 = atlas.findRegion("cloud02");
			// cloud03 = atlas.findRegion("cloud03");
			// mountainLeft = atlas.findRegion("mountain_left");
			// mountainRight = atlas.findRegion("mountain_right");
			// waterOverlay = atlas.findRegion("water_overlay");
		}
	}

	public class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;

		public AssetFonts() {
			// create three fonts using Libgdx's 15px bitmap font
//			defaultSmall = new BitmapFont(
//					Gdx.files.internal("images/arial-15.fnt"), true);
//			defaultNormal = new BitmapFont(
//					Gdx.files.internal("images/arial-15.fnt"), true);
//			defaultBig = new BitmapFont(
//					Gdx.files.internal("images/arial-15.fnt"), true);
//			// set font sizes
//			defaultSmall.setScale(0.75f);
//			defaultNormal.setScale(1.0f);
//			defaultBig.setScale(2.0f);
//			// enable linear texture filtering for smooth fonts
//			defaultSmall.getRegion().getTexture()
//					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//			defaultNormal.getRegion().getTexture()
//					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//			defaultBig.getRegion().getTexture()
//					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//			
			defaultSmall = new BitmapFont();
			defaultNormal = new BitmapFont();
			defaultBig = new BitmapFont();
		}
	}
}
