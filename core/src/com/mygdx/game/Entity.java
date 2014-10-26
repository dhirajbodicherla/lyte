package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Entity {
	private String name;
	private Vector2 pos; 
	private Vector2 size;
	private float angle; 
	private int type;
	private Body physicsBody; 
}
