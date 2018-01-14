package ipca.projeto.a13219_a13220.Outros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import ipca.projeto.a13219_a13220.Screens.Options;

/**
 * Created by Carlos Alves on 13/01/2018.
 */

public class Assets {
    public static Texture background,backgroundgame;
    public static Texture playButton;
    public static Texture scoreButton;
    public static Texture scoreButtonclick;
    public static Texture optionsButton;
    public static Texture pauseButton;
    public static Texture arrow, arrowred;
    public static Texture restartButton;
    public static Texture noButton;
    public static Texture soundButton,nosoundButton;
    public static Sound clickSound;
    public static Music music;
    public static BitmapFont font;


    public static void load () {
        background = new Texture("imagens/background.png");
        backgroundgame= new Texture("imagens/backgroundgame.png");
        playButton = new Texture("imagens/playButton.png");
        scoreButton = new Texture("imagens/score.png");
        optionsButton = new Texture("imagens/settings.png");
        //scoreButtonclick =  new Texture("imagens/score2.png");
        pauseButton = new Texture("imagens/pause.png");
        arrowred = new Texture("imagens/play.png");
        arrow = new Texture("imagens/arrow.png");
        restartButton = new Texture("imagens/restart.png");
        soundButton= new Texture("imagens/somON.png");
        nosoundButton = new Texture("imagens/somOFF.png");
        noButton = new Texture("imagens/home.png");
        clickSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Click.wav"));

        font =new BitmapFont(Gdx.files.internal("stuff.fnt"),Gdx.files.internal("stuff.png"),false);

        music = Gdx.audio.newMusic(Gdx.files.internal("Sound/Soviet Union National Anthem.mp3"));

    }

    public static void playSound (Sound sound) {
        if (Options.soundEnabled) sound.play(1);
    }
}
