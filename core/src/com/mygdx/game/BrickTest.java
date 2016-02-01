package com.mygdx.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BrickTest {

	Brick b;
	TetrisModel t;
	
	@Before
	public void setUp() throws Exception {
		t = new TetrisModel(5,5);
		b = new Brick('I', new Block(0,0), new Block(2,1), new Block(0,1), new Block(1,1));
	}
	
	@Test
	public void testSwap(){
		b.swap(b.getBrick()[0], b.getBrick()[1]);
		assertEquals("(2, 1)(0, 0)(0, 1)(1, 1)", b.toString());
	}
	@Test
	public void testSort() {
		b.sort();
		assertEquals("(2, 1)(1, 1)(0, 1)(0, 0)", b.toString());
	}

}
