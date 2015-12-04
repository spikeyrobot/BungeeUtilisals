package com.dbsoftware.bungeeutilisals.Managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.FileConfiguration;
import net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.YamlConfiguration;

public class FileManager {
	
	private FileConfiguration file;
	private String key;
	
	
	public FileManager(String path) 
	{
		this.key = System.getProperty("user.dir") + path;
		createFile();
		file = YamlConfiguration.loadConfiguration(new File(System.getProperty("user.dir") + path));
	}
	
	public void load() {
		createFile();
		try {file.load(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void save() {
		createFile();
		try {file.save(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void createFile()
	{
		File file = new File(key);
		if(!file.exists())
		{
			file.getParentFile().mkdirs();
			try 
			{
				file.createNewFile();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	public String getString(String path, String def) {
		
		if(file.contains(path)) {
			return file.getString(path);
		} else {		
			file.set(path, def);
			try {
				file.save(key);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return def;
		}
	}
	
	public int getInt(String path, int def) {
		
		if(file.contains(path)) {
			return file.getInt(path);
		}
		else {
			file.set(path, def);
			try {
				file.save(key);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return def;
		}
			
	}
	
	public boolean getBoolean(String path, boolean def) {
		
		if(file.contains(path)) {
			return file.getBoolean(path);
		} else {
			file.set(path, def);
			try {
				file.save(key);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return def;
		}
			
	}
	
	public List<String> getStringList(String path, List<String> def) {
		
		if(file.contains(path)) {
			return file.getStringList(path);
		} else {
			file.set(path, def);
			try {
				file.save(key);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return def;
		}
			
	}
	
	public List<Integer> getIntegerList(String path, List<Integer> def) {
		
		if(file.contains(path)) {
			return file.getIntegerList(path);
		} else {
			file.set(path, def);
			try {
				file.save(key);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return def;
		}
			
	}
	
	public List<Double> getDoubleList(String path, List<Double> def) {
		
		if(file.contains(path)) {
			return file.getDoubleList(path);
		} else {
			file.set(path, def);
			try {
				file.save(key);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return def;
		}
			
	}
	
	public List<Long> getLongList(String path, List<Long> def) {
		
		if(file.contains(path)) {
			return file.getLongList(path);
		} else {
			file.set(path, def);
			try {
				file.save(key);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return def;
		}
			
	}
	
	public List<Float> getFloatList(String path, List<Float> def) {
		
		if(file.contains(path)) {
			return file.getFloatList(path);
		} else {
			file.set(path, def);
			try {
				file.save(key);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return def;
		}
			
	}
	
	public double getDouble(String path, double def){
		if(file.contains(path)) {
			return file.getDouble(path);
		}
		else {
			file.set(path, def);
			try {
				file.save(key);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return def;
		}
	}
	
	public void setString(String path, String def) {
		file.set(path, def);
		try {
			file.save(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setInt(String path, int def) {
		file.set(path, def);
		try {
			file.save(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setBoolean(String path, boolean def) {
		file.set(path, def);
		try {
			file.save(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setStringList(String path, List<String> def) {
		file.set(path, def);
		try {
			file.save(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getConfigurationSection(String section)
	{
		if(file.getConfigurationSection(section) == null){
			file.createSection(section);
			try {
				file.save(key);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		List<String> ret = new ArrayList<String>();
		try
		{
			for(Object o : file.getConfigurationSection(section).getKeys(false)){
				ret.add((String) o);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
}