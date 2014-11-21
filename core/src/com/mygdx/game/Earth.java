package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Entity;

public class Earth extends Entity{
	public Body b;
	private int length;
	private TextureRegion regAsteroid;
	
	public Earth() {
		init();
	}
	
	public Earth(EntityDef ed){
		super(ed);
		init();
	}
	
	private void init(){
//		need to change the below to Earth
		regAsteroid = Assets.instance.asteroid.asteroid;
//		origin.set(size.x / 2, size.y / 2);
	}
	
	@Override
	public void render(SpriteBatch batch){
		
		TextureRegion reg = null;
		reg = regAsteroid;
		batch.draw(reg.getTexture(), 
					pos.x, pos.y, 
					origin.x, origin.y, 
					size.x, size.y, 
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
