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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.characters.Megaman;



public class MyGdxGame implements ApplicationListener, InputProcessor {
	private SpriteBatch batch;
	private Skin skin;
	private Stage stage;
	private Float sqLen;
	private BitmapFont font, font2;
	private int gold, ammo;
	private String goldLabel, ammoLabel;
	private GlyphLayout goldLayout, ammoLayout;
	private Texture background;


	@Override
	public void create() {
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage();
		sqLen = 200f; // CALCULATE A REASONABLE VALUE

		background = new Texture("background.png");

		Gdx.app.log("CREATE", "create");

		// Add listener in screen and stage
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);

		font = new BitmapFont();
		font.getData().setScale(4);
		font.setColor(Color.GOLD);
		font2 = new BitmapFont();
		font2.getData().setScale(4);
		font2.setColor(Color.RED);

		gold = 0;
		goldLabel = "GOLD: " + gold;
		ammo = 10;
		ammoLabel = "AMMO: "+ ammo;
		goldLayout = new GlyphLayout();
		ammoLayout = new GlyphLayout();
		//w = Gdx.graphics.getWidth();
		//h = Gdx.graphics.getHeight();

		final TextButton button = new TextButton("Click me", skin, "default");
		final TextButton button2 = new TextButton("Click me", skin, "default");
		final TextButton button3 = new TextButton("Click me", skin, "default");

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
				button.setText("You clicked the button");
			}
		});

		button2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				button2.setText("You clicked the button");
			}
		});

		button3.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				button3.setText("You clicked the button");
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

		//Gdx.input.setInputProcessor(stage);
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

		ammoLayout.setText(font, ammoLabel);
		w = ammoLayout.width;
		y = y - ammoLayout.height - 30f;
		font2.draw(batch, ammoLabel, Gdx.graphics.getWidth() - w, y);

		batch.end();

        batch.begin();
        stage.act(Gdx.graphics.getDeltaTime());
        batch.end();

        batch.begin();
        stage.draw();
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

		Megaman b = new Megaman(screenX, Gdx.graphics.getHeight()-screenY);
		b.setTouchable(Touchable.enabled);

		stage.addActor(b);

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

}
