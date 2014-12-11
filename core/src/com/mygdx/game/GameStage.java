package com.mygdx.game;

import java.util.ArrayList;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameStage extends Stage implements InputProcessor, GestureListener{
	
	public static float SCREEN_WIDTH, SCREEN_HEIGHT;
	private OrthographicCamera camera; 
	private SpriteBatch batch;
	
	/* Box2d Stuff */ 
	World world; 
	Box2DDebugRenderer renderer;
	
	private int inputMode;			//1. Shoot 2. Drag body 3. Change Angle
//	boolean isMouseDown;
	float rotAngle;
	
	Vector3 mouse;
	
	//Mouse Joint
	private MouseJoint mJoint;
	private Body virtualBody; 	//Virtual ground body for the mouseJoint
	private Body hitBody;
	private Vector3 tmp;
	private Vector2 tmp2;
	private Sound photonShootSound;
	
	public Level m_level; 
	
	
	public GameStage(int level)
	{
		this.init(level);
	}
	
	public void initWorld(int level)
	{
		world = new World(new Vector2(0.0f, 0.0f), false);
		renderer = new Box2DDebugRenderer();
		Vector2 screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		m_level = new Level("data/level.js", world, screenSize, level);
//		photonShootSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/photon_shoot.mp3"));
	}
	
	public void setupCamera()
	{
		camera = new OrthographicCamera();	//Defaults to screen size
		camera.setToOrtho(false);
		camera.update();
	}
	
	public void initRendering()
	{
		//Init Rendering Batch
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
	}
	
	public void initInteractivity()
	{
		mouse = new Vector3();
		
		inputMode = 2;		//Start with drag
		//Initialize Mouse Joints
		virtualBody = world.createBody(new BodyDef());
		
		tmp = new Vector3();
		tmp2 = new Vector2();
		
		//isMouseDown = false;
		
		rotAngle = 0;
	}


	public void init(int level)
	{
		initWorld(level);
		setupCamera();
		initRendering();
		initInteractivity();
		
	}
	

	public void rotateBody()
	{
		if(hitBody!=null&& ((Entity)hitBody.getUserData()).getFixedRotation()!=0){
			hitBody.setTransform(hitBody.getWorldCenter(), rotAngle);
			rotAngle+=0.1;
		}
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
			m_level.destroy();
		if(keycode == Input.Keys.RIGHT)
		{
			m_level.nextLevel();
		}
		if(keycode == Input.Keys.LEFT)
		{
			m_level.prevLevel();
		}
		
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

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) 
	{
		
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		
		//mouse = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		
		//isMouseDown = false;
		if(mJoint!=null)
		{
			((Mirror)hitBody.getUserData()).isSelected = false;
			hitBody.setType(BodyType.StaticBody);
			world.destroyJoint(mJoint);
            mJoint = null;
            hitBody=null;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		
		if(mJoint!=null){
			tmp = new Vector3(x, y, 0);
			camera.unproject(tmp);
			tmp.scl(Constants.WORLD_TO_BOX);
			mJoint.setTarget(tmp2.set(tmp.x,tmp.y));
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouse.x = screenX;
		mouse.y = screenY;
		camera.unproject(mouse);
		return false;
	}
	
	public void draw()
	{
		super.draw();
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); 	//Black Background
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		Matrix4 cameraCopy = camera.combined.cpy();
		
		//Render Level here
		m_level.render(batch);
		m_level.update(mouse);
		
		
		renderer.render(world, cameraCopy.scl(Constants.BOX_TO_WORLD));
		world.step(1/60f, 6, 2);
		update();	//keep checking if the level is solved
	}
	
	public void update()
	{
		
	}
	
	
	public void dispose() {
		world.dispose();
		m_level.destroy();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		tmp = new Vector3(x, y, 0);
		Gdx.app.debug("Touch before ", String.valueOf(tmp.x) + " : " + String.valueOf(tmp.y));
		camera.unproject(tmp);
		tmp.scl(Constants.WORLD_TO_BOX);
		Gdx.app.debug("Touch ", String.valueOf(tmp.x) + " : " + String.valueOf(tmp.y));
		world.QueryAABB(queryCallBack, tmp.x-0.0001f, tmp.y-0.0001f, tmp.x+0.0001f, tmp.y+0.0001f);
		if(hitBody == virtualBody)hitBody=null;
		
		
		if(hitBody!=null && ((Entity)hitBody.getUserData()).getFixedRotation()!=0)
		{
			m_level.setSelectedBody(hitBody);
			System.out.println("Selected");
		}
		
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {

		if(button == Input.Buttons.LEFT){
//			photonShootSound.play();
//			m_level.launchPhoton();
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		tmp = new Vector3(x, y, 0);
		camera.unproject(tmp);
		tmp.scl(Constants.WORLD_TO_BOX);

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
			((Mirror)hitBody.getUserData()).isSelected = true;
		}
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {return false;}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {return false;}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {return false;}

	@Override
	public boolean zoom(float initialDistance, float distance) {return false;}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {return false;}
}
