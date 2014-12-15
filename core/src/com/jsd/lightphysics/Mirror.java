package com.jsd.lightphysics;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jsd.lightphysics.Entity;

public class Mirror extends Entity{
	
	Texture tex_rotSelection;
	Texture tex_moveSelection;
	Texture type_tex;
	private Sprite sprite;
	private Sprite type;	//if rotatable - set to green, if movable set to blue, if both set to white
	private Sprite rotSelect;
	private Sprite moveSelect;
	
	public boolean isRotSelected;
	public boolean isMoveSelected;
	
	
	public Mirror(EntityDef ed,Vector2 bs, int index){
		super(ed,bs, index);
		init();
	}
	
	private void init(){

		int w = (int) (this.size.x+20);
		int h = (int) (this.size.y+20);
		Pixmap rotSelection= new Pixmap(w,h, Pixmap.Format.RGBA8888);
		Pixmap moveSelection= new Pixmap(w,h, Pixmap.Format.RGBA8888);
		Pixmap pType = new Pixmap(w,h, Pixmap.Format.RGBA8888);
		
		int x2 = (int)(rotSelection.getWidth());
		int y2 = (int)(moveSelection.getHeight());
				
		Color blue = new Color(0f,0.6f,1.f,1.f);
		Color green = new Color(0f,1.f,0.6f,1.f);
		
		rotSelection.setColor(Color.YELLOW);
		rotSelection.drawRectangle(0, 0, x2-3, y2-3);
		rotSelection.drawRectangle(0, 0, x2-2, y2-2);
		rotSelection.drawRectangle(0, 0, x2-1, y2-1);
		
		moveSelection.setColor(Color.MAGENTA);
		moveSelection.drawRectangle(0, 0, x2-3, y2-3);
		moveSelection.drawRectangle(0, 0, x2-2, y2-2);
		moveSelection.drawRectangle(0, 0, x2-1, y2-1);
		
		if(fixedPosition!=0)
		{
			pType.setColor(blue);
		}
		if(fixedRotation!=0)
		{
			pType.setColor(green);
		}
		
		if(fixedPosition!=0 && fixedRotation!=0)
		{
			pType.setColor(Color.WHITE);
		}
		
		
		pType.drawRectangle(0, 0, x2-3, y2-3);
		pType.drawRectangle(0, 0, x2-2, y2-2);
		pType.drawRectangle(0, 0, x2-1, y2-1);
		
		
		
		Pixmap blurRotSel = BlurUtils.blur(rotSelection, 1, 2, true);
		Pixmap blurMoveSel = BlurUtils.blur(moveSelection, 1, 2, true);
		Pixmap blurType = BlurUtils.blur(pType, 1, 1, true);
		
		
		tex_rotSelection = new Texture(blurRotSel);
		tex_moveSelection = new Texture(blurMoveSel);
		type_tex = new Texture(blurType);
		
		sprite = (Assets.instance.getSpriteAtlas()).createSprite(Constants.SPRITE_MIRROR);
		
		float sx = AssetFactory.setToScale(sprite.getWidth(), this.size.x);
		float sy = AssetFactory.setToScale(sprite.getHeight(), this.size.y);
		
		
		sprite.setScale(sx, sy);
		rotSelect = new Sprite(tex_rotSelection);
		moveSelect = new Sprite(tex_moveSelection);
		type = new Sprite(type_tex);
		
		isRotSelected = false;
		isMoveSelected = false;
		
		blurRotSel.dispose();
		blurMoveSel.dispose();
		blurType.dispose();
		
	}
	
	
	
	public void render(SpriteBatch sb){
		super.update();
		
		float w = this.size.x+20;
		float h = this.size.y+20;
		
		float x = this.pos.x;
		float y = this.pos.y;
		
		float x2 = this.pos.x - (w*0.5f);
		float y2 = this.pos.y - (h*0.5f);
		
		sprite.setPosition(x, y);
		sprite.setCenter(x, y);
		sprite.setRotation(angle*MathUtils.radiansToDegrees);
		
		
		rotSelect.setPosition(x2, y2);
		rotSelect.setRotation(angle*MathUtils.radiansToDegrees);
		
		moveSelect.setPosition(x2, y2);
		moveSelect.setRotation(angle*MathUtils.radiansToDegrees);
		
		type.setPosition(x2, y2);
		type.setRotation(angle*MathUtils.radiansToDegrees);
		
		
		//Begin drawing
		sb.begin();
		
		
		if(isRotSelected)
			rotSelect.draw(sb);
		
		if(isMoveSelected)
			moveSelect.draw(sb);
		
		if(!(isRotSelected||isMoveSelected))
			type.draw(sb);
		
        sprite.draw(sb);
		sb.end();
	}
}
