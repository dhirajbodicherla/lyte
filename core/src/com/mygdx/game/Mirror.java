package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Entity;

public class Mirror extends Entity{
	public Body b;
	private int length;
	private TextureRegion regMirror;
	
	public Mirror() {
		init();
	}
	
	public Mirror(EntityDef ed){
		super(ed);
		init();
	}
	
	private void init(){
		regMirror = Assets.instance.mirror.mirror;
		origin.set(size.x, size.y);
	}
	
	@Override
	public void render(SpriteBatch batch){
		TextureRegion reg = null;
		reg = regMirror;
		batch.draw(reg.getTexture(),
					pos.x - origin.x, pos.y - origin.y, 
					origin.x, origin.y, 
					size.x*2, size.y*2, 
					scale.x, scale.y, 
					angle,
					reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
	}
}
