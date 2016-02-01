package com.mygdx.game;

public class TetrisModel {

	/**
	 * Contains a list of blocks and null spaces where 0,n is the top row, nth
	 * column
	 */
	private Block[][] board;

	private Brick currentBrick;

	/**
	 * Keeps the requirements based on which letter and which orientation I, J,
	 * L, O, S, T, Z UR or FF, CW or CCW
	 */
	private int[][] extractor = { { 4, 3, 3, 2, 3, 3, 3 }, { 1, 2, 2, 2, 2, 2, 2 } };

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

	public boolean canMoveDown() {
		return isItEmpty('d');
	}

	public boolean canMoveLeft() {
		return isItEmpty('l');
	}

	public boolean canMoveRight() {
		return isItEmpty('r');
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
			if (board[row][i].isOn() && stopBrick()) {
				counter++;
			}
		}
		return counter == width;
	}

	/** Checks to see if the given block is part of the brick */
	public boolean partOfBrick(Block block) {
		if (currentBrick != null) {
			for (Block b : currentBrick.getBrick()) {
				if (b.equals(block)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Sees if the game is over by making sure that at least one of the blocks
	 * are in the current brick
	 */
	public boolean endGameDetection() {
		int counter = 0;
		for (Block b : currentBrick.getBrick()) {
			if (b.isOn()) {
				counter++;
			}
		}
		boolean topRowOccupied = false;
		for (int i = 0; i < board[0].length; i++) {
			if (board[0][i].isOn() && partOfBrick(board[0][i])) {
				topRowOccupied = true;
			}
		}
		return counter < 4 && topRowOccupied;
	}

	public Block[][] getBoard() {
		return board;
	}

	/** Returns the current brick */
	public Brick getCurrentBrick() {
		return currentBrick;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int indexedDepth(int i) {
		return currentBrick.getBrick()[i].getDepth();
	}

	public int indexedWidth(int i) {
		return currentBrick.getBrick()[i].getWidth();
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

	/** Picks a random block to insert */
	public void insertRandomBrick() {
		insertBrick((int) (7 * Math.random()));
		currentBrick.sort();
	}

	/** Checks to see if there is in a particular direction */
	public boolean isItEmpty(char direction) {
		if (direction == 'd') {
			return isItEmpty(1, 0);
		} else if (direction == 'l') {
			return isItEmpty(0, -1);
		} else if (direction == 'r') {
			return isItEmpty(0, 1);
		} else {
			return false;
		}
	}

	/** Returns true if a brick can be moved in that direction */
	public boolean isItEmpty(int depthOffset, int widthOffset) {
		try {
			int clear = 0;
			for (Block block : currentBrick.getBrick()) {
				if (!board[block.getDepth() + depthOffset][block.getWidth() + widthOffset].isOn()) {
					clear++;
				}
			}
			// is it up right and moving down or facing side to side and moving
			// sideways
			if ((depthOffset == 1 && (currentBrick.getOrentation() == Orentation.UPRIGHT
					|| currentBrick.getOrentation() == Orentation.FLIPFLOP))
					|| (depthOffset == 0 && (currentBrick.getOrentation() == Orentation.CLOCKWISE
							|| currentBrick.getOrentation() == Orentation.COUNTERCLOCKWISE))) {
				if (currentBrick.getKey() == 'I') {
					return clear == extractor[0][0];
				} else if (currentBrick.getKey() == 'O') {
					return clear == extractor[0][3];
				} else {
					return clear == extractor[0][1];
				}
			} else {
				if (currentBrick.getKey() == 'I') {
					return clear == extractor[1][0];
				} else if (currentBrick.getKey() == 'O') {
					return clear == extractor[1][3];
				} else {
					return clear == extractor[1][1];
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}

	/** Moves the brick down */
	public void moveDown() {
		currentBrick.sort();
		if (isItEmpty('d')) {
			for (Block block : currentBrick.getBrick()) {
				currentBrick.swapBlock(block, board[block.getDepth() + 1][block.getWidth()]);
			}
		}
	}

	/**
	 * Moves the brick left, for some reason it must be down the oppsite way of
	 * move right
	 */
	public void moveLeft() {
		currentBrick.sort();
		if (isItEmpty('l')) {
			for (int i = currentBrick.getBrick().length - 1; i >= 0; i--) {
				currentBrick.swapBlock(currentBrick.getBrick()[i],
						board[currentBrick.getBrick()[i].getDepth()][currentBrick.getBrick()[i].getWidth() - 1]);
			}
		}
	}

	/** Moves the brick right */
	public void moveRight() {
		currentBrick.sort();
		if (isItEmpty('r')) {
			for (Block block : currentBrick.getBrick()) {
				currentBrick.swapBlock(block, board[block.getDepth()][block.getWidth() + 1]);
			}
		}
	}

	/** This sets the row back to null */
	public void nullRow(int row) {
		for (int i = 0; i < board[row].length; i++) {
			board[row][i].toggle();
		}
		sinkDown(row);
	}

	public void print() {
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

	/** Rotate clockwise */
	public void rotate() {
		try {
			currentBrick.sort();
			if (notOnEdge()) {
				if (currentBrick.getKey() == 'I') {
					rotateI();
				} else if (currentBrick.getKey() == 'J') {
					rotateJ();
				} else if (currentBrick.getKey() == 'L') {
					rotateL();
				} else if (currentBrick.getKey() == 'O') {

				} else if (currentBrick.getKey() == 'S') {
					rotateS();
				} else if (currentBrick.getKey() == 'T') {
					rotateT();
				} else if (currentBrick.getKey() == 'Z') {
					rotateZ();
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean notOnEdge() {
		for (Block b : currentBrick.getBrick()) {
			if (b.getWidth() == width - 1 || b.getWidth() == 0) {
				return false;
			}
		}
		return true;
	}

	public void rotateI() {
		if (currentBrick.getOrentation() == Orentation.UPRIGHT) {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(3) + 1][indexedWidth(3)]);
			currentBrick.swapBlock(currentBrick.getBrick()[1], board[indexedDepth(3) + 2][indexedWidth(3)]);
			currentBrick.swapBlock(currentBrick.getBrick()[2], board[indexedDepth(3) + 3][indexedWidth(3)]);
			currentBrick.setOrentation(Orentation.CLOCKWISE);
		} else {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(3)][indexedWidth(3) + 3]);
			currentBrick.swapBlock(currentBrick.getBrick()[1], board[indexedDepth(3)][indexedWidth(3) + 2]);
			currentBrick.swapBlock(currentBrick.getBrick()[2], board[indexedDepth(3)][indexedWidth(3) + 1]);
			currentBrick.setOrentation(Orentation.UPRIGHT);
		}

	}

	public void rotateJ() {
		if (currentBrick.getOrentation() == Orentation.UPRIGHT) {
			currentBrick.swapBlock(currentBrick.getBrick()[2], board[indexedDepth(1) + 2][indexedWidth(1)]);
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(1) + 2][indexedWidth(1) - 1]);
			currentBrick.setOrentation(Orentation.CLOCKWISE);
		} else if (currentBrick.getOrentation() == Orentation.CLOCKWISE) {
			currentBrick.swapBlock(currentBrick.getBrick()[2], board[indexedDepth(1)][indexedWidth(1) - 1]);
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(1) - 1][indexedWidth(1) - 1]);
			currentBrick.setOrentation(Orentation.FLIPFLOP);
		} else if (currentBrick.getOrentation() == Orentation.FLIPFLOP) {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(3) - 1][indexedWidth(3) + 1]);
			currentBrick.swapBlock(currentBrick.getBrick()[1], board[indexedDepth(3) - 1][indexedWidth(3)]);
			currentBrick.setOrentation(Orentation.COUNTERCLOCKWISE);
		} else {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(2) + 1][indexedWidth(2) + 1]);
			currentBrick.swapBlock(currentBrick.getBrick()[1], board[indexedDepth(2)][indexedWidth(2) + 1]);
			currentBrick.setOrentation(Orentation.UPRIGHT);
		}

	}

	public void rotateL() {
		if (currentBrick.getOrentation() == Orentation.UPRIGHT) {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(1) + 1][indexedWidth(1)]);
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(1) + 2][indexedWidth(1)]);
			currentBrick.setOrentation(Orentation.CLOCKWISE);
		} else if (currentBrick.getOrentation() == Orentation.CLOCKWISE) {
			currentBrick.swapBlock(currentBrick.getBrick()[2], board[indexedDepth(0)][indexedWidth(0) - 2]);
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(0)][indexedWidth(0) - 1]);
			currentBrick.setOrentation(Orentation.FLIPFLOP);
		} else if (currentBrick.getOrentation() == Orentation.FLIPFLOP) {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(2) - 1][indexedWidth(2)]);
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(2) - 2][indexedWidth(2)]);
			currentBrick.setOrentation(Orentation.COUNTERCLOCKWISE);
		} else {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(2) - 1][indexedWidth(2) + 2]);
			currentBrick.swapBlock(currentBrick.getBrick()[1], board[indexedDepth(2) - 1][indexedWidth(2) + 1]);
			currentBrick.setOrentation(Orentation.UPRIGHT);
		}

	}

	public void rotateS() {
		if (currentBrick.getOrentation() == Orentation.UPRIGHT) {
			currentBrick.swapBlock(currentBrick.getBrick()[2], board[indexedDepth(1) - 1][indexedWidth(1)]);
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(1) + 1][indexedWidth(1) + 1]);
			currentBrick.setOrentation(Orentation.CLOCKWISE);
		} else {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(1) - 1][indexedWidth(1)]);
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(1) - 1][indexedWidth(1) + 1]);
			currentBrick.setOrentation(Orentation.UPRIGHT);
		}
	}

	public void rotateT() {
		if (currentBrick.getOrentation() == Orentation.UPRIGHT) {
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(0) + 1][indexedWidth(0) + 1]);
			currentBrick.swapBlock(currentBrick.getBrick()[2], board[indexedDepth(0)][indexedWidth(0) + 1]);
			currentBrick.setOrentation(Orentation.CLOCKWISE);
		} else if (currentBrick.getOrentation() == Orentation.CLOCKWISE) {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(2) - 1][indexedWidth(2)]);
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(2)][indexedWidth(2) - 1]);
			currentBrick.setOrentation(Orentation.FLIPFLOP);
		} else if (currentBrick.getOrentation() == Orentation.FLIPFLOP) {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(1) - 1][indexedWidth(1) - 1]);
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(1) + 1][indexedWidth(1) - 1]);
			currentBrick.setOrentation(Orentation.COUNTERCLOCKWISE);
		} else {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(1) - 1][indexedWidth(1)]);
			currentBrick.swapBlock(currentBrick.getBrick()[2], board[indexedDepth(1) - 1][indexedWidth(1) + 1]);
			currentBrick.setOrentation(Orentation.UPRIGHT);
		}

	}

	public void rotateZ() {
		if (currentBrick.getOrentation() == Orentation.UPRIGHT) {
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(1)][indexedWidth(1) - 1]);
			currentBrick.swapBlock(currentBrick.getBrick()[3], board[indexedDepth(1) + 1][indexedWidth(1) - 1]);
			currentBrick.setOrentation(Orentation.CLOCKWISE);
		} else {
			print();
			System.out.println(currentBrick);
			currentBrick.swapBlock(currentBrick.getBrick()[0], board[indexedDepth(1) - 1][indexedWidth(1) - 1]);
			currentBrick.swapBlock(currentBrick.getBrick()[2], board[indexedDepth(1)][indexedWidth(1) + 1]);
			currentBrick.setOrentation(Orentation.UPRIGHT);
		}
	}

	/** This method moves down every brick above the row */
	public void sinkDown(int row) {
		for (int i = row - 1; i >= 0; i--) {
			for (int j = 0; j < board[row].length; j++) {
				// TODO see if this is okay
				if (board[i][j].isOn()) {
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
		currentBrick = new Brick('J', toggleAndReturn(1, width / 2 + 1), toggleAndReturn(0, width / 2 + 1),
				toggleAndReturn(0, width / 2), toggleAndReturn(0, width / 2 - 1));
	}

	/** Spawns the L shaped block and sets the active block to it */
	public void spawnL() {
		currentBrick = new Brick('L', toggleAndReturn(0, width / 2 + 1), toggleAndReturn(0, width / 2),
				toggleAndReturn(1, width / 2 - 1), toggleAndReturn(0, width / 2 - 1));
	}

	/** Spawns the O shaped block and sets the active block to it */
	public void spawnO() {
		// TODO make +1 into -1
		currentBrick = new Brick('O', toggleAndReturn(1, width / 2 + 1), toggleAndReturn(1, width / 2),
				toggleAndReturn(0, width / 2 + 1), toggleAndReturn(0, width / 2));
	}

	/** Spawns the S shaped block and sets the active block to it */
	public void spawnS() {
		currentBrick = new Brick('S', toggleAndReturn(1, width / 2), toggleAndReturn(1, width / 2 - 1),
				toggleAndReturn(0, width / 2 + 1), toggleAndReturn(0, width / 2));
	}

	/** Spawns the T shaped block and sets the active block to it */
	public void spawnT() {
		currentBrick = new Brick('T', toggleAndReturn(1, width / 2), toggleAndReturn(0, width / 2 + 1),
				toggleAndReturn(0, width / 2), toggleAndReturn(0, width / 2 - 1));
	}

	/** Spawns the Z shaped block and sets the active block to it */
	public void spawnZ() {
		currentBrick = new Brick('Z', toggleAndReturn(1, width / 2 + 1), toggleAndReturn(1, width / 2),
				toggleAndReturn(0, width / 2), toggleAndReturn(0, width / 2 - 1));
	}

	/** Returns true if the brick has stopped */
	public boolean stopBrick() {
		return !canMoveDown();
	}

	/** Toggles block i,j */
	public void toggle(int i, int j) {
		board[i][j].toggle();
	}

	/** Turns on a block and returns the block */
	public Block toggleAndReturn(int i, int j) {
		board[i][j].toggle();
		return board[i][j];
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

}
