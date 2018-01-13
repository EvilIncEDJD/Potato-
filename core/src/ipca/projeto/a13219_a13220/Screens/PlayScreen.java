package ipca.projeto.a13219_a13220.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ipca.projeto.a13219_a13220.HUD.Hud;
import ipca.projeto.a13219_a13220.Outros.Assets;
import ipca.projeto.a13219_a13220.Outros.WorldCreator;
import ipca.projeto.a13219_a13220.Potato;

/**
 * Created by Bruno on 13/01/2018.
 */

public class PlayScreen implements Screen {

    public Potato game;

    public enum State{PAUSA, INGAME,GAMEOVER}
    public Player.State currentState;
    public State state;
    private Stage pauseStage;
    public Player.State previousState;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Vector3 touchPoint;
    private World world;
    private Player player;
    private TextureAtlas atlas;
    private Box2DDebugRenderer b2dr;
    private Rectangle pauseBounds;
    public PlayScreen(Potato game){

        atlas = new TextureAtlas("Sprites.pack");
        this.game = game;
        touchPoint = new Vector3();
        camera = new OrthographicCamera();
        gamePort = new FitViewport(Potato.V_WIDTH / Potato.PPM,Potato.V_HEIGHT / Potato.PPM,camera);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("mapy.tmx");
        state = State.INGAME;
        renderer = new OrthogonalTiledMapRenderer(map,1/ Potato.PPM);
        pauseBounds = new Rectangle((Potato.V_WIDTH - 100)/ Potato.PPM, (Potato.V_HEIGHT -100)/ Potato.PPM, 100/ Potato.PPM, 100/ Potato.PPM);

        camera.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight() /2,0);
        pauseStage = new Stage(new FitViewport(Potato.V_WIDTH,Potato.V_HEIGHT));
        world = new World(new Vector2(0,-9),true);

        player = new Player(world, this);

        b2dr = new Box2DDebugRenderer();

        new WorldCreator(world,map);


    }

    public void HandleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.body.applyLinearImpulse(new Vector2(0,6f),player.body.getWorldCenter(),true);

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x<=2)
            player.body.applyLinearImpulse(new Vector2(1f,0f),player.body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x>=-2)
            player.body.applyLinearImpulse(new Vector2(-1f,0f),player.body.getWorldCenter(),true);

    }
    public void update(float dt) {



        HandleInput(dt);
        switch(state)
        {
            case INGAME:

                player.update(dt);
                world.step(1 / 60f, 6, 2);

                camera.position.x = player.body.getPosition().x;
                camera.position.y = player.body.getPosition().y;
                if (Gdx.input.justTouched()) {
                    camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                    if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                        Assets.playSound(Assets.clickSound);
                        state = State.PAUSA;
                        return;
                    }
                }

                break;
            case PAUSA:

                break;
            case GAMEOVER:
                break;
        }

        camera.update();
        renderer.setView(camera);


    }
    public TextureAtlas getAtlas()
    {
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world,camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.setSize(128/Potato.PPM,128/Potato.PPM);
        player.draw(game.batch);

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(Assets.pauseButton, (Potato.V_WIDTH -100) / Potato.PPM, (Potato.V_HEIGHT -100)/ Potato.PPM, 100/ Potato.PPM, 100/ Potato.PPM);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
