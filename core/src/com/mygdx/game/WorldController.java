package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Constants;

public class WorldController extends InputAdapter implements InputProcessor, Disposable {

	private Game game;
	public CameraHelper cameraHelper;
	public Level level;
	public int lives;
	public int score;
	public World world;
	private OrthographicCamera camera;
	private Circle c1 = new Circle();
	private Circle c2 = new Circle();

	private static final String TAG = WorldController.class.getName();

	public WorldController(Game game) {
		this.game = game;
		init();
	}

	private void initLevel() {
		score = 0;
		level = new Level("data/level.json", world);
	}

	private void init() {
		world = new World(new Vector2(0.0f, 0.0f), false);
		
		world.setContactListener(new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beginContact(Contact contact) {
				// TODO Auto-generated method stub
				Fixture fa = contact.getFixtureA();
				Fixture fb = contact.getFixtureB();
				Entity ed1 = (Entity) fa.getUserData();
				Entity ed2 = (Entity) fb.getUserData();
				if(ed1.name.equals("photon") && ed2.name.equals("blackhole")){
					Gdx.app.debug("contact 1", ed2.name+" hit "+ed1.name);
				}
				if(ed1.name.equals("blackhole") && ed2.name.equals("photon")){
					final Fixture toRemove = fb;
					((Entity)fb.getUserData()).delete();
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run () {
//							((Entity)toRemove.getUserData()).delete();
							world.destroyBody(toRemove.getBody());
						}
					});
				}
			}
		});
		
		cameraHelper = new CameraHelper();
		Gdx.input.setInputProcessor(this);
		lives = Constants.LIVES_START;
		Constants.SCREEN_WIDTH = Gdx.graphics.getWidth();
		Constants.SCREEN_HEIGHT = Gdx.graphics.getHeight();
		initLevel();
	}

	public void update(float deltaTime) {
		world.step(1/60.f, 6, 2);
		handleDebugInput(deltaTime);
		level.update(deltaTime);
		handleInputGame(deltaTime);
		cameraHelper.update(deltaTime);
		//testCollisions();
	}
	
	private void testCollisions(){
//		Gdx.app.debug("testCollections", String.valueOf(level.mPhotons.size()) + ':' + String.valueOf(level.mBlackholes.size()) );
		for(Photon p : level.mPhotons){
			Vector2 v1 = p.getPhysicsBody().getWorldCenter();
			c1.set(v1.x, v1.y, p.radius);
			for(BlackHole b : level.mBlackholes){
				Vector2 v2 = b.getPhysicsBody().getWorldCenter();
				c2.set(v2.x, v2.y, b.radius);
				if(!c1.overlaps(c2)) continue;
				Gdx.app.debug("photon", String.valueOf(v2.x) + ':' + String.valueOf(v2.y) + ':' + String.valueOf(b.radius));
				photonDisappearsIntoBlackHole(p, b);
				break;
			}
		}
	}
	
	private void photonDisappearsIntoBlackHole(Photon p, BlackHole b){
		Gdx.app.debug("testing collisions bro", p.name + " hit " + b.name);
		p.delete();
//		world.destroyBody(p.getPhysicsBody());
	}

	private void backToMenu() {
		// switch to menu screen
		game.setScreen(new MenuScreen(game));
	}

	private Pixmap createProceduralPixmap(int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		// Fill square with red color at 50% opacity
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		// Draw a yellow-colored X shape on square
		pixmap.setColor(1, 1, 0, 1);
		pixmap.drawLine(0, 0, width, height);
		pixmap.drawLine(width, 0, 0, height);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, width, height);
		return pixmap;
	}

	private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop)
			return;

		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.UP))
			moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN))
			moveCamera(0, -camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
			cameraHelper.setPosition(0, 0);
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD))
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
		
	}
	
	public void setCamera(OrthographicCamera camera){
		this.camera = camera;
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

	@Override
	public boolean keyUp(int keycode) {
		// Reset game world
		if (keycode == Keys.R) {
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		} else if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			backToMenu();
		}

		return false;
	}
	
	private void handleInputGame (float deltaTime) {
		
	}
	
	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		Vector3 worldCoordinates = new Vector3(x, y, 0);
		camera.unproject(worldCoordinates);
		level.mSource.shoot(worldCoordinates.x, worldCoordinates.y, pointer, world);
		return false;
	}
	
	@Override
	public void dispose(){
		if (world != null) world.dispose();
	}
}