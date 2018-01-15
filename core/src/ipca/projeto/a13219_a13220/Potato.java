package ipca.projeto.a13219_a13220;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ipca.projeto.a13219_a13220.Outros.Assets;
import ipca.projeto.a13219_a13220.Screens.MainMenuScreen;
import ipca.projeto.a13219_a13220.Screens.Options;

public class Potato extends Game {
	public static final int V_WIDTH =1280;
	public static final int V_HEIGHT = 720;
	public static final float PPM = 200;
	public SpriteBatch batch;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short ITEM_BIT= 8;
	public static final short OBJECT_BIT = 16;
	public static final short MARTELO_BIT  = 32;
	public static final short SACO_BIT  = 64;


	@Override
	public void create () {
		batch = new SpriteBatch();
		Options.load();
		Assets.load();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}