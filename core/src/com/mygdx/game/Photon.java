package com.mygdx.game;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Photon extends Entity{
	
	PointLight light;
	Sprite sprite;
	Texture tex;
	Pixmap pix; 
	
	
	public Photon(EntityDef ed, int index) {
		super(ed,index);
		init();
	}
	
	public void init()
	{
		int r = (int)(this.getRadius());
		pix = new Pixmap(r*2, r*2,Pixmap.Format.RGBA8888);
		int x = (int)(pix.getWidth()*0.5);
		int y = (int)(pix.getHeight()*0.5);
		
		pix.setColor(Color.MAROON);
		pix.drawCircle(x, y, r);
		pix.fillCircle(x, y, r);
		
		tex = new Texture(pix);
		sprite = new Sprite(tex);
	}
	
	public void render(SpriteBatch sb)
	{
		super.update();
		float pixdim = (this.getRadius());
		float x = (this.pos.x) - (pixdim);
		float y = (this.pos.y) - (pixdim);
		
		sb.begin();
		sprite.setPosition(x, y);
		sprite.setRotation(angle*MathUtils.radiansToDegrees);
        sprite.draw(sb);
		sb.end();
	}
}
