package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

public class Level implements GestureListener {

	public static final String TAG = Level.class.getName();
	private static World world;
	public String name;
	private ArrayList<EntityDef> entities;
	private ArrayList<Body> planets;
	private ArrayList<Asteroid> asteroids;
	public Laser laser;

	/* new defs from J's repo */
	private LevelCollection mLevels;

	private Space mSpace; /* background */
	private Earth mTarget;
	public Laser mSource;
	public ArrayList<BlackHole> mBlackholes;
	private ArrayList<Asteroid> mAsteroids;
	private ArrayList<Mirror> mMirrors;

	// Light Photons
	public ArrayList<Photon> mPhotons;

	public Level(String filename, World w) {
		this.world = w;
		init(filename, this.world);
	}

	private void init(String filename, World world) {
		mLevels = ParseFile("data/level.js");
		mSpace = new Space();
		mSpace.pos.set(-1, -1);
		mTarget = null;
		mSource = null;
		mBlackholes = new ArrayList<BlackHole>();
		mAsteroids = new ArrayList<Asteroid>();
		mMirrors = new ArrayList<Mirror>();
		mPhotons = new ArrayList<Photon>();

		LevelStructure ld = mLevels.list.get(2);

		int numMirrors = ld.mMirrors != null ? ld.mMirrors.size() : 0;
		int numAsteroids = ld.mAsteroids != null ? ld.mAsteroids.size() : 0;
		int numBlackholes = ld.mBlackholes != null ? ld.mBlackholes.size() : 0;

		Vector2 bs = new Vector2(ld.mBaseWidth, ld.mBaseHeight);

		mTarget = new Earth(ld.mTarget);
		mTarget.setPhysicsBody(createPhysicsBody(ld.mTarget, mTarget));

		mSource = new Laser(ld.mSource, this);
		mSource.setPhysicsBody(createPhysicsBody(ld.mSource, mSource));

		for (int i = 0; i < numMirrors; i++) {
			EntityDef ed = ld.mMirrors.get(i);
			Mirror e = new Mirror(ed);
			e.setPhysicsBody(createPhysicsBody(ed, e));
			mMirrors.add(e);
		}

		for (int i = 0; i < numAsteroids; i++) {
			EntityDef ed = ld.mAsteroids.get(i);
			Asteroid e = new Asteroid(ed);
			e.setPhysicsBody(createPhysicsBody(ed, e));
			mAsteroids.add(e);
		}

		for (int i = 0; i < numBlackholes; i++) {
			EntityDef ed = ld.mBlackholes.get(i);
			BlackHole e = new BlackHole(ed);
			e.setPhysicsBody(createPhysicsBody(ed, e));
			mBlackholes.add(e);
		}
	}

	public void render(SpriteBatch batch) {
		mSpace.render(batch);
		mTarget.render(batch);
		mSource.render(batch);
		for (Asteroid a : mAsteroids) {
			a.render(batch);
		}
		for (Mirror m : mMirrors) {
			m.render(batch);
		}
		for (BlackHole b : mBlackholes) {
			b.render(batch);
		}
		for (Photon p : mPhotons) {
			p.render(batch);
		}
	}

	public void update(float deltaTime) {
		mTarget.update(deltaTime);
		mSource.update(deltaTime);
		for (Asteroid a : mAsteroids) {
			a.update(deltaTime);
		}
		for (Mirror m : mMirrors) {
			m.update(deltaTime);
		}
		for (BlackHole b : mBlackholes) {
			b.update(deltaTime);
		}
		for (Photon p : mPhotons) {
			p.update(deltaTime);
		}
		blackHoleInfluence();
	}

	public void blackHoleInfluence() {
		for (int i = 0; i < mPhotons.size(); i++) {
			Vector2 bulletPos = mPhotons.get(i).getPhysicsBody()
					.getWorldCenter();
			for (int j = 0; j < mBlackholes.size(); j++) {
				Shape planetShape = mBlackholes.get(j).getPhysicsBody()
						.getFixtureList().get(0).getShape();
				float planetRadius = planetShape.getRadius();
				Vector2 planetPosition = mBlackholes.get(j).getPhysicsBody()
						.getWorldCenter();
				Vector2 planetDistance = new Vector2(0, 0);
				planetDistance.add(bulletPos);
				planetDistance.sub(planetPosition);
				float finalDistance = planetDistance.len();
				if (finalDistance <= planetRadius * 3f) {
					planetDistance.scl(-1.f);
					float vecSum = Math.abs(planetDistance.x)
							+ Math.abs(planetDistance.y);
					planetDistance.scl(5f * (1 / vecSum) * planetRadius
							/ finalDistance);
					mPhotons.get(i)
							.getPhysicsBody()
							.applyForce(
									planetDistance,
									mPhotons.get(i).getPhysicsBody()
											.getWorldCenter(), true);
				}
			}
		}
	}

