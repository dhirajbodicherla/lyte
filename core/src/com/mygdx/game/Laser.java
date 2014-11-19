package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Entity;

public class Laser extends Entity{
	public Body b;
	private int length;
	private TextureRegion regLaser;
	
	public Laser() {
		init();
	}
	
	public Laser(EntityDef ed){
		super(ed);
		init();
	}
	
	private void init(){
		regLaser = Assets.instance.laser.laser;
//		dimension.set(new Vector2(0.5f, 0.5f));
	}
	
	@Override
	public void render(SpriteBatch batch){
		TextureRegion reg = null;
		reg = regLaser;
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
	
	public void shoot(int dx, int dy, int pointer){
		
		float fireRadius= (float)(this.getSize().x * 0.5);
		Vector2 dir = new Vector2(dx*Constants.WORLD_TO_BOX*fireRadius, dy*Constants.WORLD_TO_BOX*fireRadius);
		Vector2 firePoint = dir.add(this.getPhysicsBody().getWorldCenter());
		Body circleBody = Level.createPhysicsBody(firePoint);
		circleBody.applyLinearImpulse(dx*Constants.WORLD_TO_BOX*100, dy*Constants.WORLD_TO_BOX*100, circleBody.getWorldCenter().x, circleBody.getWorldCenter().y, true);
		Level.mPhotons.add(circleBody);
	}
}
