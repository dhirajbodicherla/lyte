package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.Entity;

public class Asteroid extends Entity{
	public Body b;
	private int length;
	private TextureRegion regAsteroid;
	
	public Asteroid() {
		init();
	}
	
	public Asteroid(EntityDef ed){
		super(ed);
		init();
	}
	
	private void init(){
		regAsteroid = Assets.instance.asteroid.asteroid;
		dimension.set(new Vector2(0.5f, 0.5f));
//		this.getp
	}
	
	@Override
	public void render(SpriteBatch batch){
		
		TextureRegion reg = null;
		reg = regAsteroid;
		batch.draw(reg.getTexture(), 
					pos.x , pos.y, 
					origin.x, origin.y, 
					dimension.x, dimension.y, 
					scale.x, scale.y, 
					angle,
					reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
	}
	
	@Override
	public void update(float deltaTime){
		
	}
}
