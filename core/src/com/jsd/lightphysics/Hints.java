package com.jsd.lightphysics;

import com.badlogic.gdx.Gdx;
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
		this.draw();
	}
	public void draw(){
		hintsLabel = new Label("", skin);
		hintsLabel.setWrap(true);
		
		hintsTable = new Table();
		hintsTable.setBounds(Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getHeight(), Gdx.graphics.getWidth()*0.7f, hintsTable.getHeight());
		hintsTable.add(hintsLabel).width(Gdx.graphics.getWidth()*0.70f);
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
