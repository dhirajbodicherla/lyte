package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
	public String id;
	public String name;
	public Vector2 pos; 
	public Vector2 size;
	public float angle; 
	public String type;
	public Body physicsBody;
	public float radius;
	public int fixedRotation;
	public int fixedPosition;
	
	public Vector2 origin;
	public Vector2 dimension;
	public Vector2 scale;
	
	public Entity(){
		this.pos = new Vector2();
		this.size = new Vector2(1,1);
		this.origin = new Vector2();
		this.dimension = new Vector2(1,1);
		this.scale = new Vector2(1,1);
		this.angle = 0;
	}
	
	public Entity(EntityDef ed){
		this.id = ed.id;
		this.pos = new Vector2(ed.x, ed.y);
		this.size = new Vector2(ed.w, ed.h);
		this.radius = ed.r;
		this.angle = ed.angle;
		this.fixedPosition = ed.fixedPosition;
		this.fixedRotation = ed.fixedRotation;
		this.origin = new Vector2();
		this.dimension = new Vector2(1,1);
		this.scale = new Vector2(1,1);
	}
	
	public void update(float deltaTime){ }
	
	public abstract void render(SpriteBatch batch);
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Vector2 getPos() {
		return pos;
	}
	public void setPos(Vector2 pos) {
		this.pos = pos;
	}
	public Vector2 getSize() {
		return size;
	}
	public void setSize(Vector2 size) {
		this.size = size;
	}
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
	public Body getPhysicsBody() {
		return physicsBody;
	}
	public void setPhysicsBody(Body physicsBody) {
		this.physicsBody = physicsBody;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getFixedRotation() {
		return fixedRotation;
	}

	public void setFixedRotation(int fixedRotation) {
		this.fixedRotation = fixedRotation;
	}

	public int getFixedPosition() {
		return fixedPosition;
	}

	public void setFixedPosition(int fixedPosition) {
		this.fixedPosition = fixedPosition;
	}
}
