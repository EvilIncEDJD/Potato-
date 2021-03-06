package ipca.projeto.a13219_a13220.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Carlos Alves on 14/01/2018.
 */

public class Options {
    public static boolean soundEnabled = true;
    public static int[] highscores = new int[] {0, 0, 0, 0, 0};
    public final static String file = "potato";

    public static void load () {
        try {
            FileHandle filehandle = Gdx.files.external(file);

            String[] strings = filehandle.readString().split("\n");

            soundEnabled = Boolean.parseBoolean(strings[0]);
            for (int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(strings[i+1]);
            }
        } catch (Throwable e) {

        }
    }

    public static void save () {
        try {
            FileHandle filehandle = Gdx.files.external(file);

            filehandle.writeString(Boolean.toString(soundEnabled)+"\n", false);
            for (int i = 0; i < 5; i++) {
                filehandle.writeString(Integer.toString(highscores[i])+"\n", true);
            }
        } catch (Throwable e) {
        }
    }

    public static void addScore (int score) {
        for (int i = 0; i < 5; i++) {
            if (highscores[i] < score) {
                for (int j = 4; j > i; j--)
                    highscores[j] = highscores[j - 1];
                highscores[i] = score;
                break;
            }
        }
    }
}
