package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
	public String name;
	public Vector2 pos; 
	public Vector2 size;
	public float angle; 
	public int type;
	public Body physicsBody;
	public Vector2 origin;
	public Vector2 dimension;
	public Vector2 scale;
	
	public Entity(){
		pos = new Vector2();
		size = new Vector2(1,1);
		origin = new Vector2();
		dimension = new Vector2(1,1);
		scale = new Vector2(1,1);
		angle = 0;
	}
	
	public void update(float deltaTime){ }
	
	public abstract void render(SpriteBatch batch);
}
