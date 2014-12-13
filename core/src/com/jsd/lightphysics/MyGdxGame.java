package com.jsd.lightphysics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;




/*
 * Author: Jay Ravi
 * 
 * Box2D World Units: meters
 * OpenGL World Units: pixels 
 * 1 Meter = 100 pixels
 * 1 pixel = 0.01m
 * 
 * 
 * Coordinate System:
 * (0,0) - Bottom left corner
 * (width, height) - top right corner
 * 
 * 
 * For rendering 2d graphics convert to pixel. 
 * Box2D bodies should be positioned and dimensioned in meters
 * 
 * 
 * WARNING!!!
 * Foregoing this procedure can cause unexpected results.  
 */

public class MyGdxGame extends InputAdapter implements InputProcessor{
	
	public MyGdxGame() {
		// TODO Auto-generated constructor stub
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
	}
	
	
	public boolean keyUp(int keycode) {
//		if(keycode == Input.Keys.D)
//			m_level.destroy();
//		if(keycode == Input.Keys.RIGHT)
//		{
//			m_level.nextLevel();
//		}
//		if(keycode == Input.Keys.LEFT)
//		{
//			m_level.prevLevel();
//		}
		Gdx.app.debug("keys", "clicked");
		Gdx.app.debug("Keys", String.valueOf(keycode) + String.valueOf(Keys.BACK) + String.valueOf(Keys.ESCAPE));
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			backToMenu();
		} 
		return false;
	}
	private void backToMenu() {
		Gdx.app.debug("keys", "back");
		// switch to menu screen
//		this.game.setScreen(new MenuScreen(this.game));
		
	}

   

}