	private LevelCollection ParseFile(String filename) {
		String fileContent = null;
		LevelCollection lvls = null;
		Json json = null;
		FileHandle handle = null;
		try {
			switch (Gdx.app.getType()) {
			case Desktop:
				String locRoot = Gdx.files.getLocalStoragePath();
				handle = Gdx.files.internal(locRoot + filename);
				fileContent = handle.readString();
				json = new Json();
				lvls = json.fromJson(LevelCollection.class, fileContent);

				break;

			case Android:
				handle = Gdx.files.internal(filename);
				fileContent = handle.readString();
				json = new Json();
				lvls = json.fromJson(LevelCollection.class, fileContent);

				break;
			}
		} catch (Exception e) {
			System.out.println("Something went wrong while loading the level");
			System.out.println(e.getMessage());
		}

		return lvls;
	}

	public static Body createPhysicsBody(EntityDef ed, Entity e) {
		BodyDef bodyDef;
		Shape bodyShape = null;
		FixtureDef fixtureDef = null;
		Body phyBody;

		float x = (ed.x);
		float y = (ed.y);
		float w = (ed.w);
		float h = (ed.h);
		float r = (ed.r);

		// float x = (ed.x/BaseSize.x)*Constants.SCREEN_WIDTH;
		// float y = (ed.y/BaseSize.y)*Constants.SCREEN_HEIGHT;
		// float w = (ed.w/BaseSize.x)*Constants.SCREEN_WIDTH;
		// float h = (ed.h/BaseSize.y)*Constants.SCREEN_HEIGHT;
		// float r = (ed.r/BaseSize.x)*Constants.SCREEN_WIDTH;

		bodyDef = new BodyDef();
		if (ed.type.equalsIgnoreCase("S")) {
			bodyDef.type = BodyType.StaticBody;
		}
		if (ed.type.equalsIgnoreCase("D")) {
			bodyDef.type = BodyType.DynamicBody;
		}

		if (ed.type.equalsIgnoreCase("K")) {
			bodyDef.type = BodyType.KinematicBody;
		}

		if (ed.shape.equalsIgnoreCase("rect")) {
			bodyShape = new PolygonShape();
			((PolygonShape) bodyShape).setAsBox(w, h);
		}
		if (ed.shape.equalsIgnoreCase("circle")) {
			bodyShape = new CircleShape();
			bodyShape.setRadius(r);
		}

		bodyDef.position.set(x, y);
		bodyDef.angle = (float) ((ed.angle) * Constants.DEGTORAD);
		bodyDef.fixedRotation = true;

		fixtureDef = new FixtureDef();
		fixtureDef.shape = bodyShape;
		fixtureDef.density = 0.4f;
		fixtureDef.friction = 0.2f;
		fixtureDef.restitution = 0.8f;

		phyBody = world.createBody(bodyDef);
		phyBody.createFixture(fixtureDef).setUserData(e);
		phyBody.setUserData(e);

		return phyBody;

	}

	public static Body createPhysicsBody(Vector2 pos, World w) {
		BodyDef circleDef = new BodyDef();
		circleDef.type = BodyType.DynamicBody;
		circleDef.position.set(pos);

		Body circleBody = world.createBody(circleDef);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(0.3f);

		FixtureDef circleFixture = new FixtureDef();
		circleFixture.shape = circleShape;
		circleFixture.density = 0.4f;
		circleFixture.friction = 0.2f;
		circleFixture.restitution = 0.8f;

		circleBody.createFixture(circleFixture);

		return circleBody;
	}

	@Override
	public boolean touchDown (float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap (float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress (float x, float y) {
		Gdx.app.debug("Level", "long press");
		return false;
	}

	@Override
	public boolean fling (float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan (float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop (float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom (float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch (Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}
