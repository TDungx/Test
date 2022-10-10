package game;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import entities.Entity;
import entities.character.Bomber;
import entities.tiles.Grass;
import entities.tiles.Wall;
import entities.tiles.destroyable.Brick;
import entities.tiles.destroyable.Portal;
import graphics.Sprite;

public class LevelLoadFromFile extends LevelLoader {
	
	public static char[][] map;
	
	public LevelLoadFromFile(Board board, int level) {
		super(board, level);
		// TODO Auto-generated constructor stub
		loadLevel(1);
	}

	@Override
	public void loadLevel(int level) {
		// TODO Auto-generated method stub
		
		File mapFile = new File("res\\levels\\Level" + level + ".txt");
		
		try {
			InputStream inS = new FileInputStream(mapFile);
			InputStreamReader inSR = new InputStreamReader(inS);
			BufferedReader br = new BufferedReader(inSR);
			
			String s = br.readLine();
			System.out.println("s " +s);
			String arr[] = s.split(" ");
			this.level = Integer.parseInt(arr[0]);
			this.levelWidth = Integer.parseInt(arr[2]);
			this.levelHeight = Integer.parseInt(arr[1]);
			
			map = new char[levelHeight][levelWidth];
			
			for( int i = 0 ; i < levelHeight ; i++ ) {
				String line = br.readLine();
				for( int j = 0 ; j < levelWidth ; j++ ) {
					map[i][j] = line.charAt(j);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void createEntities() {
		// TODO Auto-generated method stub
		for( int i = 0 ; i < levelHeight ; i++ ) {
			
			for( int j = 0 ; j < levelWidth ; j++ ) {
				
				System.out.print(map[i][j] );
				int position = j + i * this.getLevelWidth();
				if( map[i][j] == '#') {
					Entity e = new Wall(j, i);
					board.addEntity(position, e);
				}
				else if( map[i][j] == '*' ) {
					Entity e = new Brick(j, i);
					board.addEntity(position, e);
				}
				else if( map[i][j] == ' ' ) {
					Entity e = new Grass(j, i);
					board.addEntity(position, e);
				}
				else if( map[i][j] == 'x' ) {
					Entity e = new Portal(j, i);
					board.addEntity(position, e);
				}
				else if( map[i][j] == 'p' ) {
					board.addCharacter(new Bomber(j * Sprite.DEFAULT_SIZE , i * Sprite.DEFAULT_SIZE ,this.board));
					Entity e = new Grass(j, i);
					board.addEntity(position, e);
				}
				else {
					Entity e = new Grass(j, i);
					board.addEntity(position, e);
				}
			}
			System.out.println();
		}
	}

}
