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
		origin.set(radius, radius);
	}
	
	@Override
	public void render(SpriteBatch batch){
		///Gdx.app.debug("asteroid", String.valueOf(pos.x) + '-' + String.valueOf(origin.x));
		TextureRegion reg = null;
		reg = regAsteroid;
		batch.draw(reg.getTexture(), 
					pos.x - origin.x, pos.y - origin.y,
					origin.x*2, origin.y*2,
					radius*2, radius*2, 
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
