package com.mygdx.game;




import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import box2dLight.PointLight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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

public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
	
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
	Body kBody;
	ArrayList<Body> planets;
	ArrayList<Body>	bullets;
	
	float dx;
	float dy;
	
	/* 
	 * GUI
	 */
	private OrthographicCamera cameraGUI;
	private SpriteBatch batch;
	private BitmapFont font;
	private int score=0;
	
	@Override
	public void create () {
		LevelDef lvl = LevelParser.LoadFile("data/level.json");
		
		Gdx.input.setInputProcessor(this);

		width = Gdx.graphics.getWidth();		//GUI Width
		height = Gdx.graphics.getHeight(); 		//GUI Height
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		
		world = new World(new Vector2(0.0f, -0.0f), false);
		renderer = new Box2DDebugRenderer();
		logger = new FPSLogger();
		kBody = null;
		planets = new ArrayList<Body>();
		bullets = new ArrayList<Body>();
		
		
		createLevel(lvl);
		
		
		handler = new RayHandler(world);
		handler.setCombinedMatrix(camera.combined.scl(BOX_TO_WORLD));

		
		new ConeLight(handler, 100, Color.WHITE, width*WORLD_TO_BOX, width*WORLD_TO_BOX, height*0.5f*WORLD_TO_BOX, 45, 180);
		
		/*
		 * GUI
		 */
		batch = new SpriteBatch();
		font = new BitmapFont();
        font.setColor(Color.RED);
		cameraGUI = new OrthographicCamera(width, height);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip y-axis
		cameraGUI.update();
		
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
			
			if(ed.type.equals("K"))
			{
				bodyDef.type = BodyType.KinematicBody;
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
			if(ed.type.equals("K"))
			{
				kBody = phyBody; 
			}
			else{
				planets.add(phyBody);
			}
		}
		
	}
	
		
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1); 	//Black Background
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		Matrix4 cameraCopy = camera.combined.cpy();
	
		renderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
		
		world.step(1/60f, 6, 2);
		
		
		dx = Gdx.input.getX() * WORLD_TO_BOX - kBody.getWorldCenter().x;
		dy = (height-Gdx.input.getY())*WORLD_TO_BOX - kBody.getWorldCenter().y;
		float angle = (float)Math.atan2(dy,dx);
		Vector2 norm = new Vector2(dx, dy);
		norm.nor();
		dx = norm.x;
		dy = norm.y;
		
		
		kBody.setTransform(kBody.getWorldCenter(), angle);
		
		update();
		//logger.log();
		
		renderGuiScore();
		
	}
	
	public void update()
	{
		for(int i = 0 ; i < bullets.size() ; i++)
		{
			Vector2 bulletPos = bullets.get(i).getWorldCenter();
			for(int j = 0 ; j < planets.size() ;j++)
			{
				Shape planetShape = planets.get(j).getFixtureList().get(0).getShape();
				float planetRadius = planetShape.getRadius();
				Vector2 planetPosition = planets.get(j).getWorldCenter();
				Vector2 planetDistance = new Vector2(0,0);
				planetDistance.add(bulletPos);
				planetDistance.sub(planetPosition);
				float finalDistance = planetDistance.len();
				if(finalDistance <= planetRadius*5.f)
				{
					planetDistance.scl(-1.f);
					float vecSum = Math.abs(planetDistance.x) + Math.abs(planetDistance.y);
					planetDistance.scl(0.2f*(1/vecSum)*planetRadius / finalDistance);
					bullets.get(i).applyForce(planetDistance, bullets.get(i).getWorldCenter(),true);
				}
				
			}
		}
	}
	
	public void dispose() {
		world.dispose();
		handler.dispose();
		batch.dispose();
        font.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		BodyDef circleDef = new BodyDef();
		circleDef.type = BodyType.DynamicBody;
		circleDef.position.set(kBody.getWorldCenter());

		Body circleBody = world.createBody(circleDef);

		CircleShape circleShape = new CircleShape(); 
		circleShape.setRadius(5 * WORLD_TO_BOX);


		FixtureDef circleFixture = new FixtureDef(); 
		circleFixture.shape = circleShape; 
		circleFixture.density = 0.4f;
		circleFixture.friction = 0.2f; 
		circleFixture.restitution = 0.8f; 

		circleBody.createFixture(circleFixture);
		
		score++;
		circleBody.applyLinearImpulse(dx*WORLD_TO_BOX, dy*WORLD_TO_BOX, circleBody.getWorldCenter().x, circleBody.getWorldCenter().y, true);
		bullets.add(circleBody);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void renderGuiScore () {
		 float x = 10;
		 float y = height-10;
		 batch.begin();
		 font.draw(batch, score + "", x, y);
		 batch.end();
	}
		 
}
