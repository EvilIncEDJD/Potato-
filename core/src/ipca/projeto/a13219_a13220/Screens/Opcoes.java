package ipca.projeto.a13219_a13220.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import ipca.projeto.a13219_a13220.Outros.Assets;
import ipca.projeto.a13219_a13220.Potato;

/**
 * Created by Carlos Alves on 14/01/2018.
 */

public class Opcoes extends ScreenAdapter {

    Potato game;
    OrthographicCamera camera;
    Vector3 touchPoint;
    Rectangle backBounds;
    String[] highScores;
    String Score;

    public Opcoes(Potato game)
    {
        this.game = game;
        touchPoint = new Vector3();
        camera = new OrthographicCamera(Potato.V_WIDTH,Potato.V_HEIGHT);
        camera.position.set(Potato.V_WIDTH/2,Potato.V_HEIGHT /2,0);
        backBounds = new Rectangle(0, 0, 64, 64);

    }

    public void update () {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
    }

    public void draw () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(Assets.background, 0, 0, Potato.V_WIDTH, Potato.V_HEIGHT);
        game.batch.draw(Assets.arrow, 0, 0, 64, 64);
        game.batch.end();


    }

    @Override
    public void render (float delta) {
        update();
        draw();
    }
}
