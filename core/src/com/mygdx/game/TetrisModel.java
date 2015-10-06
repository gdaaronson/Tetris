package com.mygdx.game;

public class TetrisModel {

	public static final int[][] OFFSETI = { { 0, -2 }, { 0, -1 }, { 0, 0 }, { 0, 1 } };

	public static final int[][] OFFSETS = { { 0, -2 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

	/**
	 * Contains a list of blocks and null spaces where 0,n is the top row, nth
	 * column
	 */
	private Block[][] board;

	private Brick currentBrick;

	private int height;

	/** The width of the board */
	private int width;

	/** Creates a blank model of specified height and width */
	public TetrisModel(int height, int width) {
		assert(width >= 5);
		assert(height >= 5);
		board = new Block[height][width];
		this.width = width;
		this.height = height;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = new Block(i, j);
			}
		}
	}

	/** Creates a model based on the string entered */
	public TetrisModel(int height, int width, String model) {
		// TODO the code above is simaler, can we do something about that
		assert(width >= 5);
		assert(height >= 5);
		board = new Block[height][width];
		this.width = width;
		this.height = height;

		int counter = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = new Block(i, j);
				if (model.charAt(counter) == 'o') {
					board[i][j].toggle();
				}
				counter++;
			}
		}
	}

	/** Returns the number of completed rows */
	public int checkForCompletion() {
		int completed = 0;
		for (int i = 0; i < height; i++) {
			if (checkForCompletion(i)) {
				completed++;
				nullRow(i);
			}
		}
		assert(completed <= 4);
		return completed;
	}

	/** Given a row, this method checks to see if it is completely filled out */
	public boolean checkForCompletion(int row) {
		int counter = 0;
		for (int i = 0; i < width; i++) {
			if (board[row][i].isOn()) {
				counter++;
			}
		}
		return counter == width;
	}

	/** Returns the current brick */
	public Brick getCurrentBrick() {
		return currentBrick;
	}

	/**
	 * Inserts a brick based on an index: 0 = I block 1 = J block 2 = L block 3
	 * = O block 4 = S block 5 = T block 6 = Z block
	 **/
	public void insertBrick(int index) {
		assert(index >= 0);
		assert(index <= 6);
		if (index == 0) {
			spawnI();
		} else if (index == 1) {
			spawnJ();
		} else if (index == 2) {
			spawnL();
		} else if (index == 3) {
			spawnO();
		} else if (index == 4) {
			spawnS();
		} else if (index == 5) {
			spawnT();
		} else if (index == 6) {
			spawnZ();
		}
	}

	/** Moves the brick down */
	public void moveDown() {
		if(emptyDown() ){
		toggleBrick();
		for (Block block : currentBrick.getBrick()) {
			currentBrick.swapBlock(block, board[block.getDepth() + 1][block.getWidth()]);
		}
		toggleBrick();
		}
	}

	private boolean emptyDown() {
		try{
		int clear = 0;
		for (Block block : currentBrick.getBrick()) {
			if(!board[block.getDepth() + 1][block.getWidth()].isOn()){
				clear++;
			}
		}
		//TODO take orentation into account
		if(currentBrick.getKey() == 'I'){
			return clear == 4;
		}else if(currentBrick.getKey() == 'O'){
			return clear == 2;
		}else{
			return clear == 3;
		}
		}catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
		
	}

	/**
	 * Moves the brick left, for some reason it must be down the oppsite way of
	 * move right
	 */
	public void moveLeft() {
		toggleBrick();
		for (int i = currentBrick.getBrick().length - 1; i >= 0; i--) {
			currentBrick.swapBlock(currentBrick.getBrick()[i],
					board[currentBrick.getBrick()[i].getDepth()][currentBrick.getBrick()[i].getWidth() - 1]);
		}
		toggleBrick();
	}

	/** Moves the brick right */
	public void moveRight() {
		toggleBrick();
		for (Block block : currentBrick.getBrick()) {
			// block.toggle();
			currentBrick.swapBlock(block, board[block.getDepth()][block.getWidth() + 1]);
			// block.toggle();
		}
		toggleBrick();
	}

	/** This sets the row back to null */
	private void nullRow(int row) {
		for (int i = 0; i < board[row].length; i++) {
			board[row][i].toggle();
		}
		sinkDown(row);
	}

	public void print() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printOn() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].isOn()) {
					System.out.print(board[i][j] + " ");
				} else {
					System.out.print("( , ) ");
				}
			}
			System.out.println();
		}
	}

	/** This method moves down every brick above the row */
	private void sinkDown(int row) {
		for (int i = row - 1; i >= 0; i--) {
			for (int j = 0; j < board[row].length; j++) {
				// TODO see if this is okay
				if (board[i][j].on) {
					board[i][j].toggle();
					board[i + 1][j].toggle();
				}
			}
		}
	}

	/** Spawns the I shaped block and sets the active block to it */
	public void spawnI() {
		// TODO uses off sets some how
		// Block[] blocks = new Block[4];
		// for(int[] off: OFFSETI ){
		// turnOn(off[0], off[1] + width/2);
		// }
		// for(Block b: blocks){
		//
		// }
		currentBrick = new Brick('I', toggleAndReturn(0, width / 2 + 1), toggleAndReturn(0, width / 2),
				toggleAndReturn(0, width / 2 - 1), toggleAndReturn(0, width / 2 - 2));
	}

	/** Spawns the J shaped block and sets the active block to it */
	public void spawnJ() {
		currentBrick = new Brick('J',toggleAndReturn(1, width / 2 + 1), toggleAndReturn(0, width / 2 + 1),
				toggleAndReturn(0, width / 2), toggleAndReturn(0, width / 2 - 1));
	}

	/** Spawns the L shaped block and sets the active block to it */
	public void spawnL() {
		currentBrick = new Brick('L',toggleAndReturn(0, width / 2 + 1), toggleAndReturn(0, width / 2),
				toggleAndReturn(1, width / 2 - 1), toggleAndReturn(0, width / 2 - 1));
	}

	/** Spawns the O shaped block and sets the active block to it */
	public void spawnO() {
		// TODO make +1 into -1
		currentBrick = new Brick('O',toggleAndReturn(1, width / 2 + 1), toggleAndReturn(1, width / 2),
				toggleAndReturn(0, width / 2 + 1), toggleAndReturn(0, width / 2));
	}

	/** Spawns the S shaped block and sets the active block to it */
	public void spawnS() {
		currentBrick = new Brick('S',toggleAndReturn(1, width / 2), toggleAndReturn(1, width / 2 - 1),
				toggleAndReturn(0, width / 2 + 1), toggleAndReturn(0, width / 2));
	}

	/** Spawns the T shaped block and sets the active block to it */
	public void spawnT() {
		currentBrick = new Brick('T',toggleAndReturn(1, width / 2), toggleAndReturn(0, width / 2 + 1),
				toggleAndReturn(0, width / 2), toggleAndReturn(0, width / 2 - 1));
	}

	/** Spawns the Z shaped block and sets the active block to it */
	public void spawnZ() {
		currentBrick = new Brick('Z',toggleAndReturn(1, width / 2 + 1), toggleAndReturn(1, width / 2),
				toggleAndReturn(0, width / 2), toggleAndReturn(0, width / 2 - 1));
	}

	/** Toggles block i,j */
	public void toggle(int i, int j) {
		board[i][j].toggle();
	}

	/** Toggles the whole block */
	private void toggleBrick() {
		for (Block b : currentBrick.getBrick()) {
			b.toggle();
		}
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != null && board[i][j].isOn()) {
					s += "o";
				} else {
					s += ".";
				}
			}
			s += "\n";
		}
		return s;
	}

	/** Turns on a block and returns the block */
	public Block toggleAndReturn(int i, int j) {
		board[i][j].toggle();
		return board[i][j];
	}

}
