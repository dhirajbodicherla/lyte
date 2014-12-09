package com.mygdx.game;
import java.util.ArrayList;

import box2dLight.RayHandler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;




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
	
	public static final float WORLD_TO_BOX = 0.01f;
	public static final float BOX_TO_WORLD = 100.f;
	public static final double DEGTORAD = 0.0174532925199432957f;
	public static float SCREEN_WIDTH, SCREEN_HEIGHT;
	
	private OrthographicCamera camera;
	private int inputMode;			//1. Shoot 2. Drag body 3. Change Angle 
	
	private SpriteBatch batch;
    
	
	/* Box2d Stuff */ 
	World world; 
	Box2DDebugRenderer renderer;
	RayHandler handler;
	
	//Level Stuff
	int currentLevel;
	LevelCollection mLevels;
	
	//Game Elements
	private Entity mTarget;
	private Entity mSource;
	private ArrayList<BlackHole> mBlackholes;
	private ArrayList<Asteroid> mAsteroids;
	private ArrayList<Mirror> mMirrors;
	
	//Light Photons
	private ArrayList<Photon> mPhotons;
	
	//Mouse Direction
	float dx;
	float dy;
	//Mouse Position in Box2d Space
	float mx; 
	float my;
	
	boolean isMouseDown;
	
	//1 - init
	//2 - running
	//3 - destroyed
	int gameState;
	float rotAngle;
	
	
	//Mouse Joint
	private MouseJoint mJoint;
	private Body virtualBody; 	//Virtual ground body for the mouseJoint
	private Body hitBody;
	private Vector2 tmp;
	private Vector2 tmp2;
	
	
	@Override
	public void create () {
		
		initGame();	
		createLevel();
	}
	
	public void initGame()
	{
		//Current Level
		currentLevel = 0;			//Levels always start with 0
		
		inputMode = 2;		//Start with drag 
		
		//Screen Size
		SCREEN_WIDTH = Gdx.graphics.getWidth();		//GUI Width
		SCREEN_HEIGHT = Gdx.graphics.getHeight(); 		//GUI Height
		SCREEN_WIDTH = 800;
		SCREEN_HEIGHT = 600;
		Gdx.graphics.setDisplayMode(800, 600,false);
		
		//Init Rendering Batch
		batch = new SpriteBatch();
		
		//Init Camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		
		//Init Physics World and Debug
		world = new World(new Vector2(0.0f, 0.0f), false);
		renderer = new Box2DDebugRenderer();
		handler = new RayHandler(world);
		
		
		
		
		//Initialize Levels
		mLevels= LevelBuilder.ParseFile("data/level.js");
		mTarget = null;
		mSource = null;
		mBlackholes = new ArrayList<BlackHole>();
		mAsteroids = new ArrayList<Asteroid>();
		mMirrors = new ArrayList<Mirror>();
		mPhotons = new ArrayList<Photon>();
		
		//Initialize Mouse Joints
		virtualBody = world.createBody(new BodyDef());
		
		tmp = new Vector2();
		tmp2 = new Vector2();
		
		isMouseDown = false;
		
		Gdx.input.setInputProcessor(this);
		gameState = 1;
		rotAngle = 0;
		
	}
	
	public void createLevel()
	{
		
		if(gameState!=2)	//If game is initialized or destroyed then it is allowed to create new objects
		{
			LevelDef ld = mLevels.list.get(currentLevel);
			
			int numMirrors = ld.mMirrors !=null ? ld.mMirrors.size() : 0;
			int numAsteroids= ld.mAsteroids !=null ? ld.mAsteroids.size() : 0;
			int numBlackholes = ld.mBlackholes !=null ? ld.mBlackholes.size() : 0;
			
			Vector2 bs = new Vector2(ld.mBaseWidth, ld.mBaseHeight);
			
			
			mTarget = new Entity(ld.mTarget); 
			mTarget.setPhysicsBody(LevelBuilder.createPhysicsBody(ld.mTarget, mTarget, bs, world));
			
			mSource = new Entity(ld.mSource); 
			mSource.setPhysicsBody(LevelBuilder.createPhysicsBody(ld.mSource, mSource, bs,world));
			
			
			for(int i = 0 ; i < numMirrors; i++)
			{
				EntityDef ed = ld.mMirrors.get(i);
				Mirror e = new Mirror(ed);
				e.setPhysicsBody(LevelBuilder.createPhysicsBody(ed, e, bs,world));
				mMirrors.add(e);
			}
			
			for(int i = 0 ; i < numAsteroids; i++)
			{
				EntityDef ed = ld.mAsteroids.get(i);
				Asteroid e = new Asteroid(ed);
				Body b = LevelBuilder.createPhysicsBody(ed, e, bs,world);
				e.setPhysicsBody(b);
				mAsteroids.add(e);
			}
			
			for(int i = 0 ; i < numBlackholes; i++)
			{
				EntityDef ed = ld.mBlackholes.get(i);
				BlackHole e = new BlackHole(ed);
				Body b = LevelBuilder.createPhysicsBody(ed, e, bs,world);
				b.setAngularVelocity(0.5f);
				b.setAngularDamping(0.f);
				e.setPhysicsBody(b);
				mBlackholes.add(e);
			}
			
			//Define Mouse joint
			gameState = 2;	//Set Game State to running
		}		
	}
	
	public void destroyed()
	{
		//if the game is already running, it can be destroyed at some point
		if(gameState == 2)
		{
			int numMirror = mMirrors.size();
			int numAsteroids = mAsteroids.size();
			int numBlackholes = mBlackholes.size();
			int numPhotons = mPhotons.size();
			for(int i = 0 ; i < numMirror ; i++)
			{
				Body b = mMirrors.get(i).getPhysicsBody();
				world.destroyBody(b);
			}
			
			for(int i = 0 ; i < numAsteroids; i++)
			{
				Body b = mAsteroids.get(i).getPhysicsBody();
				world.destroyBody(b);
			}
			for(int i = 0 ; i < numBlackholes; i++)
			{
				Body b = mBlackholes.get(i).getPhysicsBody();
				world.destroyBody(b);
			}
			for(int i = 0 ; i < numPhotons; i++)
			{
				Body b = mPhotons.get(i).getPhysicsBody();
				world.destroyBody(b);
			}
			
			world.destroyBody(mTarget.getPhysicsBody());
			world.destroyBody(mSource.getPhysicsBody());
			mTarget = null;
			mSource = null;
			mMirrors.clear();
			mAsteroids.clear();
			mBlackholes.clear();
			mPhotons.clear();
			gameState = 3;
		}
		
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); 	//Black Background
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		Matrix4 cameraCopy = camera.combined.cpy();
		
		for(BlackHole b : mBlackholes )
		{b.render(batch);}
		
		for(Photon p : mPhotons )
		{p.render(batch);}
		
		for(Mirror m : mMirrors)
		{m.render(batch);}
		
		for(Asteroid a : mAsteroids)
		{a.render(batch);}
	
		renderer.render(world, cameraCopy.scl(BOX_TO_WORLD));
		//handler.updateAndRender();
		
		/*batch.begin();
		sprite.setPosition(100, 470);
        sprite.draw(batch);
		batch.end();*/
		
		
		
		//rayHandler.updateAndRender();
		world.step(1/60f, 6, 2);
		update();
		
		
	}
	
	public void update()
	{
		if(gameState == 2)
		{
			TrackMouse();
			blackHoleInfluence();
		}
	}
	
	public void TrackMouse()
	{
		if(gameState == 2)
		{
			mx = Gdx.input.getX() * WORLD_TO_BOX;
			my = (SCREEN_HEIGHT-Gdx.input.getY())*WORLD_TO_BOX;
			dx = mx - mSource.getPhysicsBody().getWorldCenter().x;
			dy = my - mSource.getPhysicsBody().getWorldCenter().y;
			float angle = (float)Math.atan2(dy,dx);
			Vector2 norm = new Vector2(dx, dy);
			norm.nor();
			dx = norm.x;
			dy = norm.y;
			mSource.getPhysicsBody().setTransform(mSource.getPhysicsBody().getWorldCenter(), angle);
		}
		
	}
	
	public void launchPhoton()
	{
		if(gameState == 2)
		{
			float fireRadius= (float)(mSource.getSize().x * 0.5);
			Vector2 dir = new Vector2(dx*WORLD_TO_BOX*fireRadius, dy*WORLD_TO_BOX*fireRadius);
			Vector2 firePoint = dir.add(mSource.getPhysicsBody().getWorldCenter());
			Body circleBody = LevelBuilder.createPhysicsBody(firePoint, world);
			
			
			EntityDef ed = new EntityDef();
			ed.x = circleBody.getWorldCenter().x * Constants.BOX_TO_WORLD;
			ed.y = circleBody.getWorldCenter().y * Constants.BOX_TO_WORLD;
			ed.r = 5;
			Photon p = new Photon(ed);
			p.setPhysicsBody(circleBody);
			p.getPhysicsBody().applyLinearImpulse(dx*WORLD_TO_BOX, dy*WORLD_TO_BOX, circleBody.getWorldCenter().x, circleBody.getWorldCenter().y, true);
			mPhotons.add(p);
			
		}
	}
	
	public void blackHoleInfluence()
	{
		if(gameState==2)
		{
			for(int i = 0 ; i < mPhotons.size() ; i++)
			{
				Vector2 bulletPos = mPhotons.get(i).getPhysicsBody().getWorldCenter();
				for(int j = 0 ; j < mBlackholes.size() ;j++)
				{
					Shape planetShape = mBlackholes.get(j).getPhysicsBody().getFixtureList().get(0).getShape();
					float planetRadius = planetShape.getRadius();
					float planetInfluenceRadius = mBlackholes.get(j).getIr() * Constants.WORLD_TO_BOX;
					Vector2 planetPosition = mBlackholes.get(j).getPhysicsBody().getWorldCenter();
					Vector2 planetDistance = new Vector2(0,0);
					planetDistance.add(bulletPos);
					planetDistance.sub(planetPosition);
					float finalDistance = planetDistance.len();
					if(finalDistance <= planetInfluenceRadius)
					{
						planetDistance.scl(-1.f);
						float vecSum = Math.abs(planetDistance.x) + Math.abs(planetDistance.y);
						planetDistance.scl(0.3f*(1/vecSum)*planetRadius / finalDistance);
						mPhotons.get(i).getPhysicsBody().applyForce(planetDistance, mPhotons.get(i).getPhysicsBody().getWorldCenter(),true);
					}
					
				}
			}
		}
	}

	public void rotateBody()
	{
		if(hitBody!=null&& ((Entity)hitBody.getUserData()).getFixedRotation()!=0){
			hitBody.setTransform(hitBody.getWorldCenter(), rotAngle);
			rotAngle+=0.1;
		}
	}
	
	public void dispose() {
		world.dispose();
		destroyed();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.R)
			rotateBody();
		if(keycode == Input.Keys.D)
			destroyed();
		if(keycode == Input.Keys.C)
			createLevel();
		if(keycode == Input.Keys.RIGHT)
		{
			currentLevel++;
			if(currentLevel<0)
				currentLevel = 0;
			if(currentLevel>mLevels.list.size()-1)
				currentLevel = mLevels.list.size()-1;
			
			destroyed();
			createLevel();
		}
		if(keycode == Input.Keys.LEFT)
		{
			currentLevel--;
			if(currentLevel<0)
				currentLevel = 0;
			if(currentLevel>mLevels.list.size()-1)
				currentLevel = mLevels.list.size()-1;
			
			destroyed();
			createLevel();
		}
		
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		
		return false;
	}
	
	private QueryCallback queryCallBack = new QueryCallback()
	{
		public boolean reportFixture(Fixture fixture) {
			if(fixture.testPoint(tmp.x, tmp.y)){
				hitBody = fixture.getBody();
				return true;
			}
			else{
				return false;
			}
		}
	};
	
	public Vector2 unproject(int x, int y)
	{
		mx = Gdx.input.getX() * WORLD_TO_BOX;
		my = (SCREEN_HEIGHT-Gdx.input.getY())*WORLD_TO_BOX;
		return new Vector2(mx, my);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) 
	{
		if(button == Input.Buttons.LEFT){
			launchPhoton();
		}
		
		if(button == Input.Buttons.RIGHT){
			
			isMouseDown = true;
			tmp = unproject(x,y);
			hitBody = null;
			world.QueryAABB(queryCallBack, tmp.x-0.0001f, tmp.y-0.0001f, tmp.x+0.0001f, tmp.y+0.0001f);
			if(hitBody == virtualBody)hitBody=null;
			
			if(hitBody!=null && ((Entity)hitBody.getUserData()).getFixedPosition()!=0)
			{
				hitBody.setType(BodyType.DynamicBody);
				MouseJointDef mJointDef = new MouseJointDef();
				mJointDef.bodyA = virtualBody;
				mJointDef.bodyB = hitBody;
				mJointDef.collideConnected = true;
				mJointDef.target.set(tmp.x, tmp.y);
				mJointDef.maxForce = 1000.f * hitBody.getMass();
				mJoint = (MouseJoint)world.createJoint(mJointDef);
				hitBody.setAwake(true);
			}
			
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		isMouseDown = false;
		if(mJoint!=null)
		{
			hitBody.setType(BodyType.StaticBody);
			world.destroyJoint(mJoint);
            mJoint = null;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		
		if(mJoint!=null){
			tmp = unproject(x,y);
			mJoint.setTarget(tmp2.set(tmp.x,tmp.y));
		}
		
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
}
