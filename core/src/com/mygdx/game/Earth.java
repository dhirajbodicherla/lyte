package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Entity;

public class Earth extends Entity{
	public Body b;
	private int length;
	private TextureRegion regEarth;
	
	public Earth() {
		init();
	}
	
	public Earth(EntityDef ed){
		super(ed);
		init();
	}
	
	private void init(){
//		regEarth = Assets.instance.earth.earth;
//		origin.set(radius, radius);
		setAnimation(Assets.instance.earth.animEarth);
		stateTime = MathUtils.random(0.0f, 1.0f);
	}
	
	@Override
	public void render(SpriteBatch batch){
		
		TextureRegion reg = null;
		reg = animation.getKeyFrame(stateTime, true);
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
		stateTime += deltaTime;
	}
}
