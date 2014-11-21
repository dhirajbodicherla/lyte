package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Entity;

public class BlackHole extends Entity{
	public Body b;
	private int length;
	private TextureRegion regBlackHole;
	
	public BlackHole() {
		init();
	}
	
	public BlackHole(EntityDef ed){
		super(ed);
		init();
	}
	
	private void init(){
		regBlackHole = Assets.instance.blackhole.blackhole;
		origin.set(radius, radius);
	}
	
	@Override
	public void render(SpriteBatch batch){
		TextureRegion reg = null;
		reg = regBlackHole;
		batch.draw(reg.getTexture(), 
					pos.x - origin.x, pos.y - origin.y,
					origin.x, origin.y, 
					radius*2, radius*2,
					scale.x, scale.y, 
					angle,
					reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
	}
}
