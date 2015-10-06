package com.mygdx.game;

/**Each brick has 4 block coordinates associated with it*/
public class Brick {
	
	private Block[] brick;
	
	private char key;
	
	private Orentation orentation;
	
	public char getKey() {
		return key;
	}

	public Brick(char key, Block ... blocks){
		brick = blocks;
		this.key = key;
		orentation = Orentation.UPRIGHT;
	}
	
	public Block[] getBrick(){
		return brick;
	}
			
	public void swapBlock(Block old, Block current){
		for(int i = 0; i < brick.length; i++){
			if(old.equals(brick[i])){
				old.toggle();
				brick[i] = current;
				current.toggle();
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

	public Orentation getOrentation() {
		return orentation;
	}

	public void setOrentation(Orentation orentation) {
		this.orentation = orentation;
	}
}
