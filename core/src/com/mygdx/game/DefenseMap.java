package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.characters.GenericBicho;
import com.mygdx.server.ComunicationProtocol;

import java.util.Random;


public class DefenseMap extends GenericMap implements ApplicationListener, InputProcessor {
	private SpriteBatch batch;
	private Skin skin;
	private Float sqLen;
	private BitmapFont font, font2;
	private GlyphLayout goldLayout, ammoLayout;
	private Texture background;

	private int typeBicho = 1;
	private boolean freeze = false;

	private final int AMMO_PRICE = 10;
	private final int FREEZE_PRICE = 50;
	private final int METEOR_PRIZE = 50;
	private final int INIT_AMMO = 10;
	private final int INIT_GOLD = 60;
	private final int MAX_METEOR_DISTANCE = 250;



	@Override
	public void create() {
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		stage = new Stage();
		sqLen = (float) Math.round(Gdx.graphics.getWidth()/5.0); // CALCULATE A REASONABLE VALUE

        cp = new ComunicationProtocol("1235", this);
        cp.connect();

        ammo = INIT_AMMO;
		gold = INIT_GOLD;

		background = new Texture("background.png");

		Gdx.app.log("CREATE", "create");

		// Add listener in screen and stage
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);

		initializeButtonsTexts();

		//goldThread();

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
		if(!freeze) {
			stage.act(Gdx.graphics.getDeltaTime());
		}
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

		if (this.ammo > 0) {
			this.ammo--;
			this.ammoLabel = "AMMO: " + this.ammo;
			return true;
		}

		/*Gdx.app.log("TOUCHDOWN", "Megaman va!");

		GenericBicho actor = null;
		switch (typeBicho) {
			case 1:
				actor = new Invader(screenX, Gdx.graphics.getHeight()-screenY, this);
				break;
			case 2:
				actor = new Megaman(screenX, Gdx.graphics.getHeight()-screenY, this);
				break;
			case 3:
				actor = new Centauro(screenX, Gdx.graphics.getHeight()-screenY, this);
				break;
		}
		actor.setTouchable(Touchable.enabled);

		if (gold - actor.getPrice() >= 0) {
			gold -= actor.getPrice();
			goldLabel = "GOLD: " + gold;
			stage.addActor(actor);
		}*/

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
		ammoLabel = "AMMO: "+ ammo;
		goldLayout = new GlyphLayout();
		ammoLayout = new GlyphLayout();
		//w = Gdx.graphics.getWidth();
		//h = Gdx.graphics.getHeight();

		final ImageButton button = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("meteor_button.png")))); //Set the button up
		final ImageButton button2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("ammo_button.png")))); //Set the button up
		final ImageButton button3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("snowflake_button.png")))); //Set the button up


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


		// Meteor
		button.addListener(new ClickListener(){

            // TODO: restar monedicas

			@Override
			public void clicked(InputEvent event, float x, float y){

				new Thread(new Runnable() {
					@Override
					public void run() {
					for (int i=0; i<6; i++) {
						Random r = new Random();
						int rx = r.nextInt(Gdx.graphics.getWidth());
						int ry = r.nextInt(Gdx.graphics.getHeight());

						Array<Actor> actors = stage.getActors();
						for (Actor a:actors) {
							if (a instanceof GenericBicho) {
								if (close(a.getX(), a.getY(), rx, ry)) {
									a.remove();
								}
							}
						}

                        class MyActor extends Actor {
                            private Texture texture;
                            private int rx,ry;
                            public MyActor(String text, int rx, int ry) {
                                this.texture = new Texture(text);
                                setBounds(rx,ry, Math.round(Gdx.graphics.getWidth()*0.3), Math.round(Gdx.graphics.getWidth()*0.3));
                                this.rx = rx; this.ry=ry;
                            }
                            @Override
                            public void draw(Batch batch, float alpha){
                                //batch.draw(texture,rx,ry);
                                batch.draw(this.texture,rx, ry, Math.round(Gdx.graphics.getWidth()*0.3), Math.round(Gdx.graphics.getWidth()*0.3));
                            }
                        }

                        MyActor a1 = new MyActor("001.png", rx,ry);
                        stage.addActor(a1);
						try {
							Thread.sleep(400);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
                        a1.remove();

					}
					}
				}).start();

			}
		});

		// Ammo
		button2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				//button2.setText("You clicked the button");
				if (gold - AMMO_PRICE >= 0) {
					gold -= AMMO_PRICE;
					ammo += AMMO_PRICE;
					ammoLabel = "AMMO: " + ammo;
					goldLabel = "GOLD: " + gold;
				}
			}
		});

		// Freeze
		button3.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if (gold - FREEZE_PRICE >= 0) {
					gold -= FREEZE_PRICE;
					goldLabel = "GOLD: " + gold;
					freeze = true;
					new Thread(new Runnable() {
						@Override
						public void run() {
							Gdx.app.postRunnable(new Runnable() {
								@Override
								public void run() {

								}
							});

							try {
								Thread.sleep(1500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							freeze = false;
						}
					}).start();
				}
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

	private boolean close(float ax, float ay, int mx, int my) {
		// And distance of point from the center of the circle
		float distance = (float) Math.sqrt(((ax - mx) * (ax - mx))
				+ ((ay - my) * (ay - my)));

        Gdx.app.log("DISTANCE", "Distance: " + distance);


        if (distance <= MAX_METEOR_DISTANCE) {
			return true;
		}
		else {
			return false;
		}
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
