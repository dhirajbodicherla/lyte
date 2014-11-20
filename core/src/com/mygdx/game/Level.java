package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

public class Level {

	public static final String TAG = Level.class.getName();
	private static World world;
	public String name;
	private ArrayList<EntityDef> entities;
	private ArrayList<Body> planets;
	private ArrayList<Asteroid> asteroids;
	public Laser laser;
	
	/* new defs from J's repo */
	private LevelCollection mLevels;
	
	private Entity mTarget;
	public Laser mSource;
	private ArrayList<BlackHole> mBlackholes;
	private ArrayList<Asteroid> mAsteroids;
	private ArrayList<Mirror> mMirrors;
	
	//Light Photons
	public static ArrayList<Body> mPhotons;

	public Level(String filename, World w) {
		this.world = w;
		init(filename, this.world);
	}

	private void init(String filename, World world) {
		mLevels= ParseFile("data/level.js");
		mTarget = null;
		mSource = null;
		mBlackholes = new ArrayList<BlackHole>();
		mAsteroids = new ArrayList<Asteroid>();
		mMirrors = new ArrayList<Mirror>();
		mPhotons = new ArrayList<Body>();
		
		LevelStructure ld = mLevels.list.get(2);
		
		int numMirrors = ld.mMirrors !=null ? ld.mMirrors.size() : 0;
		int numAsteroids= ld.mAsteroids !=null ? ld.mAsteroids.size() : 0;
		int numBlackholes = ld.mBlackholes !=null ? ld.mBlackholes.size() : 0;
		
		Vector2 bs = new Vector2(ld.mBaseWidth, ld.mBaseHeight);
		
		mTarget = new Entity(ld.mTarget) {
			
			@Override
			public void render(SpriteBatch batch) {
				// TODO Auto-generated method stub
				
			}
		};
		
		mTarget.setPhysicsBody(createPhysicsBody(ld.mTarget, mTarget, bs));
		
		mSource = new Laser(ld.mSource);
		mSource.setPhysicsBody(createPhysicsBody(ld.mSource, mSource, bs));

		for(int i = 0 ; i < numMirrors; i++) {
			EntityDef ed = ld.mMirrors.get(i);
			Mirror e = new Mirror(ed);
			e.setPhysicsBody(createPhysicsBody(ed, e, bs));
			mMirrors.add(e);
		}
		
		for(int i = 0 ; i < numAsteroids; i++)
		{
			EntityDef ed = ld.mAsteroids.get(i);
			Asteroid e = new Asteroid(ed);
			e.setPhysicsBody(createPhysicsBody(ed, e, bs));
			mAsteroids.add(e);
		}
		
		for(int i = 0 ; i < numBlackholes; i++)
		{
			EntityDef ed = ld.mBlackholes.get(i);
			BlackHole e = new BlackHole(ed);
			e.setPhysicsBody(createPhysicsBody(ed, e, bs));
			mBlackholes.add(e);
		}
		
		/*
		String locRoot = Gdx.files.getLocalStoragePath();
		FileHandle handle = Gdx.files.internal(filename);
		String fileContent = handle.readString();
		Json json = new Json();
		LevelStructure lvl = json.fromJson(LevelStructure.class, fileContent);

		asteroids = new ArrayList<Asteroid>();
		planets = new ArrayList<Body>();
		laser = new Laser();

		BodyDef bodyDef;
		Shape bodyShape = null;
		FixtureDef fixtureDef = null;
		Body phyBody;

		for (int i = 0; i < lvl.entities.length; i++) {
			EntityDef ed = lvl.entities[i];
			float x = ed.x;
			float y = ed.y;
			float w = ed.w;
			float h = ed.h;

			bodyDef = new BodyDef();
			if (ed.type.equalsIgnoreCase("S")) {
				bodyDef.type = BodyType.StaticBody;
			}
			if (ed.type.equals("D")) {
				bodyDef.type = BodyType.DynamicBody;
			}

			if (ed.type.equals("K")) {
				bodyDef.type = BodyType.KinematicBody;
			}

			// bodyDef.position.set(x * Constants.WORLD_TO_BOX, y *
			// Constants.WORLD_TO_BOX);

			if (ed.shape.equalsIgnoreCase("rect")) {
				bodyShape = new PolygonShape();
				((PolygonShape) bodyShape).setAsBox((w * 0.5f)
						* Constants.WORLD_TO_BOX, h * 0.5f
						* Constants.WORLD_TO_BOX);
			}
			if (ed.shape.equalsIgnoreCase("circle")) {
				bodyShape = new CircleShape();
				bodyShape.setRadius(ed.r * Constants.WORLD_TO_BOX);
			}

			fixtureDef = new FixtureDef();
			fixtureDef.shape = bodyShape;
			fixtureDef.density = 0.4f;
			fixtureDef.friction = 0.2f;
			fixtureDef.restitution = 0.8f;

			phyBody = world.createBody(bodyDef);
			phyBody.createFixture(fixtureDef);

			if (ed.type.equals("K")) {
				// kBody = phyBody;
				Laser l = new Laser();
				l.b = phyBody;
				l.pos = new Vector2(x * Constants.WORLD_TO_BOX, y
						* Constants.WORLD_TO_BOX);
			} else {
				Asteroid a = new Asteroid();
				a.b = phyBody;
				a.pos = new Vector2(x * Constants.WORLD_TO_BOX, y
						* Constants.WORLD_TO_BOX);
				asteroids.add(a);
			}
		}
		*/
	}

