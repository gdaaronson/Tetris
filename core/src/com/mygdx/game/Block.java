package com.mygdx.game;

public class Block {
	
	int depth;
	
	int width;
	
	boolean on;
	
	/**Initializes the block at location depth and width from the left*/
	public Block(int depth, int width){
		this.depth = depth;
		this.width = width;
		on = false;
	}
	
	public boolean isOn(){
		return on;
	}
	
	public void toggle(){
		on = !on;
	}
	
	public void setOn(boolean on) {
		this.on = on;
	}

	public int getDepth() {
		return depth;
	}

	public int getWidth() {
		return width;
	}
	
	public String toString(){
		return "(" + depth + ", " + width + ")"; 
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	
}
