package com.mygdx.game;

/**Each brick has 4 block coordinates associated with it*/
public class Brick {
	
	private Block[] brick;
	
	private char key;
	
	public char getKey() {
		return key;
	}

	public Brick(char key, Block ... blocks){
		brick = blocks;
		this.key = key;
	}
	
	public Block[] getBrick(){
		return brick;
	}
			
	public void swapBlock(Block old, Block current){
		for(int i = 0; i < brick.length; i++){
			if(old.equals(brick[i])){
				brick[i] = current;
			}
		}
	}
	
	public String toString(){
		String s = "";
		for(Block b: brick){
			s += b.toString();
		}
		return s;
	}
}