	public void render(SpriteBatch batch) {
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
		
		// planets
		
//		for(Entity m : mMirrors){
//			m.render(batch);
//		}
//		laser.render(batch);
	}

	public void update(float deltaTime) {
//		bunnyHead.update(deltaTime);
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
//		for (Photon p : mPhotons) {
//			p.update(deltaTime);
//		}
		blackHoleInfluence();
	}
	
	public void blackHoleInfluence() {
		for(int i = 0 ; i < mPhotons.size() ; i++) {
			Vector2 bulletPos = mPhotons.get(i).getWorldCenter();
			for(int j = 0 ; j < mBlackholes.size() ;j++) {
				Shape planetShape = mBlackholes.get(j).getPhysicsBody().getFixtureList().get(0).getShape();
				float planetRadius = planetShape.getRadius();
				Vector2 planetPosition = mBlackholes.get(j).getPhysicsBody().getWorldCenter();
				Vector2 planetDistance = new Vector2(0,0);
				planetDistance.add(bulletPos);
				planetDistance.sub(planetPosition);
				float finalDistance = planetDistance.len();
				if(finalDistance <= planetRadius*10.f) {
					planetDistance.scl(-1.f);
					float vecSum = Math.abs(planetDistance.x) + Math.abs(planetDistance.y);
					planetDistance.scl(0.5f*(1/vecSum)*planetRadius / finalDistance);
					mPhotons.get(i).applyForce(planetDistance, mPhotons.get(i).getWorldCenter(),true);
				}
				
			}
		}
	}
	
	private LevelCollection ParseFile(String filename) {
		String fileContent = null;
		LevelCollection lvls = null;
		Json json = null;
		FileHandle handle = null; 
		try{
			switch(Gdx.app.getType())
			{
			case Desktop:
				String locRoot = Gdx.files.getLocalStoragePath();
				handle = Gdx.files.internal(locRoot+filename);
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
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while loading the level");
			System.out.println(e.getMessage());
		}
	
		return lvls;
	}
	
	public Body createPhysicsBody(EntityDef ed, Entity e, Vector2 BaseSize) {
		BodyDef bodyDef; 
		Shape bodyShape = null;
		FixtureDef fixtureDef = null; 
		Body phyBody;
		
		float x = (ed.x/BaseSize.x)*Constants.SCREEN_WIDTH;
		float y = (ed.y/BaseSize.y)*Constants.SCREEN_HEIGHT;
		float w = (ed.w/BaseSize.x)*Constants.SCREEN_WIDTH;
		float h = (ed.h/BaseSize.y)*Constants.SCREEN_HEIGHT;
		float r = (ed.r/BaseSize.x)*Constants.SCREEN_WIDTH;
		
		
		bodyDef = new BodyDef();
		if(ed.type.equalsIgnoreCase("S"))
		{
			bodyDef.type = BodyType.StaticBody;
		}
		if(ed.type.equalsIgnoreCase("D"))
		{
			bodyDef.type = BodyType.DynamicBody;
		}
		
		if(ed.type.equalsIgnoreCase("K"))
		{
			bodyDef.type = BodyType.KinematicBody;
		}
		
		if(ed.shape.equalsIgnoreCase("rect"))
		{
			bodyShape = new PolygonShape();
			((PolygonShape) bodyShape).setAsBox((w*0.5f)*Constants.WORLD_TO_BOX, h*0.5f*Constants.WORLD_TO_BOX);
		}
		if(ed.shape.equalsIgnoreCase("circle"))
		{
			bodyShape = new CircleShape();
			bodyShape.setRadius(r * Constants.WORLD_TO_BOX);
		}
		
		bodyDef.position.set(x*Constants.WORLD_TO_BOX, y*Constants.WORLD_TO_BOX);
		bodyDef.angle = (float) ((ed.angle)*Constants.DEGTORAD);
		bodyDef.fixedRotation = true;
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = bodyShape; 
		fixtureDef.density = 0.4f;
		fixtureDef.friction = 0.2f; 
		fixtureDef.restitution = 0.8f;
		
		phyBody = world.createBody(bodyDef);
		phyBody.createFixture(fixtureDef);
		phyBody.setUserData(e);
		
		return phyBody;

	}
	
	public static Body createPhysicsBody(Vector2 pos, World w) {
		BodyDef circleDef = new BodyDef();
		circleDef.type = BodyType.DynamicBody;
		circleDef.position.set(pos);
		
		Body circleBody = world.createBody(circleDef);

		CircleShape circleShape = new CircleShape(); 
		circleShape.setRadius(5 * Constants.WORLD_TO_BOX);

		FixtureDef circleFixture = new FixtureDef(); 
		circleFixture.shape = circleShape; 
		circleFixture.density = 0.4f;
		circleFixture.friction = 0.2f; 
		circleFixture.restitution = 0.8f; 

		circleBody.createFixture(circleFixture);
		
		return circleBody;
	}
	
}
