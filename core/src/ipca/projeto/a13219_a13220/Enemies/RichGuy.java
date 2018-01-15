package ipca.projeto.a13219_a13220.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;

import ipca.projeto.a13219_a13220.HUD.Hud;
import ipca.projeto.a13219_a13220.Item.Martelo;
import ipca.projeto.a13219_a13220.Potato;
import ipca.projeto.a13219_a13220.Screens.PlayScreen;

/**
 * Created by Bruno on 14/01/2018.
 */

public class RichGuy extends Enemy {

    private float stateTime;
    public Body body;
    private TextureRegion morto;

    private Animation running;
    private Animation jump;
    private boolean setToDestroy,destroyed;
    private boolean correrdireita;
    public float stateTimer;
    private Array<TextureRegion> frames;
    public RichGuy(PlayScreen screen, float x, float y)
    {
        super(screen, x, y);

        setToDestroy = false;
        frames = new Array<TextureRegion>();
        for(int i = 1; i < 5; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("badguy"), i * 256, 0, 256, 256));
            running = new Animation(0.2f, frames);
        }
        setBounds(getX(), getY(), 128 / Potato.PPM, 128 / Potato.PPM);
        stateTime = 0;

    }
    public void update(float dt) {
        stateTime += dt;
        if(setToDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
            setRegion( new TextureRegion(screen.getAtlas().findRegion("morto"),0,0,128,128));
            stateTime = 0;
        }
        else if(!destroyed) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) running.getKeyFrame(stateTime, true));
        }
    }
    @Override
    public void defineEnemy()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(60/ Potato.PPM);
        fdef.filter.categoryBits = Potato.ENEMY_BIT;
        fdef.filter.maskBits = Potato.GROUND_BIT |
                Potato.ENEMY_BIT | Potato.MARTELO_BIT |
                Potato.PLAYER_BIT;


        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }
    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }
    @Override
    public void hitwithHammer(Martelo martelo) {

        setToDestroy = true;
        Hud.addScore(10);
    }


}
