package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.characters.Centauro;
import com.mygdx.characters.GenericBicho;
import com.mygdx.characters.Invader;
import com.mygdx.characters.Megaman;


public class AttackMap extends GenericMap implements ApplicationListener, InputProcessor {
	private SpriteBatch batch;
	private Skin skin;
	public Stage stage;
	private Float sqLen;
	private BitmapFont font, font2;
	private GlyphLayout goldLayout, ammoLayout;
	private Texture background;

	private int typeBicho = 1;


	@Override
	public void create() {
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage();
		sqLen = 200f; // CALCULATE A REASONABLE VALUE
		gold = 20;

		background = new Texture("background.png");

		Gdx.app.log("CREATE", "create");

		// Add listener in screen and stage
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);

		initializeButtonsTexts();

		goldThread();

	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render() {
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

		batch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		goldLayout.setText(font, goldLabel);
		float w = goldLayout.width;
		float y = Gdx.graphics.getHeight() - goldLayout.height;
		font.draw(batch, goldLabel, Gdx.graphics.getWidth() - w, y);

		batch.end();

        batch.begin();
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
        batch.end();

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/**
	 * Pulsación en la pantalla -> crear nuevo bicho
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("TOUCHDOWN", "Megaman va!");

		GenericBicho actor = null;
		switch (typeBicho) {
			case 1:
				actor = new Invader(screenX, Gdx.graphics.getHeight()-screenY, this, true);
				break;
			case 2:
				actor = new Megaman(screenX, Gdx.graphics.getHeight()-screenY, this, true);
				break;
			case 3:
				actor = new Centauro(screenX, Gdx.graphics.getHeight()-screenY, this, true);
				break;
		}
		actor.setTouchable(Touchable.disabled);

		if (gold - actor.getPrice() >= 0) {
			gold -= actor.getPrice();
			goldLabel = "GOLD: " + gold;
			stage.addActor(actor);
		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


	private void initializeButtonsTexts() {
		font = new BitmapFont();
		font.getData().setScale(4);
		font.setColor(Color.GOLD);
		font2 = new BitmapFont();
		font2.getData().setScale(4);
		font2.setColor(Color.RED);

		goldLabel = "GOLD: " + gold;
		goldLayout = new GlyphLayout();
		ammoLayout = new GlyphLayout();
		//w = Gdx.graphics.getWidth();
		//h = Gdx.graphics.getHeight();

		final ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("invader_button.png")))); //Set the button up
		final ImageButton button2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("megaman_button.png")))); //Set the button up
		final ImageButton button3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("centauro_button.png")))); //Set the button up


		final TextButton settings = new TextButton("Click me", skin, "default");

		settings.setWidth(sqLen*3/5);
		settings.setHeight(sqLen*3/5);
		settings.setPosition(30f, Gdx.graphics.getHeight() - sqLen*3/5 - 30f);

		button.setWidth(sqLen);
		button.setHeight(sqLen);
		button.setPosition(Gdx.graphics.getWidth() /5 - sqLen + 30f, 30f);

		button2.setWidth(sqLen);
		button2.setHeight(sqLen);
		button2.setPosition(Gdx.graphics.getWidth()*3/5 - sqLen, 30f);

		button3.setWidth(sqLen);
		button3.setHeight(sqLen);
		button3.setPosition(Gdx.graphics.getWidth() - sqLen -30f, 30f);

		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				//button.setText("You clicked the button");
				typeBicho = 1;
			}
		});

		button2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				//button2.setText("You clicked the button");
				typeBicho = 2;
			}
		});

		button3.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				//button3.setText("You clicked the button");
				typeBicho = 3;
			}
		});

		settings.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				settings.setText("You clicked the button");
			}
		});

		//TO-DO: ADD THE FUNCTIONALITY TO THE LISTENERS

		stage.addActor(button);
		stage.addActor(button2);
		stage.addActor(button3);
		stage.addActor(settings);
	}

	private void goldThread(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// do something important here, asynchronously to the rendering thread
				// post a Runnable to the rendering thread that processes the result
				while (true) {
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							// process the result, e.g. add it to an Array<Result> field of the ApplicationListener.


							Array<Actor> actors = stage.getActors();
							int count = 0;
							for (Actor a:actors) {
								if (a instanceof GenericBicho) {
									count ++;
									gold ++;
									goldLabel = "GOLD: " + gold;
								}
							}

							Gdx.app.log("THREAD", "Num Actors: " + count);

						}
					});

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}


}
