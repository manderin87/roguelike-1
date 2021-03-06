package edu.brown.cs.roguelike.engine.save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.brown.cs.roguelike.engine.game.Game;
import edu.brown.cs.roguelike.engine.level.Level;

/**
 * Manages the saving and loading of levels
 * 
 * @author lelberty
 *
 */
public class SaveManager {
	
	private static final String fileExtension = ".rog";
	
	private String saveFile;

	public SaveManager(String saveFile) {
		if (saveFile.endsWith(fileExtension))
			this.saveFile = saveFile;
		else this.saveFile = saveFile + fileExtension;
	}

	/**
	 * Saves the current level, obliterating anything that
	 * was already there
	 * 
	 * @throws SaveLoadException
	 */
	public void saveLevel(Level l) throws SaveLoadException {
		try {
			FileOutputStream fos = new FileOutputStream(this.saveFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(l);
			fos.close();
			oos.close();
		} catch (Exception e) {
			throw new SaveLoadException(e);
		}
	}
	
	public void deleteSave() {
		File f = new File(saveFile);
		if(f.exists()) 
			f.delete();
		
	}

	/**
	 * 
	 * Attempts to load a savegame from saveFile
	 *  
	 * @throws SaveLoadException if there was no save file
	 */
	public Level loadLevel() throws SaveLoadException {
		
		Level l = null;

		try {
			FileInputStream fis = new FileInputStream(this.saveFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
		
			Object o = ois.readObject();
			ois.close();
			
			if (o instanceof Level) {
				l = (Level)o;
				
			} else {
				throw new SaveLoadException(
						"Save file was not a Level object. Found: " 
								+ o + " instead");
			}
			
		} catch (Exception e) {
			throw new SaveLoadException(e);
		}
		
		return l;
	}
	
	/**
	 * Saves the game, obliterating anything that
	 * was already there
	 * 
	 * @throws SaveLoadException
	 */
	public void saveGame(Game g) throws SaveLoadException {
		try {
			FileOutputStream fos = new FileOutputStream(this.saveFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(g);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SaveLoadException(e);
		}
	}

	/**
	 * 
	 * Attempts to load a savegame from saveFile
	 *  
	 * @throws SaveLoadException if there was no save file
	 */
	public Game loadGame() throws SaveLoadException {
		
		Game g = null;

		try {
			FileInputStream fis = new FileInputStream(this.saveFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
		
			Object o = ois.readObject();
			ois.close();
			
			if (o instanceof Game) {
				g = (Game)o;
			} else {
				throw new SaveLoadException(
						"Save file was not a Game object. Found: " 
								+ o + " instead");
			}
			
		} catch (Exception e) {
			throw new SaveLoadException(e);
		}
		
		return g;
	}

}
