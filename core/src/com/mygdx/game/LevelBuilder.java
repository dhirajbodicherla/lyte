package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;

public final class LevelBuilder {
	
	public static LevelCollection ParseFile(String filename)
	{
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
	
	public static Body createPhysicsBody(EntityDef ed, Entity e, Vector2 BaseSize, World world) 
	{
		BodyDef bodyDef; 
		Shape bodyShape = null;
		FixtureDef fixtureDef = null; 
		Body phyBody;
		
		float x = (ed.x/BaseSize.x)*MyGdxGame.SCREEN_WIDTH;
		float y = (ed.y/BaseSize.y)*MyGdxGame.SCREEN_HEIGHT;
		float w = (ed.w/BaseSize.x)*MyGdxGame.SCREEN_WIDTH;
		float h = (ed.h/BaseSize.y)*MyGdxGame.SCREEN_HEIGHT;
		float r = (ed.r/BaseSize.x)*MyGdxGame.SCREEN_WIDTH;
		
		
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
			((PolygonShape) bodyShape).setAsBox((w*0.5f)*MyGdxGame.WORLD_TO_BOX, h*0.5f*MyGdxGame.WORLD_TO_BOX);
		}
		if(ed.shape.equalsIgnoreCase("circle"))
		{
			bodyShape = new CircleShape();
			bodyShape.setRadius(r * MyGdxGame.WORLD_TO_BOX);
		}
		
		bodyDef.position.set(x*MyGdxGame.WORLD_TO_BOX, y*MyGdxGame.WORLD_TO_BOX);
		bodyDef.angle = (float) ((ed.angle)*MyGdxGame.DEGTORAD);
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
	
	public static Body createPhysicsBody(Vector2 pos, World world)
	{
		BodyDef circleDef = new BodyDef();
		circleDef.type = BodyType.DynamicBody;
		circleDef.position.set(pos);

		Body circleBody = world.createBody(circleDef);

		CircleShape circleShape = new CircleShape(); 
		circleShape.setRadius(5 * MyGdxGame.WORLD_TO_BOX);

		FixtureDef circleFixture = new FixtureDef(); 
		circleFixture.shape = circleShape; 
		circleFixture.density = 0.4f;
		circleFixture.friction = 0.2f; 
		circleFixture.restitution = 0.8f; 

		circleBody.createFixture(circleFixture);
		
		return circleBody;
	}
}
