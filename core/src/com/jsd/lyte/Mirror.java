package com.jsd.lyte;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jsd.lyte.Entity;

public class Mirror extends Entity{
	private TextureRegion regMirror;
	
	Texture tex;
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
		//regMirror = Assets.instance.mirror.mirror;
		//origin.set(size.x, size.y);
		Pixmap pix = new Pixmap((int)(this.size.x), 
				         (int)(this.size.y), 
				         Pixmap.Format.RGBA8888);
		
		
		Pixmap pSelection= new Pixmap((int)(this.size.x+10), 
		         (int)(this.size.y+10), 
		         Pixmap.Format.RGBA8888);
		
		Pixmap pType = new Pixmap((int)(this.size.x+10), 
		         (int)(this.size.y+10), 
		         Pixmap.Format.RGBA8888);
		
		int x = (int)(pix.getWidth());
		int y = (int)(pix.getHeight());
		
		int x2 = (int)(pSelection.getWidth());
		int y2 = (int)(pSelection.getHeight());
				
		pix.setColor(Color.CYAN);
		pix.drawRectangle(0, 0, x, y);
		
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
		
		tex = new Texture(pix);
		selection = new Texture(blurSel);
		type_tex = new Texture(blurType);
		
		sprite = new Sprite(tex);
		select = new Sprite(selection);
		type = new Sprite(type_tex);
		isSelected = false;
		pix.dispose();
		blurSel.dispose();
		blurType.dispose();
		
	}
	
	public void render(SpriteBatch sb){
		super.update();
		TextureRegion reg = null;
		reg = regMirror;
		super.update();
		
		float w = this.size.x;
		float h = this.size.y;
		
		
		float x = this.pos.x - (w*0.5f);
		float y = this.pos.y - (h*0.5f);
		
		float x2 = this.pos.x - ((w+10)*0.5f);
		float y2 = this.pos.y - ((h+10)*0.5f);
		
		
		/*batch.draw(reg.getTexture(),
					pos.x - origin.x, pos.y - origin.y, 
					origin.x, origin.y, 
					size.x*2, size.y*2, 
					scale.x, scale.y, 
					angle,
					reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);*/
		sb.begin();
		sprite.setPosition(x, y);
		sprite.setRotation(angle*MathUtils.radiansToDegrees);
		
		select.setPosition(x2, y2);
		select.setRotation(angle*MathUtils.radiansToDegrees);
		
		type.setPosition(x2, y2);
		type.setRotation(angle*MathUtils.radiansToDegrees);
		
		if(isSelected)
			select.draw(sb);
		
		if(!isSelected)
			type.draw(sb);
		
        sprite.draw(sb);
		sb.end();
	}
}
