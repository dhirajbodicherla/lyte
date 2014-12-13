package com.jsd.lyte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameStage extends Stage implements GestureListener{
	
	public static float SCREEN_WIDTH, SCREEN_HEIGHT;
	private OrthographicCamera camera; 
	private SpriteBatch batch;
	
	/* Box2d Stuff */ 
	World world; 
	Box2DDebugRenderer renderer;
	Vector2 VIEWPORT;
	
//	boolean isMouseDown;
	
	Vector3 mouse;
	
	//Mouse Joint
	private MouseJoint mJoint;
	private Body virtualBody; 	//Virtual ground body for the mouseJoint
	private Body hitBody;
	private Vector3 tmp;
	private Vector2 tmp2;
	private Sound photonShootSound;
	public GestureDetector gd;
	private LightPhysics game;
	public Level m_level; 
	private Boolean gameIsPaused = false;
	
	
	public GameStage(int level, LightPhysics game)
	{
		VIEWPORT = Assets.instance.queryViewport();
		gd = new GestureDetector(this);
//        Gdx.input.setInputProcessor(gd);
		gd.setLongPressSeconds(2);
		this.game = game;
		this.init(level);
	}
	
	public void initWorld(int level)
	{
		world = new World(new Vector2(0.0f, 0.0f), false);
		renderer = new Box2DDebugRenderer();
		m_level = new Level("data/level.js", world, level);
		//photonShootSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/game.wav"));
	}
	
	public void setupCamera()
	{
		camera = new OrthographicCamera(VIEWPORT.x,VIEWPORT.y);	//Defaults to screen size
//		camera.position.set(640*0.5f, 320*0.5f,0);
		//camera.position.set(0,0,0);
		camera.setToOrtho(false);
		camera.update();
	}
	public void resize(int width, int height)
	{
		camera.viewportHeight = VIEWPORT.y;
		camera.viewportWidth = (VIEWPORT.y / (float) height)
				* (float) width;
		camera.position.set(camera.viewportWidth / 2,
				camera.viewportHeight / 2, 0);
		camera.update();
	}
	public void initRendering()
	{
		//Init Rendering Batch
		batch = new SpriteBatch();		
	}
	
	public void initInteractivity()
	{
		mouse = new Vector3();
		
		//Initialize Mouse Joints
		virtualBody = world.createBody(new BodyDef());
		
		tmp = new Vector3();
		tmp2 = new Vector2();
		
	}


	public void init(int level)
	{
		initWorld(level);
		setupCamera();
		initRendering();
		initInteractivity();
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		Gdx.app.log("my app", "keydown");
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
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
	
//	@Override
//	public boolean touchDown(int x, int y, int pointer, int button) 
//	{
//		Gdx.app.log("my app", "touchdown");
//		return false;
//	}

//	@Override
//	public boolean touchUp(int x, int y, int pointer, int button) {
//		Gdx.app.log("my app", "touchup");
//		//mouse = new Vector2(Gdx.input.getX(), Gdx.input.getY());
//		
//		//isMouseDown = false;
//		if(mJoint!=null)
//		{
//			((Mirror)hitBody.getUserData()).isSelected = false;
//			hitBody.setType(BodyType.StaticBody);
//			world.destroyJoint(mJoint);
//            mJoint = null;
//            hitBody=null;
//		}
//		return false;
//	}

//	@Override
//	public boolean touchDragged(int x, int y, int pointer) {
//		Gdx.app.log("my app", "touchdragged");
//		if(mJoint!=null){
//			tmp = new Vector3(x, y, 0);
//			camera.unproject(tmp);
//			tmp.scl(Constants.WORLD_TO_BOX);
//			mJoint.setTarget(tmp2.set(tmp.x,tmp.y));
//		}
//		return false;
//	}

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
		batch.setProjectionMatrix(camera.combined);
		//Render Level here
		
		m_level.render(batch);
		m_level.update(mouse);
	
		renderer.render(world, cameraCopy.scl(Constants.BOX_TO_WORLD));
		if(!gameIsPaused){
			world.step(1/60f, 6, 2);
		}
	}
	
	
	public void dispose() {
		world.dispose();
		m_level.destroy();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		tmp = new Vector3(x, y, 0);
		camera.unproject(tmp);
		tmp.scl(Constants.WORLD_TO_BOX);
		
		world.QueryAABB(queryCallBack, tmp.x-0.0001f, tmp.y-0.0001f, tmp.x+0.0001f, tmp.y+0.0001f);
		if(hitBody == virtualBody)hitBody=null;
		
		
		if(hitBody!=null && ((Entity)hitBody.getUserData()).getFixedRotation()!=0)
		{
			Gdx.input.vibrate(25);
			((Mirror)(hitBody.getUserData())).isSelected=true;
			m_level.setSelectedBody(hitBody);
		}
		
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		Gdx.app.log("my app", "longpress");
		tmp = new Vector3(x, y, 0);
		camera.unproject(tmp);
		tmp.scl(Constants.WORLD_TO_BOX);

		hitBody = null;
		world.QueryAABB(queryCallBack, tmp.x-0.0001f, tmp.y-0.0001f, tmp.x+0.0001f, tmp.y+0.0001f);
		if(hitBody == virtualBody)hitBody=null;
		
		if(hitBody!=null && ((Entity)hitBody.getUserData()).getFixedPosition()!=0)
		{
			Gdx.input.vibrate(25);
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
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if(mJoint!=null){
			tmp = new Vector3(x, y, 0);
			camera.unproject(tmp);
			tmp.scl(Constants.WORLD_TO_BOX);
			mJoint.setTarget(tmp2.set(tmp.x,tmp.y));
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
//		Gdx.app.log("my app", "touchup");
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
	public boolean zoom(float initialDistance, float distance) {return false;}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
	
	public void pause(){
		gameIsPaused = true;
	}
	
	public void resume(){
		gameIsPaused = false;
	}
	
}
