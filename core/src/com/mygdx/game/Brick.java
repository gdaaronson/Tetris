package com.mygdx.game;

/** Each brick has 4 block coordinates associated with it */
public class Brick {

	private Block[] brick;

	private char key;

	private Orentation orentation;

	public char getKey() {
		return key;
	}

	public Brick(char key, Block... blocks) {
		brick = blocks;
		this.key = key;
		orentation = Orentation.UPRIGHT;
	}

	public Block[] getBrick() {
		return brick;
	}

	public void swapBlock(Block old, Block current) {
		for (int i = 0; i < brick.length; i++) {
			if (old.equals(brick[i])) {
				old.toggle();
				brick[i] = current;
				current.toggle();
			}
		}
	}
	
	public void sort(){
		sort(3);
	}

	public void sort(int start) {
		if (start >= 0) {
			int maxDepth = brick[3 - start].getDepth();
			int maxWidth = brick[3 - start].getWidth();
			int maxIndex = 3 - start;
			for (int i = 4 - start; i < brick.length; i++) {
				if ((maxDepth < brick[i].getDepth()) || (maxDepth == brick[i].getDepth() && maxWidth < brick[i].getWidth())) {
					maxIndex = i;
					maxDepth = brick[i].getDepth();
					maxWidth = brick[i].getWidth();
				} 
			}
			swap(brick[3 - start], brick[maxIndex]);
			sort(start - 1);
		}
	}

	public void swap(Block block, Block block2) {
		int index1 = -1;
		int index2 = -1;
		for (int i = 0; i < brick.length; i++) {
			if (block.equals(brick[i])) {
				index1 = i;
			} 
			if(block2.equals(brick[i])){
				index2 = i;
			}
		}
		Block temp = brick[index1];
		brick[index1] = brick[index2];
		brick[index2] = temp;

	}

	public String toString() {
		String s = "";
		for (Block b : brick) {
			s += b.toString();
		}
		return s;
	}

	public Orentation getOrentation() {
		return orentation;
	}

	public void setOrentation(Orentation orentation) {
		this.orentation = orentation;
	}
}
