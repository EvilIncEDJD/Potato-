package ipca.projeto.a13219_a13220.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ipca.projeto.a13219_a13220.Potato;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Potato.V_WIDTH ;
		config.height = Potato.V_HEIGHT;
		new LwjglApplication(new Potato(), config);
	}
}
