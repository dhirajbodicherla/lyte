package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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
//		regAsteroid = Assets.instance.asteroid.asteroid;
//		origin.set(radius, radius);
		setAnimation(Assets.instance.asteroid.animAsteroid);
		stateTime = MathUtils.random(0.0f, 1.0f);
	}
	
	@Override
	public void render(SpriteBatch batch){
		///Gdx.app.debug("asteroid", String.valueOf(pos.x) + '-' + String.valueOf(origin.x));
		TextureRegion reg = null;
//		reg = regAsteroid;
		reg = animation.getKeyFrame(stateTime, true);
		batch.draw(reg.getTexture(), 
					pos.x - 1.0f, pos.y - 1.0f,
					origin.x, origin.y,
					radius*2, radius*2, 
					scale.x, scale.y, 
					angle,
					reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
	}
	
	@Override
	public void update(float deltaTime){
		stateTime += deltaTime;
	}
}
