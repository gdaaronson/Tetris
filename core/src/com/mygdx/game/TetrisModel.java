package com.mygdx.game;

public class TetrisModel {

	/**
	 * Contains a list of blocks and null spaces where 0,n is the top row, nth
	 * column
	 */
	private Block[][] board;

	private Brick currentBrick;

	private int height;
	
	public static final int[][] OFFSETS = {
			{0, -2},{0, -1}, {0, 0}, {0, 1},
					{1, -1}, {1, 0}, {1, 1}
	};

	public static final int[][] OFFSETI = {
			{0, -2},{0, -1}, {0, 0}, {0, 1}
	};

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
	public void moveDown(Brick brick) {
		toggleBlock();
		for (Block block : brick.getBrick()) {
			brick.swapBlock(block, board[block.getDepth() + 1][block.getWidth()]);
		}
		toggleBlock();
	}
	
	/** Moves the brick left */
	public void moveLeft(Brick brick) {
		//TODO figure out how to switch which blocks are in the current brick
		toggleBlock();
		for (Block block : brick.getBrick()) {
			brick.swapBlock(block, board[block.getDepth()][block.getWidth() - 1]);
		}
		toggleBlock();
	}
	
	public void moveLeftDuckTape(Brick brick) {
		toggleBlock();
		for (int i = brick.getBrick().length - 1; i >=0; i--) {
			brick.swapBlock(brick.getBrick()[i], board[brick.getBrick()[i].getDepth()][brick.getBrick()[i].getWidth() - 1]);
		}
		toggleBlock();
	}
	
	/** Moves the brick right */
	public void moveRight(Brick brick) {
		toggleBlock();
		for (Block block : brick.getBrick()) {
//			block.toggle();
			brick.swapBlock(block, board[block.getDepth()][block.getWidth() + 1]);
//			block.toggle();
		}
		toggleBlock();
	}	

	/** This sets the row back to null */
	private void nullRow(int row) {
		for (int i = 0; i < board[row].length; i++) {
			board[row][i] = null;
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
			if (board[i] != null) {
				Block[] temp = board[i];
				board[i] = board[i + 1];
				board[i + 1] = temp;
			}
		}
	}

	/** Spawns the I shaped block and sets the active block to it */
	public void spawnI() {
//		Block[] blocks = new Block[4];
//		for(int[] off: OFFSETI ){
//			turnOn(off[0], off[1] + width/2);
//		}
//		for(Block b: blocks){
//			
//		}
		currentBrick = new Brick(turnOnAndReturn(0, width / 2 + 1), turnOnAndReturn(0, width / 2),
				turnOnAndReturn(0, width / 2 - 1), turnOnAndReturn(0, width / 2 - 2));
	}

	/** Spawns the J shaped block and sets the active block to it */
	public void spawnJ() {
		currentBrick = new Brick(turnOnAndReturn(1, width / 2 + 1), turnOnAndReturn(0, width / 2 + 1),
				turnOnAndReturn(0, width / 2 - 1), turnOnAndReturn(0, width / 2));
	}

	/** Spawns the L shaped block and sets the active block to it */
	public void spawnL() {
		currentBrick = new Brick(turnOnAndReturn(0, width / 2), turnOnAndReturn(1, width / 2 - 1),
				turnOnAndReturn(0, width / 2 + 1), turnOnAndReturn(0, width / 2 - 1));
	}

	/** Spawns the O shaped block and sets the active block to it */
	public void spawnO() {
		currentBrick = new Brick(turnOnAndReturn(1, width / 2), turnOnAndReturn(1, width / 2 + 1),
				turnOnAndReturn(0, width / 2), turnOnAndReturn(0, width / 2 + 1));
	}

	/** Spawns the S shaped block and sets the active block to it */
	public void spawnS() {
		currentBrick = new Brick(turnOnAndReturn(1, width / 2), turnOnAndReturn(1, width / 2 - 1),
				turnOnAndReturn(0, width / 2 + 1), turnOnAndReturn(0, width / 2));
	}

	/** Spawns the T shaped block and sets the active block to it */
	public void spawnT() {
		currentBrick = new Brick(turnOnAndReturn(1, width / 2), turnOnAndReturn(0, width / 2 - 1),
				turnOnAndReturn(0, width / 2 + 1), turnOnAndReturn(0, width / 2));
	}

	/** Spawns the Z shaped block and sets the active block to it */
	public void spawnZ() {
		currentBrick = new Brick(turnOnAndReturn(1, width / 2), turnOnAndReturn(0, width / 2 - 1),
				turnOnAndReturn(1, width / 2 + 1), turnOnAndReturn(0, width / 2));
	}

	/** Toggles block i,j on or off */
	private void toggle(int i, int j) {
		board[i][j].toggle();
	}
	
	/** Toggles the whole block*/
	private void toggleBlock(){
		for(Block b: currentBrick.getBrick()){
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

	/** Turns on a block */
	public void turnOn(int i, int j) {
		board[i][j].toggle();
	}

	/** Turns on a block and returns the block */
	public Block turnOnAndReturn(int i, int j) {
		board[i][j].toggle();
		return board[i][j];
	}

}
