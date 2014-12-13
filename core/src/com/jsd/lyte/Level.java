package com.jsd.lyte;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;

public class Level implements ContactListener{
	
	World m_world;
	//List of levels
	LevelCollection mLevels;
	//Current Level
	int currentLevel;
	//level over? 
	public boolean isSolved;	//initially set to false
	public boolean isGameOver;
	
	
	//Number of photons hits
	int photonCount;
	
	//1 - init
	//2 - running
	//3 - destroyed
	int gameState;
	
	//current Rotatable Body
	Body SelectedBody;
	
	
	//Game Elements
	private Entity mTarget;
	private Entity mSource;
	private ArrayList<BlackHole> mBlackholes;
	private ArrayList<Asteroid> mAsteroids;
	private ArrayList<Mirror> mMirrors;
	private ArrayList<Photon> mPhotons;
	
	//direction of shoot
	float dx, dy;

	
	public Level(String filename, World world, int level) {
		m_world = world;
		currentLevel = level -1 ;			// Levels always start with 0
		isGameOver = false;
		init(filename);
		
	}

	private void init(String filename) {
		gameState = 1;
		parseFile(filename);
		setupGameObjects();
		initLevel();
		m_world.setContactListener(this);
	}
	
	private void parseFile(String file)
	{
		String fileContent = null;
		Json json = null;
		FileHandle handle = null; 
		try{
			switch(Gdx.app.getType())
			{
			case Desktop:
				String locRoot = Gdx.files.getLocalStoragePath();
				handle = Gdx.files.internal(locRoot+file);
				fileContent = handle.readString();
				json = new Json();
				mLevels = json.fromJson(LevelCollection.class, fileContent);
				
				break;
				
			case Android:
				handle = Gdx.files.internal(file);
				fileContent = handle.readString();
				json = new Json();
				mLevels = json.fromJson(LevelCollection.class, fileContent);
				
				break;
			}
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong while loading the level");
			System.out.println(e.getMessage());
		}
	}

	public void nextLevel()
	{
		currentLevel++;
		if(currentLevel<0)
			currentLevel = 0;
//		if(currentLevel>mLevels.list.size()-1)
//			currentLevel = mLevels.list.size()-1;
		destroy();
//		Gdx.app.debug("level", String.valueOf(currentLevel) + String.valueOf(mLevels.list.size()-1));
		if(currentLevel == mLevels.list.size()-1){
			Gdx.app.debug("Level", "game is over and return");
			isGameOver = true;
//			return;
		}
		initLevel();
	}
	
	public void prevLevel()
	{
		currentLevel--;
		if(currentLevel<0)
			currentLevel = 0;
		if(currentLevel>mLevels.list.size()-1)
			currentLevel = mLevels.list.size()-1;
		
		destroy();
		initLevel();
	}
	
	private void setupGameObjects()
	{
		mTarget = null;
		mSource = null;
		SelectedBody = null;
		mBlackholes = new ArrayList<BlackHole>();
		mAsteroids = new ArrayList<Asteroid>();
		mMirrors = new ArrayList<Mirror>();
		mPhotons = new ArrayList<Photon>();
	}
	
	private void initLevel()
	{
		if(gameState!=2)	//If game is initialized or destroyed then it is allowed to create new objects
		{
			//Reset Photon count
			photonCount = 0;
			
			//Reset levelSolve
			isSolved = false;
			
			LevelDef ld = mLevels.list.get(currentLevel);
			
			int numMirrors = ld.mMirrors !=null ? ld.mMirrors.size() : 0;
			int numAsteroids= ld.mAsteroids !=null ? ld.mAsteroids.size() : 0;
			int numBlackholes = ld.mBlackholes !=null ? ld.mBlackholes.size() : 0;
			
			Vector2 bs = new Vector2(ld.mBaseWidth, ld.mBaseHeight);
			
			
			mTarget = new Earth(ld.mTarget,bs, 0); 
			mTarget.setPhysicsBody(PhysicsBuilder.createPhysicsBody(ld.mTarget, mTarget, bs, m_world));
			
			mSource = new Entity(ld.mSource,bs, 0); 
			mSource.setPhysicsBody(PhysicsBuilder.createPhysicsBody(ld.mSource, mSource, bs, m_world));
			
			
			for(int i = 0 ; i < numMirrors; i++)
			{
				EntityDef ed = ld.mMirrors.get(i);
				Mirror e = new Mirror(ed,bs, i);
				Body b = PhysicsBuilder.createPhysicsBody(ed, e, bs, m_world);
				e.setPhysicsBody(b);
				mMirrors.add(e);
			}
			
			for(int i = 0 ; i < numAsteroids; i++)
			{
				EntityDef ed = ld.mAsteroids.get(i);
				Asteroid e = new Asteroid(ed,bs, i);
				Body b = PhysicsBuilder.createPhysicsBody(ed, e, bs, m_world);
				b.setAngularVelocity(-0.3f);
				e.setPhysicsBody(b);
				mAsteroids.add(e);
			}
			
			for(int i = 0 ; i < numBlackholes; i++)
			{
				EntityDef ed = ld.mBlackholes.get(i);
				BlackHole e = new BlackHole(ed,bs, i);
				Body b = PhysicsBuilder.createPhysicsBody(ed, e, bs, m_world);
				b.setAngularVelocity(0.5f);
				b.setAngularDamping(0.f);
				e.setPhysicsBody(b);
				mBlackholes.add(e);
			}
			
			gameState = 2;	//Set Game State to running
		}
	}
	
