package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

public class Level {

	public static final String TAG = Level.class.getName();

	public String name;
	private ArrayList<EntityDef> entities;
	private ArrayList<Body> planets;
	private ArrayList<Asteroid> asteroids;
	public Laser laser;

	public Level(String filename, World w) {
		init(filename, w);
	}

	private void init(String filename, World world) {

		String locRoot = Gdx.files.getLocalStoragePath();
		FileHandle handle = Gdx.files.internal(filename);
		String fileContent = handle.readString();
		Json json = new Json();
		LevelStructure lvl = json.fromJson(LevelStructure.class, fileContent);

		asteroids = new ArrayList<Asteroid>();
		planets = new ArrayList<Body>();
		laser = new Laser();

		BodyDef bodyDef;
		Shape bodyShape = null;
		FixtureDef fixtureDef = null;
		Body phyBody;

		for (int i = 0; i < lvl.entities.length; i++) {
			EntityDef ed = lvl.entities[i];
			float x = ed.x;
			float y = ed.y;
			float w = ed.w;
			float h = ed.h;

			bodyDef = new BodyDef();
			if (ed.type.equalsIgnoreCase("S")) {
				bodyDef.type = BodyType.StaticBody;
			}
			if (ed.type.equals("D")) {
				bodyDef.type = BodyType.DynamicBody;
			}

			if (ed.type.equals("K")) {
				bodyDef.type = BodyType.KinematicBody;
			}

			// bodyDef.position.set(x * Constants.WORLD_TO_BOX, y *
			// Constants.WORLD_TO_BOX);

			if (ed.shape.equalsIgnoreCase("rect")) {
				bodyShape = new PolygonShape();
				((PolygonShape) bodyShape).setAsBox((w * 0.5f)
						* Constants.WORLD_TO_BOX, h * 0.5f
						* Constants.WORLD_TO_BOX);
			}
			if (ed.shape.equalsIgnoreCase("circle")) {
				bodyShape = new CircleShape();
				bodyShape.setRadius(ed.r * Constants.WORLD_TO_BOX);
			}

			fixtureDef = new FixtureDef();
			fixtureDef.shape = bodyShape;
			fixtureDef.density = 0.4f;
			fixtureDef.friction = 0.2f;
			fixtureDef.restitution = 0.8f;

			phyBody = world.createBody(bodyDef);
			phyBody.createFixture(fixtureDef);

			if (ed.type.equals("K")) {
				// kBody = phyBody;
				Laser l = new Laser();
				l.b = phyBody;
				l.pos = new Vector2(x * Constants.WORLD_TO_BOX, y
						* Constants.WORLD_TO_BOX);
			} else {
				Asteroid a = new Asteroid();
				a.b = phyBody;
				a.pos = new Vector2(x * Constants.WORLD_TO_BOX, y
						* Constants.WORLD_TO_BOX);
				asteroids.add(a);
			}
		}
	}

	public void render(SpriteBatch batch) {
		for (Asteroid a : asteroids) {
			a.render(batch);
		}
		laser.render(batch);
		// planets
	}

	public void update(float deltaTime) {
//		bunnyHead.update(deltaTime);
		for (Asteroid a : asteroids) {
			a.update(deltaTime);
		}
		laser.update(deltaTime);
	}
}
