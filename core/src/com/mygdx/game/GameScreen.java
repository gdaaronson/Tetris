package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

	final Tetris game;
	Texture blockImage;
	Texture brickImage;
	OrthographicCamera camera;
	Rectangle[] brick;
	Rectangle[][] block;
	TetrisModel t;
	int width;
	int height;

	public GameScreen(final Tetris game) {
		this.game = game;
		t = new TetrisModel(8, 8);
		t.insertRandomBrick();
		height = 480;
		width = 800;

		blockImage = new Texture("badlogic.jpg");
		brickImage = new Texture("badlogic.jpg");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		brick = new Rectangle[4];
		for (int i = 0; i < brick.length; i++) {
			brick[i] = new Rectangle();
			brick[i].set(getPixelWidth(i), getPixelHeight(i), width / t.getWidth(), height / t.getHeight());
		}
		
		block = new Rectangle[8][8];
		for (int i = 0; i < block.length; i++){
			for (int j = 0; j < block[i].length; j++) {
				block[i][j] = new Rectangle();
				block[i][j].set(getPixelWidth(i,j), getPixelHeight(i, j), width / t.getWidth(), height / t.getHeight());

			}
		}
	}

	private float getPixelHeight(int i, int j) {
		// TODO Auto-generated method stub
		return height - t.getBoard()[i][j].getDepth() * height / t.getHeight()
				- height / t.getHeight();
	}

	private float getPixelWidth(int i, int j) {
		return t.getBoard()[i][j].getWidth() * width / t.getWidth();
	}

	private int getPixelHeight(int block) {
		return height - t.getCurrentBrick().getBrick()[block].getDepth() * height / t.getHeight()
				- height / t.getHeight();
	}

	private int getPixelWidth(int block) {
		return t.getCurrentBrick().getBrick()[block].getWidth() * width / t.getWidth();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();

		//Draw the brick
		for (Rectangle block : brick) {
			game.batch.draw(brickImage, block.x, block.y, width / t.getWidth(), height / t.getHeight());
		}
		
		//Draw the blocks
		for (int i = 0; i < block.length; i++){
			for (int j = 0; j < block[i].length; j++) {
				if(t.getBoard()[i][j].isOn()){
					game.batch.draw(blockImage, block[i][j].x, block[i][j].y, width / t.getWidth(), height / t.getHeight());
				}
			}
		}
		game.batch.end();

		if (Gdx.input.isKeyJustPressed(Keys.S)) {
			if (t.canMoveDown()) {
				t.moveDown();
				for (Rectangle block : brick) {
					block.y -= height / t.getHeight();
				}
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.A)) {
			if (t.canMoveLeft()) {
				t.moveLeft();
				for (Rectangle block : brick) {
					block.x -= width / t.getWidth();
				}
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.D)) {
			if (t.canMoveRight()) {
				t.moveRight();
				for (Rectangle block : brick) {
					block.x += width / t.getWidth();
				}
			}
		}

		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			t.rotate();
			for (int i = 0; i < brick.length; i++) {
				brick[i].x = getPixelWidth(i);
				brick[i].y = getPixelHeight(i);
			}
		}
		t.checkForCompletion();
		if(t.stopBrick()){
			t.insertRandomBrick();
		}
		if(t.endGameDetection()){
			dispose();
		}
		System.out.println(t);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		game.setScreen(new EndGameScreen(game));

	}

}
