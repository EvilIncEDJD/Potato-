package ipca.projeto.a13219_a13220.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import ipca.projeto.a13219_a13220.HUD.Hud;
import ipca.projeto.a13219_a13220.Outros.Assets;
import ipca.projeto.a13219_a13220.Potato;

/**
 * Created by Bruno on 14/01/2018.
 */

public class GameOverScreen extends ScreenAdapter{
    Potato game;
    OrthographicCamera camera;
    Vector3 touchPoint;
    Rectangle backBounds;
    private Rectangle restartBounds;
    String score, gameOver, scoreText;


    public GameOverScreen(Potato game) {
        this.game = game;
        restartBounds = new Rectangle(100, 100, 100, 100);

        camera = new OrthographicCamera(Potato.V_WIDTH, Potato.V_HEIGHT);
        camera.position.set(Potato.V_WIDTH / 2, Potato.V_HEIGHT / 2, 0);
        backBounds = new Rectangle(Potato.V_WIDTH -200, 100, 100, 100);
        touchPoint = new Vector3();
        gameOver = new String("G A M E  O V E R");
        scoreText = new String("SCORE");


    }
    public void update () {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
                return;
            }

            if (restartBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new PlayScreen(game));
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
        game.batch.draw(Assets.restartButton,100, 100, 100, 100);

        Assets.font.draw(game.batch,gameOver,Potato.V_WIDTH/2-70 ,Potato.V_HEIGHT-100);

                score = Integer.toString(Hud.score);
            Assets.font.draw(game.batch, score,Potato.V_WIDTH/2,Potato.V_HEIGHT/2);
        Assets.font.draw(game.batch, scoreText,Potato.V_WIDTH/2 -20,Potato.V_HEIGHT/2 +100);


        game.batch.draw(Assets.noButton, Potato.V_WIDTH -200, 100, 100, 100);
        game.batch.end();


    }

    @Override
    public void render (float delta) {
        update();
        draw();
    }
}



