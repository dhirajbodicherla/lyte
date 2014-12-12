package com.jsd.lyte;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jsd.lyte.Entity;

public class Mirror extends Entity{
	
	Texture selection;
	Texture type_tex;
	private Sprite sprite;
	private Sprite type;	//if rotatable - set to green, if movable set to blue, if both set to white
	private Sprite select;
	
	public boolean isSelected;
	
	
	public Mirror(EntityDef ed,Vector2 bs, int index){
		super(ed,bs, index);
		init();
	}
	
	private void init(){

		Pixmap pSelection= new Pixmap((int)(this.size.x+10), 
		         (int)(this.size.y+10), 
		         Pixmap.Format.RGBA8888);
		
		Pixmap pType = new Pixmap((int)(this.size.x+10), 
		         (int)(this.size.y+10), 
		         Pixmap.Format.RGBA8888);
		
		
		int x2 = (int)(pSelection.getWidth());
		int y2 = (int)(pSelection.getHeight());
				
		
		pSelection.setColor(Color.YELLOW);
		pSelection.drawRectangle(0, 0, x2, y2);
		
		if(fixedPosition!=0)
		{
			pType.setColor(Color.BLUE);
			pType.drawRectangle(0, 0, x2, y2);
		}
		if(fixedRotation!=0)
		{
			pType.setColor(Color.GREEN);
			pType.drawRectangle(0, 0, x2, y2);
		}
		
		if(fixedPosition!=0 && fixedRotation!=0)
		{
			pType.setColor(Color.WHITE);
			pType.drawRectangle(0, 0, x2, y2);
		}
		
		
		
		Pixmap blurSel = BlurUtils.blur(pSelection, 2, 2, true);
		Pixmap blurType = BlurUtils.blur(pType, 2, 2, true);
		
		
		selection = new Texture(blurSel);
		type_tex = new Texture(blurType);
		
		sprite = (Assets.instance.getSpriteAtlas()).createSprite(Constants.SPRITE_MIRROR);
		
		float sx = AssetFactory.setToScale(sprite.getWidth(), this.size.x);
		float sy = AssetFactory.setToScale(sprite.getHeight(), this.size.y);
		
		
		sprite.setScale(sx, sy);
		select = new Sprite(selection);
		type = new Sprite(type_tex);
		
		isSelected = false;
		
		blurSel.dispose();
		blurType.dispose();
		
	}
	
	
	
	public void render(SpriteBatch sb){
		super.update();
		
		float w = this.size.x;
		float h = this.size.y;
		
		float x = this.pos.x;
		float y = this.pos.y;
		
		float x2 = this.pos.x - ((w+10)*0.5f);
		float y2 = this.pos.y - ((h+10)*0.5f);
		
		sprite.setPosition(x, y);
		sprite.setCenter(x, y);
		sprite.setRotation(angle*MathUtils.radiansToDegrees);
		
		
		select.setPosition(x2, y2);
		select.setRotation(angle*MathUtils.radiansToDegrees);
		
		type.setPosition(x2, y2);
		type.setRotation(angle*MathUtils.radiansToDegrees);
		
		
		//Begin drawing
		sb.begin();
		
		
		if(isSelected)
			select.draw(sb);
		
		if(!isSelected)
			type.draw(sb);
		
        sprite.draw(sb);
		sb.end();
	}
}
