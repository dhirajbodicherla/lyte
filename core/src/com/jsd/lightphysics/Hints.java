package com.jsd.lightphysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Hints {
	
	private Skin skin;
	private Stage stage;
	private int level;
	private Table hintsTable;
	private Label hintsLabel;
	
	public Hints(Stage stage){
		this.stage = stage;
		this.level = level;
		
		skin = new Skin(Gdx.files.internal("data/ui/uimenuskin.json"));
		Vector2 vp = Assets.instance.queryViewport();
		float ratio = vp.x / vp.y;
		skin.getFont("default").scale(1.5f);
//		skin.getFont("default").setScale(1, ratio);
		Gdx.app.debug("Ratio", String.valueOf(ratio));
		skin.getFont("default").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		this.draw();
	}
	public void draw(){
		
		hintsLabel = new Label("", skin);
		hintsLabel.setWrap(true);
		hintsLabel.setFillParent(true);
//		hintsLabel.setFontScale(1.0f, 1.0f);
//		hintsLabel.setSize(Gdx.graphics.getWidth()*0.70f, Gdx.graphics.getHeight()*0.09f);
		
		hintsTable = new Table();
		hintsTable.setBounds(Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getHeight(), Gdx.graphics.getWidth()*0.7f, hintsLabel.getHeight());
		hintsTable.add(hintsLabel).width(Gdx.graphics.getWidth()*0.70f).height(Gdx.graphics.getWidth()*0.09f);
		hintsTable.center();
		hintsTable.top();
		
		this.stage.addActor(hintsTable);
	}
	
	public void showHint(String text){
		hintsLabel.setText(text);
		hintsTable.setVisible(true);
	}
	
	public void hideHint(){
		hintsLabel.setText("");
		hintsTable.setVisible(false);
	}
}
