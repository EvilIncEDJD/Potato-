package ipca.projeto.a13219_a13220.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ipca.projeto.a13219_a13220.Enemies.Enemy;
import ipca.projeto.a13219_a13220.Item.Martelo;
import ipca.projeto.a13219_a13220.Potato;
import ipca.projeto.a13219_a13220.Screens.PlayScreen;

/**
 * Created by Bruno on 13/01/2018.
 */

public class Player extends Sprite {

    public enum State{SALTO, CORRER,PARADO, DEAD}
    public State currentState;
    public State previousState;
    public World world;
    public Body body;
    private TextureRegion playerDefault;
    private Animation running;
    private Animation jump;
    private boolean correrdireita;
    private boolean playerisDead;
    public float stateTimer;
    private PlayScreen screen;
    private Array<Martelo> martelos;
    public Player(PlayScreen screen)
    {

        super(screen.getAtlas().findRegion("RunFausto"));
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.PARADO;
        previousState = State.PARADO;
        stateTimer = 0;
        correrdireita = true;
        playerisDead = false;
        Array<TextureRegion> frames = new Array<TextureRegion>();


        for(int i = 1; i < 5; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("RunFausto"), i * 256, 0, 256, 256));
            running = new Animation(0.2f, frames);
        }

        frames.clear();

        DefinePlayer();
        playerDefault = new TextureRegion(screen.getAtlas().findRegion("RunFausto"),0,0,256,256);
        setBounds(0,0,256/Potato.PPM,256/Potato.PPM);

        setRegion(playerDefault);

        martelos = new Array<Martelo>();
    }

    public void update(float dt)
    {
        for(Martelo  hammer : martelos) {
            hammer.update(dt);
            if(hammer.isDestroyed())
                martelos.removeValue(hammer, true);
        }

        setPosition(body.getPosition().x - getWidth()/2,body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));


    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();

        TextureRegion region;

        switch (currentState)
        {

            case CORRER:
                region = (TextureRegion) running.getKeyFrame(stateTimer,true);
                break;
            default:
                region = playerDefault;
                break;
        }
        if((body.getLinearVelocity().x < 0 || !correrdireita) && !region.isFlipX())
        {
            region.flip(true, false);
            correrdireita = false;
        }


        else if((body.getLinearVelocity().x > 0 || correrdireita) && region.isFlipX()){
            region.flip(true, false);
            correrdireita = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;

        return  region;
    }

    public State getState(){
        if(playerisDead)
            return State.DEAD;
        if(body.getLinearVelocity().x != 0)
            return State.CORRER;
        else
            return State.PARADO;
    }
    public void DefinePlayer()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(128*4/ Potato.PPM,128*2/ Potato.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(60/ Potato.PPM);
        fdef.filter.categoryBits = Potato.PLAYER_BIT;
        fdef.filter.maskBits = Potato.GROUND_BIT |
                Potato.ENEMY_BIT |
                Potato.ITEM_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    public void die() {
        playerisDead = true;
    }

    public void hit() {
        die();
    }

    public void AllowJump()
    {
        screen.AllowJump();
    }

    public void fire(){
        martelos.add(new Martelo(screen, body.getPosition().x, body.getPosition().y, correrdireita ? true : false));
    }

    public void draw(Batch batch){
        super.draw(batch);
        for(Martelo hammer : martelos)
            hammer.draw(batch);
    }
}