	public void render(SpriteBatch batch) {
		
		mTarget.render(batch);
		
		for(BlackHole b : mBlackholes )
		{b.render(batch);}
		
		for(Photon p : mPhotons )
		{p.render(batch);}
		
		for(Mirror m : mMirrors)
		{m.render(batch);}
		
		for(Asteroid a : mAsteroids)
		{a.render(batch);}
	}

	public void update(Vector3 mouse) {
		if(gameState == 2)
		{
			TrackMouse(mouse);
			blackHoleInfluence();
		}
	}
	
	public void destroy()
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
				m_world.destroyBody(b);
			}
			
			for(int i = 0 ; i < numAsteroids; i++)
			{
				Body b = mAsteroids.get(i).getPhysicsBody();
				m_world.destroyBody(b);
			}
			for(int i = 0 ; i < numBlackholes; i++)
			{
				Body b = mBlackholes.get(i).getPhysicsBody();
				m_world.destroyBody(b);
			}
			for(int i = 0 ; i < numPhotons; i++)
			{
				Body b = mPhotons.get(i).getPhysicsBody();
				m_world.destroyBody(b);
			}
			
			m_world.destroyBody(mTarget.getPhysicsBody());
			m_world.destroyBody(mSource.getPhysicsBody());
			mTarget = null;
			mSource = null;
			mMirrors.clear();
			mAsteroids.clear();
			mBlackholes.clear();
			mPhotons.clear();
			gameState = 3;
		}
	}

	public void clockwiseRotate()
	{
		if(SelectedBody!=null && ((Entity)SelectedBody.getUserData()).getFixedRotation()!=0){
			float currentRotation = SelectedBody.getAngle();
			currentRotation-=0.1f;
			SelectedBody.setTransform(SelectedBody.getWorldCenter(), currentRotation);
		}
	}
	
	public void antiClockWiseRotate()
	{
		if(SelectedBody!=null && ((Entity)SelectedBody.getUserData()).getFixedRotation()!=0){
			float currentRotation = SelectedBody.getAngle();
			currentRotation+=0.1f;
			SelectedBody.setTransform(SelectedBody.getWorldCenter(), currentRotation);
		}
	}
	
	
	public void TrackMouse(Vector3 mouse)
	{
		if(gameState == 2)
		{
			dx = 320*Constants.WORLD_TO_BOX - mSource.getPhysicsBody().getWorldCenter().x;
			dy = 240*Constants.WORLD_TO_BOX - mSource.getPhysicsBody().getWorldCenter().y;
			float angle = (float)Math.atan2(dy,dx);
			Vector2 norm = new Vector2(dx, dy);
			norm.nor();
			dx = norm.x;
			dy = norm.y;
			mSource.getPhysicsBody().setTransform(mSource.getPhysicsBody().getWorldCenter(), angle);
		}
	}
	
	public void setSelectedBody(Body b)
	{
		SelectedBody = b;
	}
	public Body getSelectedBody()
	{
		return SelectedBody;
	}
	
	public void blackHoleInfluence() {
		if(gameState==2)
		{
			for(int i = 0 ; i < mPhotons.size() ; i++)
			{
				Vector2 bulletPos = mPhotons.get(i).getPhysicsBody().getWorldCenter();
				for(int j = 0 ; j < mBlackholes.size() ;j++)
				{
					Shape planetShape = mBlackholes.get(j).getPhysicsBody().getFixtureList().get(0).getShape();
					float planetRadius = planetShape.getRadius();
					float planetInfluenceRadius = mBlackholes.get(j).getIr()*Constants.WORLD_TO_BOX;
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
	public void launchPhoton()
	{
		if(gameState == 2)
		{
			float fireRadius= (float)(mSource.getSize().x * 0.5 * Constants.WORLD_TO_BOX) ;
			Vector2 dir = new Vector2(dx*fireRadius, dy*fireRadius);
			Vector2 firePoint = dir.add(mSource.getPhysicsBody().getWorldCenter());
			EntityDef ed = new EntityDef();
			ed.name = "p";
			ed.x = firePoint.x * Constants.BOX_TO_WORLD;
			ed.y = firePoint.y * Constants.BOX_TO_WORLD;
			ed.r = 5;
			Photon p = new Photon(ed,new Vector2(640,480), photonCount);
			Body circleBody = PhysicsBuilder.createPhysicsBody(ed, p, m_world);

			p.setPhysicsBody(circleBody);
			p.getPhysicsBody().applyLinearImpulse(dx*Constants.WORLD_TO_BOX, dy*Constants.WORLD_TO_BOX, circleBody.getWorldCenter().x, circleBody.getWorldCenter().y, true);
			mPhotons.add(p);
			++photonCount;
			
		}
	}
	
	public void removePhoton(Photon p)
	{
		int index = p.getId();
		mPhotons.remove(index);
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		Entity ed1 = (Entity) fa.getBody().getUserData();
		Entity ed2 = (Entity) fb.getBody().getUserData();
		if(ed1 != null && ed2!= null){
			if((ed1.name.equals("b") ||  ed1.name.equals("a"))&& ed2.name.equals("p")){
				final Fixture toRemove = fb;
				removePhoton(((Photon)fb.getBody().getUserData()));
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run () {
						m_world.destroyBody(toRemove.getBody());
					}
				});
			}
			
			if(ed1.name.equals("e") && ed2.name.equals("p")){
				isSolved = true;
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
}

