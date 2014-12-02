package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;

public class WorldRenderer {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;
	private OrthographicCamera cameraGUI;
	private BitmapFont font;
	private int score = 0;
	private Box2DDebugRenderer b2debugRenderer;
	private static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
	private Viewport viewport;

	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
				Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
//		viewport = new FitViewport(800, 480, camera);

		/*
		 * GUI Stuff
		 */
		font = new BitmapFont();
		font.setColor(Color.RED);
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH,
				Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip y-axis
		cameraGUI.update();
		b2debugRenderer = new Box2DDebugRenderer();
		this.worldController.setCamera(camera);
	}

	public void render() {
		renderWorld(batch);
		renderGUI(batch);
	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();

		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height)
				* (float) width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2,
				cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
//		viewport.update(width, height);
	}

	// @Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	private void renderGUI(SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		renderGuiScore(batch);
		renderGuiExtraLive(batch);
		batch.end();
	}

	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
		if (DEBUG_DRAW_BOX2D_WORLD) {
			b2debugRenderer.render(worldController.world, camera.combined);
		}
	}

	private void renderGuiScore(SpriteBatch batch) {
		float x = -15;
		float y = -15;
		// font.draw(batch, score + "", x, y);
		batch.draw(Assets.instance.mirror.mirror, x, y, 50, 50, 100, 100,
				0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig
				.draw(batch, "" + score, x + 75, y + 37);
	}

	private void renderGuiExtraLive(SpriteBatch batch) {
		int lives = 2;
		float x = cameraGUI.viewportWidth - 200; // Constants.LIVES_START * 50;
		float y = -15;
		for (int i = 0; i < Constants.LIVES_START; i++) {
			if (lives <= i)
				batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			batch.draw(Assets.instance.laser.laser, x + i * 50, y, 50, 50, 120,
					100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
		}
	}
}