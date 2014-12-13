package com.jsd.lyte;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Entity {
	protected int id;
	protected String name;
	protected Vector2 pos; 			//Maintain position in pixels
	protected Vector2 size;			//Maintain size in pixels
	protected float angle; 			//Maintain angle in degrees
	protected Body physicsBody;
	protected String type;
	protected float ir;				//Maintain influence radius in pixels
	protected float radius;			//Maintain radius in pixels
	protected int fixedRotation;
	protected int fixedPosition;
	
	public Vector2 origin;
	public Vector2 dimension;
	public Vector2 scale;
	
	
	public Entity(EntityDef ed, Vector2 bs, int index)	//index is the value at the arraylist
	{
		Vector2 vp = Assets.instance.queryViewport();
		this.name = ed.name;
		this.pos = new Vector2((ed.x/bs.x)*vp.x, (ed.y/bs.y)*vp.y);
		this.size = new Vector2((ed.w/bs.x)*vp.x, (ed.h/bs.y)*vp.y);
		this.radius = (ed.r/bs.x)*vp.x;
		this.ir = (ed.ir/bs.x)*vp.x;
		this.angle = ed.angle;
		this.fixedPosition = ed.fixedPosition;
		this.fixedRotation = ed.fixedRotation;
		this.origin = new Vector2(0,0);
		this.dimension = new Vector2(1,1);
		this.scale = new Vector2(1,1);
	}
	
	public void update()
	{
		//update position and angle
		this.pos.x = this.physicsBody.getPosition().x * Constants.BOX_TO_WORLD;
		this.pos.y = this.physicsBody.getPosition().y * Constants.BOX_TO_WORLD;
		this.angle = this.physicsBody.getAngle(); //get angle in radias from physics body;
	}
	public void render(SpriteBatch sb)
	{
		//render textures and lights
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getName() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
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

	public float getIr() {
		return ir;
	}

	public void setIr(float ir) {
		this.ir = ir;
	}
	
}