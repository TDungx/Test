package game;


public abstract class LevelLoader {
	protected int levelWidth = 15;
	protected int levelHeight = 13;
	public int level;
	Board board;
	
	public LevelLoader(int width, int height, Board board) {
		this.levelWidth = width;
		this.levelHeight = height;
		this.board = board;
	}
	
	public LevelLoader(Board board, int level) {
		this.board = board;
		this.level = level;
	}
	
	public abstract void loadLevel(int level);

	public abstract void createEntities();

	public int getLevel() {
		return level;
	}
	
	public int getLevelWidth() {
		return levelWidth;
	}

	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}

	public int getLevelHeight() {
		return levelHeight;
	}

	public void setLevelHeight(int levelHeight) {
		this.levelHeight = levelHeight;
	}
	
	
}
