package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyGdxGame implements ApplicationListener {
	private SpriteBatch batch;
	private Skin skin;
	private Stage stage;
	private Float sqLen;

	@Override
	public void create() {
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage();
		sqLen = 200f;

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

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
}
