package com.mygdx.game;

import java.util.ArrayList;

public class LevelDef {
	public String mName;
	public float mBaseWidth; 
	public float mBaseHeight;
	public EntityDef mTarget;
	public EntityDef mSource;
	public ArrayList<EntityDef> mMirrors; 
	public ArrayList<EntityDef> mAsteroids;
	public ArrayList<EntityDef> mBlackholes;
}
