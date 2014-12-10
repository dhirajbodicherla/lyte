package com.mygdx.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;



public class BlackHole extends Entity{
	
	Sprite sprite;
	Texture tex;
	Pixmap pix;
	
	public BlackHole(EntityDef ed, int index) {
		super(ed, index);
		init();
	}
	
	public void init(){
		int ir = (int)(this.getIr()) + 2;
		int r = ((int)((this.getRadius()))+2);
		pix = new Pixmap((ir+3)*2, (ir+3)*2,Pixmap.Format.RGBA8888);
		int x = (int)(pix.getWidth()*0.5);
		int y = (int)(pix.getHeight()*0.5);
		
		Color blue = new Color(0.15f,0.7f,1.f,1.f);
		Color yellow = new Color(0.99f,1.f,0.4f,1.f);
		
		pix.setColor(blue);
		//influence circle
		
		pix.drawCircle(x, y, (int)((ir-2)));
		pix.drawCircle(x, y, (int)((ir-1)));
		pix.drawCircle(x, y, (int)((ir+0)));
		pix.drawCircle(x, y, (int)((ir+1)));
		pix.drawCircle(x, y, (int)((ir+2)));
		
		
		pix.setColor(yellow);
		//actual body
		pix.drawCircle(x, y, (int)((r-2)));
		pix.drawCircle(x, y, (int)((r-1)));
		pix.drawCircle(x, y, (int)((r+0)));
		pix.drawCircle(x, y, (int)((r+1)));
		pix.drawCircle(x, y, (int)((r+2)));
		//black fill
		pix.setColor(Color.BLACK);
		pix.fillCircle(x, y, (int)(r));
		Pixmap blurred = BlurUtils.blur(pix, 2, 2, true);

		
		tex = new Texture(blurred);
		sprite = new Sprite(tex);
		blurred.dispose();
		
	}
	
	public void render(SpriteBatch sb)
	{
		super.update();
		float pixdim = this.getIr()+3;
		
		float x = this.pos.x - (pixdim);
		float y = this.pos.y - (pixdim);
		
		sb.begin();
		sprite.setPosition(x, y);
		sprite.setRotation(angle*MathUtils.radiansToDegrees);
        sprite.draw(sb);
		sb.end();
	}
	
	public void dispose()
	{
		tex.dispose();
	}
}
