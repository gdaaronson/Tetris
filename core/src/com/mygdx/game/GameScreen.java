package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

	Rectangle[][] block;
	Texture blockImage;
	Rectangle[] brick;
	Texture brickImage;
	OrthographicCamera camera;
	final Tetris game;
	int height;
	int heightp;
	private int level;
	final private int[] levelSpeed = { 48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2, 2,
			2, 2, 2, 2, 2, 1 };
	int levelTime = 60;
	private int linesClear;
	private int score;
	TetrisModel t;
	private Music tetrisMusic;
	int time = 0;
	private boolean wantToMoveLeft;
	private boolean wantToMoveRight;
	Texture white;
	int width;
	int widthp;

	public GameScreen(final Tetris game) {
		this.game = game;
		height = 20;
		width = 10;
		t = new TetrisModel(height, width);
		t.insertRandomBrick();
		heightp = 480;
		widthp = 720;
		level = 0;
		score = 0;
		linesClear = 0;
		blockImage = new Texture(Gdx.files.internal("badlogic.jpg"));
		brickImage = new Texture(Gdx.files.internal("bhb23jpg.bmp"));
		white = new Texture(Gdx.files.internal("Blank White.png"));

		tetrisMusic = Gdx.audio.newMusic(Gdx.files.internal("Tetris A.mp3"));
		tetrisMusic.setLooping(true);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, widthp + 80, heightp);

		brick = new Rectangle[4];
		for (int i = 0; i < brick.length; i++) {
			brick[i] = new Rectangle();
			brick[i].set(getPixelWidth(i), getPixelHeight(i), widthp / t.getWidth(), heightp / t.getHeight());
		}

		block = new Rectangle[height][width];
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				block[i][j] = new Rectangle();
				block[i][j].set(getPixelWidth(i, j), getPixelHeight(i, j), widthp / t.getWidth(),
						heightp / t.getHeight());
			}
		}
	}

	@Override
	public void dispose() {
		game.setScreen(new EndGameScreen(game));
	}

	private int getPixelHeight(int block) {
		return heightp - t.getCurrentBrick().getBrick()[block].getDepth() * heightp / t.getHeight()
				- heightp / t.getHeight();
	}

	private float getPixelHeight(int i, int j) {
		return heightp - t.getBoard()[i][j].getDepth() * heightp / t.getHeight() - heightp / t.getHeight();
	}

	private int getPixelWidth(int block) {
		return t.getCurrentBrick().getBrick()[block].getWidth() * widthp / t.getWidth();
	}

	private float getPixelWidth(int i, int j) {
		return t.getBoard()[i][j].getWidth() * widthp / t.getWidth();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the blocks
		game.batch.begin();

		// TODO make this line work
		game.batch.draw(white, 0, widthp, 200, heightp);

		game.font.draw(game.batch, "Next: " + t.getNextBrick(), widthp, heightp - 50);
		game.font.draw(game.batch, "Level:", widthp, heightp - 100);
		game.font.draw(game.batch, "" + level, widthp, heightp - 150);
		game.font.draw(game.batch, "Score:", widthp, heightp - 200);
		game.font.draw(game.batch, "" + score, widthp, heightp - 250);
		game.font.draw(game.batch, "Lines:", widthp, heightp - 300);
		game.font.draw(game.batch, "cleared:", widthp, heightp - 325);
		game.font.draw(game.batch, "" + linesClear, widthp, heightp - 350);

		// Draw the blocks
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[i].length; j++) {
				// Draw a block
				if (t.getBoard()[i][j].isOn()) {
					game.batch.draw(blockImage, block[i][j].x, block[i][j].y, widthp / t.getWidth(),
							heightp / t.getHeight());
				}
				// Draw a brick
				if (t.partOfBrick(t.getBoard()[i][j])) {
					game.batch.draw(brickImage, block[i][j].x, block[i][j].y, widthp / t.getWidth(),
							heightp / t.getHeight());
				}
			}
		}
		game.batch.end();

		// Detect move down
		if (Gdx.input.isKeyPressed(Keys.S) || ((level <= 29 && time % levelSpeed[level] == levelSpeed[level] - 1)
				|| (level > 29 && time % levelSpeed[29] == levelSpeed[29] - 1))) {
			if (t.canMoveDown()) {
				t.moveDown();
				for (Rectangle block : brick) {
					block.y -= heightp / t.getHeight();
				}
			}
			if (wantToMoveLeft && t.canMoveLeft()) {
				t.moveLeft();
				for (Rectangle block : brick) {
					block.x -= widthp / t.getWidth();
				}
				wantToMoveLeft = false;
			}
			if (wantToMoveRight && t.canMoveRight()) {
				t.moveRight();
				for (Rectangle block : brick) {
					block.x += widthp / t.getWidth();
				}
				wantToMoveRight = false;
			}
		}

		// Detect move left
		if (Gdx.input.isKeyJustPressed(Keys.A)) {
			if (t.canMoveLeft()) {
				t.moveLeft();
				for (Rectangle block : brick) {
					block.x -= widthp / t.getWidth();
				}
			} else {
				wantToMoveLeft = true;
				wantToMoveRight = false;
			}
		}

		// Detect move right
		if (Gdx.input.isKeyJustPressed(Keys.D)) {
			if (t.canMoveRight()) {
				t.moveRight();
				for (Rectangle block : brick) {
					block.x += widthp / t.getWidth();
				}
			} else {
				wantToMoveRight = true;
				wantToMoveLeft = false;
			}
		}

		// Detect rotate
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			t.rotate();
			for (int i = 0; i < brick.length; i++) {
				brick[i].x = getPixelWidth(i);
				brick[i].y = getPixelHeight(i);
			}
		}

		// See if any rows are complete and scores them
		if (t.stopBrick()) {
			int rowClear = t.checkForCompletion();
			if (rowClear == 1) {
				score += 40 * (level + 1);
				linesClear++;
			} else if (rowClear == 2) {
				score += 100 * (level + 1);
				linesClear += 2;
			} else if (rowClear == 3) {
				score += 300 * (level + 1);
				linesClear += 3;
			} else if (rowClear == 4) {
				score += 1200 * (level + 1);
				linesClear += 4;
			}
		}
		
		if (level * 1 + 1 < linesClear || linesClear > 100) {
			level++;
			linesClear = 0;
		}

		// If the brick has stopped, make a new brick
		if (t.stopBrick() && ((level <= 29 && time % levelSpeed[level] == levelSpeed[level] - 1)
				|| (level > 29 && time % levelSpeed[29] == levelSpeed[29] - 1))) {
			t.insertRandomBrick();
		}

		// Check to see if the game is over
		// TODO make sure this works
		if (t.endGameDetection() && t.checkForCompletion() == 0) {
			dispose();
		}
		System.out.println(time);
		System.out.println(t);
		time++;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		tetrisMusic.play();
	}

}
