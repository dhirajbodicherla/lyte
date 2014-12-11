package com.jsd.lyte;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Asteroid extends Entity{
	Sprite sprite; 
	Texture tex; 
	Pixmap pix; 
	
	public Asteroid(EntityDef ed,Vector2 bs, int index)
	{
		super(ed,bs, index);
		init();
	}
	public void dispose()
	{
		tex.dispose();
	}
	
	public void init()
	{
		int r = (int)(this.getRadius());
		
		pix = new Pixmap(r*2, r*2,Pixmap.Format.RGBA8888);
		int x = (int)(pix.getWidth()*0.5);
		int y = (int)(pix.getHeight()*0.5);
		pix.setColor(Color.ORANGE);
		pix.drawCircle(x, y, r);
		
		tex = new Texture(pix);
		sprite = new Sprite(tex);
		pix.dispose();
	}
	
	public void render(SpriteBatch sb)
	{
		super.update();
		float pixdim = this.getRadius();
		float x = this.pos.x - (pixdim);
		float y = this.pos.y - (pixdim);
		
		sb.begin();
		sprite.setPosition(x, y);
		sprite.setRotation(angle*MathUtils.radiansToDegrees);
        sprite.draw(sb);
		sb.end();
	}
}
