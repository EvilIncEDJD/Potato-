package ipca.projeto.a13219_a13220.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import ipca.projeto.a13219_a13220.Outros.Assets;
import ipca.projeto.a13219_a13220.Potato;

/**
 * Created by Bruno on 12/01/2018.
 */

public class MainMenuScreen extends ScreenAdapter {
    Potato game;
    OrthographicCamera camera;
    private Viewport gamePort;
    Rectangle soundBounds;
    Rectangle playBounds;
    Rectangle optionsBounds;
    Rectangle highscoresBounds;
    Vector3 touchPoint;


    public MainMenuScreen (Potato game) {
        this.game = game;


        camera = new OrthographicCamera(Potato.V_WIDTH,Potato.V_HEIGHT);
        //  gamePort = new FitViewport(MyGame.V_WIDTH / MyGame.PPM,MyGame.V_HEIGHT / MyGame.PPM,camera);

        camera.position.set(Potato.V_WIDTH/2,Potato.V_HEIGHT /2,0);
        // soundBounds = new Rectangle(0, 0, 64, 64);
        playBounds = new Rectangle(Potato.V_WIDTH / 2 - 50,50, 100,100);
        highscoresBounds = new Rectangle(0, 0, 100, 100);
        optionsBounds = new Rectangle(Potato.V_WIDTH - 100, 0, 100, 100);
        touchPoint = new Vector3();
    }

    public void update ()
    {


        if (Gdx.input.justTouched())
        {
            //game.setScreen(new PlayScreen(game));
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playBounds.contains(touchPoint.x, touchPoint.y))
            {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new PlayScreen(game));
                return;
            }
            if (highscoresBounds.contains(touchPoint.x, touchPoint.y))
            {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new HighScoresScreen(game));
                return;
            }
            if (optionsBounds.contains(touchPoint.x, touchPoint.y))
            {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new Opcoes(game));
                return;
            }


           /* if (soundBounds.contains(touchPoint.x, touchPoint.y))
            {
                Assets.playSound(Assets.clickSound);
                Options.soundEnabled = !Options.soundEnabled;
               if (Options.soundEnabled)
                    Assets.music.play();
                else
                    Assets.music.pause();
            }*/
        }
    }

    public void draw () {


       /* game.batch.enableBlending();
        game.batch.begin();
        game.batch.draw(Assets.logo, 160 - 274 / 2, 480 - 10 - 142, 274, 142);
        game.batch.draw(Assets.mainMenu, 10, 200 - 110 / 2, 300, 110);
        game.batch.draw(Options.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
        game.batch.end();*/
    }

    @Override
    public void render (float delta)
    {
        update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(Assets.background,0,0,Potato.V_WIDTH,Potato.V_HEIGHT);
        game.batch.draw(Assets.playButton,Potato.V_WIDTH / 2 - 50,50, 100,100);
        game.batch.draw(Assets.scoreButton,0,0, 100,100);
        game.batch.draw(Assets.optionsButton,Potato.V_WIDTH -100,0, 100,100);
        game.batch.end();
    }

    @Override
    public void pause () {

    }
}
