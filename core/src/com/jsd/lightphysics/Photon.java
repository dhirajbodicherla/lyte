package com.jsd.lightphysics;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Photon extends Entity{
	
	PointLight light;
	Sprite sprite;
	private boolean isDeleted;
	
	
	public Photon(EntityDef ed, Vector2 bs,int index) {
		super(ed,bs,index);
		init();
	}
	
	public void init()
	{
		sprite = Assets.instance.getSpriteAtlas().createSprite(Constants.SPRITE_PHOTON);
		float w = sprite.getWidth();
		float h = sprite.getHeight();
		float sx = AssetFactory.setToScale(w, 2*this.radius);
		float sy = AssetFactory.setToScale(h, 2*this.radius);
		sprite.setScale(sx, sy);
		isDeleted = false;
	}
	
	public void render(SpriteBatch sb)
	{
		if(!isDeleted)
		{
			super.update();
			float x = this.pos.x;// - (pixdim);
			float y = this.pos.y;// - (pixdim);
			
			sb.begin();
			sprite.setPosition(x, y);
			sprite.setCenter(x, y);
			sprite.setRotation(angle*MathUtils.radiansToDegrees);
	        sprite.draw(sb);
			sb.end();
		}
	}
	
	public void setDelete(boolean b)
	{
		isDeleted = b;
	}
	public boolean getIsDelete()
	{
		return isDeleted;
	}
}
