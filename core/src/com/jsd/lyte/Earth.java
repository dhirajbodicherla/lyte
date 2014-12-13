package com.jsd.lyte;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Earth extends Entity{
	Sprite sprite; 
	Texture tex; 
	Pixmap pix; 
	
	public Earth(EntityDef ed,Vector2 bs, int index)
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
		sprite = (Assets.instance.getSpriteAtlas()).createSprite(Constants.SPRITE_EARTH);
		float w = sprite.getWidth();
		float h = sprite.getHeight();
		float sx = AssetFactory.setToScale(w, 2*this.radius);
		float sy = AssetFactory.setToScale(h, 2*this.radius);
		sprite.setScale(sx, sy);
	}
	
	public void render(SpriteBatch sb)
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
