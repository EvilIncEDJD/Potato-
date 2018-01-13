package ipca.projeto.a13219_a13220;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ipca.projeto.a13219_a13220.Outros.Assets;
import ipca.projeto.a13219_a13220.Screens.MainMenuScreen;

public class Potato extends Game {
	public static final int V_WIDTH =800;
	public static final int V_HEIGHT = 480;
	public static final float PPM = 100;
	public SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		//Options.load();
		Assets.load();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}