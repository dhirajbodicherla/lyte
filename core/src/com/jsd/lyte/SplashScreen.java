package com.jsd.lyte;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;



public class SplashScreen extends AbstractGameScreen {

	private Stage stage;
	private Image imgBackground;
	private boolean animationDone;

	public SplashScreen(LightPhysics game) {
		super(game);
		stage = new Stage();
		init();
	}
	
	public void init()
	{
		Assets.instance.init(new AssetManager());
		animationDone = false;
		buildStage();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(deltaTime);
		stage.draw();
		
		if(Assets.instance.update() && animationDone)
		{
			Assets.instance.setupAssets();
			game.setScreen(new MenuScreen(game));
		}
		
	}

		
	private void buildStage() {		
		Vector2 SCREEN = Assets.instance.queryScreen();
		
		Table layer = new Table();
		Texture texBackground = new Texture(Gdx.files.internal(Constants.IMG_SPLASH_SCREEN));
		imgBackground = new Image(texBackground);
		
		//Source: http://www.toxsickproductions.com/libgdx/libgdx-basics-game-and-screens/
		imgBackground.addAction(Actions.sequence(Actions.alpha(0)
                ,Actions.fadeIn(0.75f),Actions.delay(5.f),Actions.run(new Runnable() {
            @Override
            public void run() {
                animationDone = true;
            }
        })));
		
		layer.setBounds(0, 0, SCREEN.x, SCREEN.y);
		layer.add(imgBackground);
		
		stage.addActor(layer);
		
			
	}
	
	@Override
	public void resize(int width, int height) {
//		stage.setViewport(new StretchViewport(68, 40));
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		Assets.instance.load();
	}

	@Override
	public void hide() {
		stage.dispose();
	}

	@Override
	public void pause() {
	}

}