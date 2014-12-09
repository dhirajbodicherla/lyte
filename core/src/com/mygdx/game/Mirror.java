package com.mygdx.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Entity;

public class Mirror extends Entity{
	private TextureRegion regMirror;
	Pixmap pix;
	Texture tex;
	Texture selection; 
	private Sprite sprite;
	
	private boolean isSelected;
	
	
	public Mirror(EntityDef ed, int index){
		super(ed, index);
		init();
	}
	
	private void init(){
		//regMirror = Assets.instance.mirror.mirror;
		origin.set(size.x, size.y);
		
		pix = new Pixmap((int)(this.size.x), 
				         (int)(this.size.y), 
				         Pixmap.Format.RGBA8888);
		int x = (int)(pix.getWidth());
		int y = (int)(pix.getHeight());
		
		pix.setColor(Color.CYAN);
		pix.drawRectangle(0, 0, x, y);
		tex = new Texture(pix);
		sprite = new Sprite(tex);
		isSelected = false;
		pix.dispose();
		
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
        sprite.draw(sb);
		sb.end();
	}
}
