package com.mygdx.game;

import java.util.ArrayList;

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
		width = Gdx.graphics.getWidth();		//GUI Width
		height = Gdx.graphics.getHeight(); 		//GUI Height
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		
		world = new World(new Vector2(0, 0), false);
		renderer = new Box2DDebugRenderer();
		logger = new FPSLogger();
		
		
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
		
		
		createWalls();
		createObstacles();
		
		circleBody.applyLinearImpulse(1*WORLD_TO_BOX, -1*WORLD_TO_BOX, circleBody.getWorldCenter().x, circleBody.getWorldCenter().y, true);
		
		handler = new RayHandler(world);
		handler.setCombinedMatrix(camera.combined.scl(BOX_TO_WORLD));

		pl = new PointLight(handler, 10, Color.CYAN, 100*WORLD_TO_BOX, circleBody.getPosition().x,circleBody.getPosition().y);
		//new DirectionalLight(handler, 5000, Color.WHITE, -90);
		new ConeLight(handler, 100, Color.WHITE, width*WORLD_TO_BOX, width*WORLD_TO_BOX, height*0.5f*WORLD_TO_BOX, 45, 180);
		
	}
	
	public void createWalls()
	{
		BodyDef wallDef; 
		PolygonShape wallShape;
		
		
		//left
		wallDef = new BodyDef();
		wallDef.position.set(10*0.5f*WORLD_TO_BOX, height*0.5f*WORLD_TO_BOX);
		
		wallShape = new PolygonShape();
		wallShape.setAsBox(10*0.5f*WORLD_TO_BOX, height*0.5f*WORLD_TO_BOX);
		(world.createBody(wallDef)).createFixture(wallShape, 0.f);
		
		
		//right
		wallDef = new BodyDef();
		wallDef.position.set((width-10*0.5f)*WORLD_TO_BOX, height*0.5f*WORLD_TO_BOX);
		
		wallShape = new PolygonShape();
		wallShape.setAsBox(10*0.5f*WORLD_TO_BOX, height*0.5f*WORLD_TO_BOX);
		(world.createBody(wallDef)).createFixture(wallShape, 0.f);
		
		
		//bottom
		wallDef = new BodyDef();
		wallDef.position.set(width * 0.5f * WORLD_TO_BOX, (10*0.5f)*WORLD_TO_BOX);
		
		wallShape = new PolygonShape();
		wallShape.setAsBox(width*0.5f*WORLD_TO_BOX, (10*0.5f)*WORLD_TO_BOX);
		(world.createBody(wallDef)).createFixture(wallShape, 0.f);
		
		
		//top
		wallDef = new BodyDef();
		wallDef.position.set(width * 0.5f * WORLD_TO_BOX, (height-10*0.5f)*WORLD_TO_BOX);
		
		wallShape = new PolygonShape();
		wallShape.setAsBox(width*0.5f*WORLD_TO_BOX, (10*0.5f)*WORLD_TO_BOX);
		(world.createBody(wallDef)).createFixture(wallShape, 0.f);
		
		
		
		
	}

	public void createObstacles()
	{
		Vector2 v1 = new Vector2(50*WORLD_TO_BOX, 50*WORLD_TO_BOX);
		Vector2 v2 = new Vector2((width*0.5f)*WORLD_TO_BOX, (height*0.5f)*WORLD_TO_BOX);
		Vector2 v3 = new Vector2(width*0.75f*WORLD_TO_BOX, height*0.75f*WORLD_TO_BOX);
		Vector2 v4 = new Vector2((width-50)*WORLD_TO_BOX, 60*WORLD_TO_BOX);
		ArrayList<Vector2> pos = new ArrayList<Vector2>();
		pos.add(v1);
		pos.add(v2);
		pos.add(v3);
		pos.add(v4);
		
		float hsize = 20;
		
		BodyDef obsDef; 
		PolygonShape obsBox;
		
		for(int i = 0; i < pos.size() ; i++)
		{
			obsDef = new BodyDef();
			obsDef.position.set(pos.get(i));
			
			obsBox = new PolygonShape();
			obsBox.setAsBox(hsize*WORLD_TO_BOX, hsize*WORLD_TO_BOX);
			(world.createBody(obsDef)).createFixture(obsBox, 0.f);
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
		handler.updateAndRender();
		world.step(1/60f, 6, 2);
		
		logger.log();
		
	}
	
	public void dispose() {
		world.dispose();
		handler.dispose();
	}
}
