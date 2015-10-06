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
	public void rotateI(){
		t.spawnI();
		t.rotate();
		System.out.println(t.getCurrentBrick());
		assertEquals("o....\no....\no....\no....\n.....\n", t.toString());
	}
	
	@Test
	public void stoppingDownTest(){
		t = new TetrisModel(6,6,"..............................o.oooo");
		t.spawnI();
		t.moveDown();
		t.moveDown();
		t.moveDown();
		t.moveDown();
		t.moveDown();
		assertEquals("......\n......\n......\n......\n.oooo.\no.oooo\n", t.toString());
		t = new TetrisModel(6,6,"........................o....oo.oooo");
		t.spawnI();
		t.moveDown();
		t.moveDown();
		t.moveDown();
		t.moveDown();
		t.checkForCompletion();
		assertEquals("......\n......\n......\n......\n......\no.oooo\n", t.toString());
	}
	
	@Test
	public void stoppingDownAndCompletingTest(){
		t = new TetrisModel(6,6,"..............................oo.ooo");
		t.spawnL();
		t.moveDown();
		t.moveDown();
		t.moveDown();
		t.moveDown();
		t.moveDown();
		t.checkForCompletion();
		assertEquals("......\n......\n......\n......\n......\n..ooo.\n", t.toString());
	}
	
	@Test
	public void stoppingLeftAndRight(){
		t.spawnI();
		assertEquals("oooo.\n.....\n.....\n.....\n.....\n", t.toString());
		t.moveLeft();
		assertEquals("oooo.\n.....\n.....\n.....\n.....\n", t.toString());
		t.moveRight();
		t.moveRight();
		assertEquals(".oooo\n.....\n.....\n.....\n.....\n", t.toString());
	}
	
	@Test
	public void testToString() {
		String s = ".....\n.....\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
		t.toggle(0, 0);
		s = "o....\n.....\n.....\n.....\n.....\n";
		assertEquals(s, t.toString());
	}
	
	@Test
	public void testSpawnI(){
		t.insertBrick(0);
		assertEquals("oooo.\n.....\n.....\n.....\n.....\n", t.toString());
	}
	
	@Test
	public void testSpawnJ(){
		t.insertBrick(1);
		assertEquals(".ooo.\n...o.\n.....\n.....\n.....\n", t.toString());

	}
	
	@Test
	public void testSpawnL(){
		t.insertBrick(2);
		assertEquals(".ooo.\n.o...\n.....\n.....\n.....\n", t.toString());

	}
	
	@Test
	public void testSpawnO(){
		t.insertBrick(3);
		assertEquals("..oo.\n..oo.\n.....\n.....\n.....\n", t.toString());

	}
	
	@Test
	public void testSpawnS(){
		t.insertBrick(4);
		assertEquals("..oo.\n.oo..\n.....\n.....\n.....\n", t.toString());

	}
	
	@Test
	public void testSpawnT(){
		t.insertBrick(5);
		assertEquals(".ooo.\n..o..\n.....\n.....\n.....\n", t.toString());
	}
	
	@Test
	public void testSpawnZ(){
		t.insertBrick(6);
		assertEquals(".oo..\n..oo.\n.....\n.....\n.....\n", t.toString());
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
	public void moveITest(){
		t.spawnI();
		assertEquals("oooo.\n.....\n.....\n.....\n.....\n", t.toString());
		t.moveRight();
		assertEquals(".oooo\n.....\n.....\n.....\n.....\n", t.toString());
		t.moveLeft();
		assertEquals("oooo.\n.....\n.....\n.....\n.....\n", t.toString());
		t.moveDown();
		assertEquals(".....\noooo.\n.....\n.....\n.....\n", t.toString());
	}

	@Test
	public void moveJTest(){
		t.spawnJ();
		assertEquals(".ooo.\n...o.\n.....\n.....\n.....\n", t.toString());
		t.moveRight();
		assertEquals("..ooo\n....o\n.....\n.....\n.....\n", t.toString());
		t.moveLeft();
		assertEquals(".ooo.\n...o.\n.....\n.....\n.....\n", t.toString());
		t.moveDown();
		assertEquals(".....\n.ooo.\n...o.\n.....\n.....\n", t.toString());
	}
	
	@Test
	public void moveLTest(){
		t.spawnL();
		assertEquals(".ooo.\n.o...\n.....\n.....\n.....\n", t.toString());
		t.moveRight();
		assertEquals("..ooo\n..o..\n.....\n.....\n.....\n", t.toString());
		t.moveLeft();
		assertEquals(".ooo.\n.o...\n.....\n.....\n.....\n", t.toString());
		t.moveDown();
		assertEquals(".....\n.ooo.\n.o...\n.....\n.....\n", t.toString());
	}
	
	@Test
	public void moveOTest(){
		t.spawnO();
		assertEquals("..oo.\n..oo.\n.....\n.....\n.....\n", t.toString());
		t.moveRight();
		assertEquals("...oo\n...oo\n.....\n.....\n.....\n", t.toString());
		t.moveLeft();
		assertEquals("..oo.\n..oo.\n.....\n.....\n.....\n", t.toString());
		t.moveDown();
		assertEquals(".....\n..oo.\n..oo.\n.....\n.....\n", t.toString());
	}

	@Test
	public void moveSTest(){
		t.spawnS();
		assertEquals("..oo.\n.oo..\n.....\n.....\n.....\n", t.toString());
		t.moveRight();
		assertEquals("...oo\n..oo.\n.....\n.....\n.....\n", t.toString());
		t.moveLeft();
		assertEquals("..oo.\n.oo..\n.....\n.....\n.....\n", t.toString());
		t.moveDown();
		assertEquals(".....\n..oo.\n.oo..\n.....\n.....\n", t.toString());
	}
	
	@Test
	public void moveTTest(){
		t.spawnT();
		assertEquals(".ooo.\n..o..\n.....\n.....\n.....\n", t.toString());
		t.moveRight();
		assertEquals("..ooo\n...o.\n.....\n.....\n.....\n", t.toString());
		t.moveLeft();
		assertEquals(".ooo.\n..o..\n.....\n.....\n.....\n", t.toString());
		t.moveDown();
		assertEquals(".....\n.ooo.\n..o..\n.....\n.....\n", t.toString());
	}
	
	@Test
	public void moveZTest(){
		t.spawnZ();
		assertEquals(".oo..\n..oo.\n.....\n.....\n.....\n", t.toString());
		t.moveRight();
		assertEquals("..oo.\n...oo\n.....\n.....\n.....\n", t.toString());
		t.moveLeft();
		assertEquals(".oo..\n..oo.\n.....\n.....\n.....\n", t.toString());
		t.moveDown();
		assertEquals(".....\n.oo..\n..oo.\n.....\n.....\n", t.toString());
	}
}
