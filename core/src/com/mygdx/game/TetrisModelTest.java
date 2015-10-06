package com.mygdx.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TetrisModelTest {

	TetrisModel t;
	
	@Before
	public void setUp() throws Exception {
		t = new TetrisModel(5,5);
	}

	@Test
	public void testToString() {
		String s = ".....\n.....\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
		t.turnOn(0, 0);
		s = "o....\n.....\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
	}
	
	@Test
	public void testSpawnI(){
		t.spawnI();
		String s = "oooo.\n.....\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
		t.insertBrick(0);
		assertEquals(s, t.toString());
		t.moveDown(t.getCurrentBrick());
		assertEquals(".....\noooo.\n.....\n.....\n.....\n", t.toString());
	}
	
	@Test
	public void testSpawnJ(){
		t.spawnJ();
		String s = ".ooo.\n...o.\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
		t.insertBrick(1);
		assertEquals(s, t.toString());
		t.moveDown(t.getCurrentBrick());
		assertEquals(".....\n.ooo.\n...o.\n.....\n.....\n", t.toString());

	}
	
	@Test
	public void testSpawnL(){
		t.spawnL();
		String s = ".ooo.\n.o...\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
		t.insertBrick(2);
		assertEquals(s, t.toString());
		t.moveDown(t.getCurrentBrick());
		assertEquals(".....\n.ooo.\n.o...\n.....\n.....\n", t.toString());

	}
	
	@Test
	public void testSpawnO(){
		t.spawnO();
		String s = "..oo.\n..oo.\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
		t.insertBrick(3);
		assertEquals(s, t.toString());
		t.moveDown(t.getCurrentBrick());
		assertEquals(".....\n..oo.\n..oo.\n.....\n.....\n", t.toString());

	}
	
	@Test
	public void testSpawnS(){
		t.spawnS();
		String s = "..oo.\n.oo..\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
		t.insertBrick(4);
		assertEquals(s, t.toString());
		t.moveDown(t.getCurrentBrick());
		assertEquals(".....\n..oo.\n.oo..\n.....\n.....\n", t.toString());

	}
	
	@Test
	public void testSpawnT(){
		t.spawnT();
		String s = ".ooo.\n..o..\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
		t.insertBrick(5);
		assertEquals(s, t.toString());
		t.moveDown(t.getCurrentBrick());
		assertEquals(".....\n.ooo.\n..o..\n.....\n.....\n", t.toString());

	}
	
	@Test
	public void testSpawnZ(){
		t.spawnZ();
		String s = ".oo..\n..oo.\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
		t.insertBrick(6);
		assertEquals(s, t.toString());
		t.moveDown(t.getCurrentBrick());
		assertEquals(".....\n.oo..\n..oo.\n.....\n.....\n", t.toString());

	}
	
	@Test
	public void modelConstructorTest(){
		t = new TetrisModel(5,5,"....................ooooo");
		assertEquals(".....\n.....\n.....\n.....\nooooo\n",t.toString());
	}
	
	@Test
	public void testCheckForCompleation(){
		t = new TetrisModel(5, 5, "....................ooooo");
		assertEquals(1, t.checkForCompletion());
		t = new TetrisModel(5, 5, "...............oooooooooo");
		assertEquals(2, t.checkForCompletion());
		t = new TetrisModel(5, 5, "oooooooooo.....oooooooooo");
		assertEquals(4, t.checkForCompletion());
	}
	
	@Test
	public void sinkDownTest(){
		t = new TetrisModel(5, 5, ".oooooooooooooooooooooooo");
		assertEquals(4, t.checkForCompletion());
		assertEquals(".....\n.....\n.....\n.....\n.oooo\n", t.toString());
		
		t = new TetrisModel(5, 5, ".oooo..ooo...oo....oooooo");
		assertEquals(1, t.checkForCompletion());
		assertEquals(".....\n.oooo\n..ooo\n...oo\n....o\n", t.toString());
		
		t = new TetrisModel(5, 5, ".oooo..oooooooo....oooooo");
		assertEquals(2, t.checkForCompletion());
		assertEquals(".....\n.....\n.oooo\n..ooo\n....o\n", t.toString());
	}
	
	@Test
	public void moveTest(){
		t.spawnI();
		
		assertEquals("oooo.\n.....\n.....\n.....\n.....\n", t.toString());
		System.out.println(t.getCurrentBrick());
		t.moveRight(t.getCurrentBrick());
		System.out.println(t.getCurrentBrick());
		assertEquals(".oooo\n.....\n.....\n.....\n.....\n", t.toString());
		t.moveLeft(t.getCurrentBrick());
		assertEquals("oooo.\n.....\n.....\n.....\n.....\n", t.toString());
	}
}
