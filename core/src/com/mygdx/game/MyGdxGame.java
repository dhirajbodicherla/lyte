package com.mygdx.game;

import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import box2dLight.PointLight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.WorldRenderer;
import com.mygdx.game.WorldController;

/*
 * Author: Jay Ravi
 * 
 * Box2D World Units: meters
 * OpenGL World Units: pixels 
 * 1 Meter = 100 pixels
 * 1 pixel = 0.01m
 * 
 * 
 * Coordinate System:
 * (0,0) - Bottom left corner
 * (width, height) - top right corner
 * 
 * 
 * For rendering 2d graphics convert to pixel. 
 * Box2D bodies should be positioned and dimensioned in meters
 * 
 * 
 * WARNING!!!
 * Foregoing this procedure can cause unexpected results.  
 */

//public class MyGdxGame implements ApplicationListener{
public class MyGdxGame extends Game{
	
	@Override
	public void create () {
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		setScreen(new GameScreen(this));
	}
}
