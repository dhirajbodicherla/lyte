package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Entity;

public class Space extends Entity{
	public Body b;
	private int length;
	private TextureRegion regMirror;
	
	public Space() {
		init();
	}
	
	private void init(){
		regMirror = Assets.instance.space.space;
		origin.set(size.x, size.y);
	}
	
	@Override
	public void render(SpriteBatch batch){
		TextureRegion reg = null;
		reg = regMirror;
		batch.draw(reg.getTexture(),
					-30,-30, 
					origin.x, origin.y, 
					40, 40, 
					scale.x, scale.y, 
					angle,
					reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
	}
}
