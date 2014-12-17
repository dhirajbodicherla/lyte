package com.jsd.lightphysics;

import com.badlogic.gdx.Screen;

public abstract class AbstractGameScreen implements Screen {
	protected LightPhysics game;

	public AbstractGameScreen(LightPhysics game) {
		this.game = game;
	}

	public abstract void render(float deltaTime);

	public abstract void resize(int width, int height);

	public abstract void show();

	public abstract void hide();

	public abstract void pause();

	public void resume() {
	}

	public void dispose() {
	}
}