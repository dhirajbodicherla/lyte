package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Entity;

public class Photon extends Entity{
	public Body b;
	private int length;
	private TextureRegion regAsteroid;
	public boolean isDeleted = false;
	
	public Photon() {
		init();
	}
	
	public Photon(EntityDef ed){
		super(ed);
		init();
	}
	
	private void init(){
//		need to change the below to photon
		regAsteroid = Assets.instance.photon.photon;
		origin.set(radius, radius);
	}
	
	@Override
	public void render(SpriteBatch batch){
		if(isDeleted) return;
		TextureRegion reg = null;
		reg = regAsteroid;
		batch.draw(reg.getTexture(), 
					this.getPhysicsBody().getPosition().x - origin.x, this.getPhysicsBody().getPosition().y - origin.y, 
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
	
	@Override
	public void delete(){
		isDeleted = true;
	}
}
