package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.Entity;

public class Laser extends Entity{
	public Body b;
	private int length;
	private TextureRegion regLaser;
	private Level level;
	
	public Laser() {
		init();
	}
	
	public Laser(EntityDef ed, Level level){
		super(ed);
		this.level = level;
		init();
	}
	
	private void init(){
		regLaser = Assets.instance.laser.laser;
		origin.set(size.x, size.y);
	}
	
	@Override
	public void render(SpriteBatch batch){
		TextureRegion reg = null;
		reg = regLaser;
		
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
	
	@Override
	public void update(float deltaTime){
		
	}
	
	public void shoot(float x, float y, int pointer, World w){
		float fireRadius= (float)(this.getSize().x * 0.5);
		Vector2 dir = new Vector2(x*fireRadius, y*fireRadius);
		Vector2 firePoint = dir.add(this.getPhysicsBody().getWorldCenter());
		float dx = x - this.getPhysicsBody().getWorldCenter().x;
		float dy = y - this.getPhysicsBody().getWorldCenter().y;
		float angle = (float)Math.atan2(dy,dx);
		this.angle = angle * MathUtils.radiansToDegrees;
		
		EntityDef ed = new EntityDef();
		ed.name = "photon";
		ed.angle = 0;
		ed.r = 0.3f;
		ed.shape = "circle";
		ed.type = "D";
		ed.x = 0f;
		ed.y = 0f;
		Photon p = new Photon(ed);
		p.setPos(firePoint);
		p.setPhysicsBody(Level.createPhysicsBody(ed, p));
		p.getPhysicsBody().applyLinearImpulse(x, y, p.getPhysicsBody().getWorldCenter().x, p.getPhysicsBody().getWorldCenter().y, true);
		
		this.getPhysicsBody().setTransform(this.getPhysicsBody().getWorldCenter(), angle);
		
		this.level.mPhotons.add(p);
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
