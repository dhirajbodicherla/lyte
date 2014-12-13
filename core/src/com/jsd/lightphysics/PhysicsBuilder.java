package com.jsd.lightphysics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicsBuilder {
	
	public static Body createPhysicsBody(EntityDef ed, 
										 Entity e, 
										 Vector2 BaseSize, 
										 World world) 
	{
		BodyDef bodyDef; 
		Shape bodyShape = null;
		FixtureDef fixtureDef = null; 
		Body phyBody;
		
		Vector2 VIEWPORT = Assets.instance.queryViewport();
		
		float x = (ed.x/BaseSize.x) * VIEWPORT.x *Constants.WORLD_TO_BOX;
		float y = (ed.y/BaseSize.y) * VIEWPORT.y *Constants.WORLD_TO_BOX;
		float w = (ed.w/BaseSize.x) * VIEWPORT.x *Constants.WORLD_TO_BOX;
		float h = (ed.h/BaseSize.y) * VIEWPORT.y *Constants.WORLD_TO_BOX;
		float r = (ed.r/BaseSize.x) * VIEWPORT.x *Constants.WORLD_TO_BOX;
		
		
		/*float x = ed.x *Constants.WORLD_TO_BOX;
		float y = ed.y *Constants.WORLD_TO_BOX;
		float w = ed.w *Constants.WORLD_TO_BOX;
		float h = ed.h *Constants.WORLD_TO_BOX;
		float r = ed.r *Constants.WORLD_TO_BOX;*/
		
		
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
			((PolygonShape) bodyShape).setAsBox((w*0.5f), h*0.5f);
		}
		if(ed.shape.equalsIgnoreCase("circle"))
		{
			bodyShape = new CircleShape();
			bodyShape.setRadius(r);
		}
		
		bodyDef.position.set(x, y);
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
	
	public static Body createPhysicsBody(EntityDef ed, Photon p, World world)
	{
		float x = (ed.x*Constants.WORLD_TO_BOX);
		float y = (ed.y*Constants.WORLD_TO_BOX);
		float r = (ed.r*Constants.WORLD_TO_BOX);
		
		BodyDef circleDef = new BodyDef();
		circleDef.type = BodyType.DynamicBody;
		circleDef.position.set(x, y);

		Body circleBody = world.createBody(circleDef);

		CircleShape circleShape = new CircleShape(); 
		circleShape.setRadius(r);

		FixtureDef circleFixture = new FixtureDef(); 
		circleFixture.shape = circleShape; 
		circleFixture.density = 0.4f;
		circleFixture.friction = 0.2f; 
		circleFixture.restitution = 0.8f; 

		circleBody.createFixture(circleFixture);
		circleBody.setUserData(p);
		
		return circleBody;
	}

}
