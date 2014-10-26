package com.mygdx.game;

import java.util.ArrayList;
import java.util.logging.Level;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.RayHandler;
import box2dLight.PointLight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

public class MyGdxGame extends ApplicationAdapter {
	
	private static final float WORLD_TO_BOX = 0.01f;
	private static final float BOX_TO_WORLD = 100.f;
	float width, height;
	OrthographicCamera camera;

	FPSLogger logger;
	
	/* Box 2D Lights */ 
	RayHandler handler;
	PointLight pl;
	
	/* Box2d Stuff */ 
	World world; 
	Box2DDebugRenderer renderer;
	
	Body circleBody;
	
	
	
	@Override
	public void create () {
		LevelDef lvl = LevelParser.LoadFile("data/level.json");

		width = Gdx.graphics.getWidth();		//GUI Width
		height = Gdx.graphics.getHeight(); 		//GUI Height
		
		System.out.println(width);
		System.out.println(height);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		
		world = new World(new Vector2(0.0f, -9.8f), false);
		renderer = new Box2DDebugRenderer();
		logger = new FPSLogger();
		
		
		createLevel(lvl);
		
		BodyDef circleDef = new BodyDef();
		circleDef.type = BodyType.DynamicBody;
		circleDef.position.set(40*0.5f*WORLD_TO_BOX, (height-20)*WORLD_TO_BOX);
		
		circleBody = world.createBody(circleDef);
		
		CircleShape circleShape = new CircleShape(); 
		circleShape.setRadius(5 * WORLD_TO_BOX);
		
		
		FixtureDef circleFixture = new FixtureDef(); 
		circleFixture.shape = circleShape; 
		circleFixture.density = 0.4f;
		circleFixture.friction = 0.2f; 
		circleFixture.restitution = 0.8f; 
		
		circleBody.createFixture(circleFixture);
		
		
		circleBody.applyLinearImpulse(1*WORLD_TO_BOX, -1*WORLD_TO_BOX, circleBody.getWorldCenter().x, circleBody.getWorldCenter().y, true);
		
		handler = new RayHandler(world);
		handler.setCombinedMatrix(camera.combined.scl(BOX_TO_WORLD));

		pl = new PointLight(handler, 10, Color.CYAN, 100*WORLD_TO_BOX, circleBody.getPosition().x,circleBody.getPosition().y);
		new ConeLight(handler, 100, Color.WHITE, width*WORLD_TO_BOX, width*WORLD_TO_BOX, height*0.5f*WORLD_TO_BOX, 45, 180);
		
	}
	
	public void createLevel(LevelDef lvl)
	{
		BodyDef bodyDef; 
		Shape bodyShape = null;
		FixtureDef fixtureDef = null; 
		Body phyBody;
		
		for(int i = 0 ; i < lvl.entities.size() ; i++)
		{
			EntityDef ed = lvl.entities.get(i);
			float x = ed.x;
			float y = ed.y;
			float w = ed.w;
			float h = ed.h;
			
			
			bodyDef = new BodyDef();
			if(ed.type.equalsIgnoreCase("S"))
			{
				bodyDef.type = BodyType.StaticBody;
			}
			if(ed.type.equals("D"))
			{
				bodyDef.type = BodyType.DynamicBody;
			}
			
			bodyDef.position.set(x*WORLD_TO_BOX, y*WORLD_TO_BOX);
			
			
			if(ed.shape.equalsIgnoreCase("rect"))
			{
				bodyShape = new PolygonShape();
				((PolygonShape) bodyShape).setAsBox((w*0.5f)*WORLD_TO_BOX, h*0.5f*WORLD_TO_BOX);
			}
			if(ed.shape.equalsIgnoreCase("circle"))
			{
				bodyShape = new CircleShape();
				bodyShape.setRadius(ed.r * WORLD_TO_BOX);
			}
			
			fixtureDef = new FixtureDef();
			fixtureDef.shape = bodyShape; 
			fixtureDef.density = 0.4f;
			fixtureDef.friction = 0.2f; 
			fixtureDef.restitution = 0.8f;
			
			
			phyBody = world.createBody(bodyDef);
			phyBody.createFixture(fixtureDef);
		
		}
		
	}
	
		
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1); 	//Black Background
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		pl.setPosition(circleBody.getPosition().x, circleBody.getPosition().y);
		
		Matrix4 cameraCopy = camera.combined.cpy();
	
		renderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
		
		world.step(1/60f, 6, 2);
		
		logger.log();
		
	}
	
	public void dispose() {
		world.dispose();
		handler.dispose();
	}
}
