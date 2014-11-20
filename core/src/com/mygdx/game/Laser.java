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
	
	@Override
	public void update(float deltaTime){
		
	}
	
	public void shoot(int x, int y, int pointer, World w){
		Vector2 norm = TrackMouse(x,y);
		float fireRadius= (float)(this.getSize().x * 1.5);
		Vector2 dir = new Vector2(norm.x*Constants.WORLD_TO_BOX*fireRadius, norm.y*Constants.WORLD_TO_BOX*fireRadius);
		Vector2 firePoint = dir.add(this.getPhysicsBody().getWorldCenter());
		Body circleBody = Level.createPhysicsBody(firePoint, w);
		circleBody.applyLinearImpulse(norm.x/20, norm.y/20, circleBody.getWorldCenter().x, circleBody.getWorldCenter().y, true);
		
		Level.mPhotons.add(circleBody);
	}
	
	public Vector2 TrackMouse(int x, int y){
		
		float mx = x * Constants.WORLD_TO_BOX;
		float my = (Constants.SCREEN_HEIGHT-Gdx.input.getY())*Constants.WORLD_TO_BOX;
		float dx = x * Constants.WORLD_TO_BOX - this.getPhysicsBody().getWorldCenter().x;
		float dy = (Constants.SCREEN_HEIGHT-Gdx.input.getY())*Constants.WORLD_TO_BOX - this.getPhysicsBody().getWorldCenter().y;
		float angle = (float)Math.atan2(dy,dx);
		Vector2 norm = new Vector2(dx, dy);
		norm.nor();
		dx = norm.x;
		dy = norm.y;
		this.getPhysicsBody().setTransform(this.getPhysicsBody().getWorldCenter(), angle);
		return new Vector2(dx, dy);
	}
}
