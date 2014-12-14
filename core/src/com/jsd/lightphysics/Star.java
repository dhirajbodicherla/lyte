package com.jsd.lightphysics;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Star extends Entity{
	Sprite sprite;  
	Sprite flareSprite;
	Texture flareTex;	//solar flare
	
	public Star(EntityDef ed,Vector2 bs, int index)
	{
		super(ed,bs, index);
		init();
	}
	public void dispose()
	{
	}
	
	public void init()
	{
		
		Pixmap pFlare= new Pixmap((int)((this.size.x * 2.5)+2), 
		         (int)(this.size.y), 
		         Pixmap.Format.RGBA8888);
		
		int x = (int)(pFlare.getWidth());
		int y = (int)(pFlare.getHeight());
		
		Color orange = new Color(1.f,0.57f,0.16f,1.f);
		pFlare.setColor(orange);
		pFlare.drawRectangle(0, 0, x, y);
		pFlare.drawRectangle(0, 0, x+1, y+1);
		pFlare.drawRectangle(0, 0, x+2, y+2);
		
		Pixmap blurFlare = BlurUtils.blur(pFlare, 2, 2,true);
		
		flareTex = new Texture(blurFlare);
		flareSprite = new Sprite(flareTex);
		
		sprite = (Assets.instance.getSpriteAtlas()).createSprite(Constants.SPRITE_STAR);
		float w = sprite.getWidth();
		float h = sprite.getHeight();
		float sx = AssetFactory.setToScale(w, 2*this.size.x);
		float sy = AssetFactory.setToScale(h, 2*this.size.x);
		sprite.setScale(sx, sy);
		
		blurFlare.dispose();
	}
	
	public void render(SpriteBatch sb)
	{
		super.update();
		float x = this.pos.x;
		float y = this.pos.y;
		
		float w = this.size.x*2.5f;
		float h = this.size.y;
		
		float x2 = this.pos.x - ((w)*0.5f);
		float y2 = this.pos.y - ((h)*0.5f);
		
		//flare sprite
		flareSprite.setPosition(x2, y2);
		flareSprite.setRotation(angle*MathUtils.radiansToDegrees);
		
		//main Sprite
		sprite.setPosition(x, y);
		sprite.setCenter(x, y);
		sprite.setRotation(angle*MathUtils.radiansToDegrees);
		
		sb.begin();
		flareSprite.draw(sb);
        sprite.draw(sb);
		sb.end();
	}
}
