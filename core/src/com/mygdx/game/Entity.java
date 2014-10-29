package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
	private String name;
	private Vector2 pos; 
	private Vector2 size;
	private float angle; 
	private int type;
	private Body physicsBody;
	
	public void update(float deltaTime){ }
	
	public abstract void render(SpriteBatch batch);
}
