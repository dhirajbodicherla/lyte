package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
	
	public void shoot(float x, float y, int pointer, World w){		
		float fireRadius= (float)(this.getSize().x * 0.5);
		Vector2 dir = new Vector2(x*Constants.WORLD_TO_BOX*fireRadius, y*Constants.WORLD_TO_BOX*fireRadius);
		Vector2 firePoint = dir.add(this.getPhysicsBody().getWorldCenter());
		float dx = x - this.getPhysicsBody().getWorldCenter().x;
		float dy = y - this.getPhysicsBody().getWorldCenter().y;
		float angle = (float)Math.atan2(dy,dx);
		this.getPhysicsBody().setTransform(this.getPhysicsBody().getWorldCenter(), angle);
		Body circleBody = Level.createPhysicsBody(firePoint, w);
		circleBody.applyLinearImpulse(x * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX, circleBody.getWorldCenter().x, circleBody.getWorldCenter().y, true);
		
		Level.mPhotons.add(circleBody);
	}
	
	public Vector2 TrackMouse(int x, int y){
		
		float mx = Gdx.input.getX() * Constants.WORLD_TO_BOX;
		float my = (Constants.SCREEN_HEIGHT-Gdx.input.getY())*Constants.WORLD_TO_BOX;
		float dx = mx - this.getPhysicsBody().getWorldCenter().x;
		float dy = my - this.getPhysicsBody().getWorldCenter().y;
		float angle = (float)Math.atan2(dy,dx);
		Vector2 norm = new Vector2(dx, dy);
		norm.nor();
		dx = norm.x;
		dy = norm.y;
		this.getPhysicsBody().setTransform(this.getPhysicsBody().getWorldCenter(), angle);
		return new Vector2(dx, dy);
	}
}
