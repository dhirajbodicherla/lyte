package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

//public final class LevelParser {
//	
//	private static String mfilename;
//	public static LevelDef LoadFile(String filename)
//	{
//		String locRoot = Gdx.files.getLocalStoragePath(); 
//		FileHandle handle = Gdx.files.internal(locRoot+filename);
//		String fileContent = handle.readString();
//		Json json = new Json();
//		LevelDef lvl = json.fromJson(LevelDef.class, fileContent);
//		return lvl;
//	}
//	
//	public static String getRoot()
//	{
//		return mfilename;
//	}
//}
public class LevelStructure{
	public String name;
	public EntityDef[] entities;
	public String mName;
	public float mBaseWidth; 
	public float mBaseHeight;
	public EntityDef mTarget;
	public EntityDef mSource;
	public ArrayList<EntityDef> mMirrors; 
	public ArrayList<EntityDef> mAsteroids;
	public ArrayList<EntityDef> mBlackholes;
}