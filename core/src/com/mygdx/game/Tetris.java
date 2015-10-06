package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Tetris implements ApplicationListener {
	SpriteBatch batch;
	Texture img;
	Array<Rectangle> block;
	Array<Boolean> active;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        block = new Array<Rectangle>();
        active = new Array<Boolean>();
        spawnBlock();
	}

	private int x = 320;
	private int y = 300;
	public int maxlength = 640;
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y, 60, 60);
        for (int i = 0; i < block.size; i++) {
        	if(!active.get(i)){
        		batch.draw(img, block.get(i).x, block.get(i).y, 60, 60);
        	}else{
        		batch.draw(img, x, y, 60, 60);
        	}
        }
	    if(Gdx.input.isKeyJustPressed(Keys.LEFT) && x > 0){
	    	x -= maxlength/10;
	    }
	    if(Gdx.input.isKeyJustPressed(Keys.RIGHT) && x < 9*maxlength/10) {
	    	x += maxlength/10;	    
	    }
	    if(y > 0){
	    	y = y - 2;
	    }else{
	    	active.removeIndex(active.lastIndexOf(true, false));
	    	active.add(false);
	    	spawnBlock();
	    }
	    batch.end();
	}

    private void spawnBlock() {
        Rectangle block = new Rectangle();
        block.x = 320;
        block.y = 300;
        block.width = 80;
        block.height = 80;
        this.block.add(block);
        active.add(true);
    }
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
