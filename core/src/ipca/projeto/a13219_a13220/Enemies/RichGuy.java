package ipca.projeto.a13219_a13220.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;

import java.util.Timer;

import ipca.projeto.a13219_a13220.HUD.Hud;
import ipca.projeto.a13219_a13220.Item.Martelo;
import ipca.projeto.a13219_a13220.Item.MoneyBag;
import ipca.projeto.a13219_a13220.Potato;
import ipca.projeto.a13219_a13220.Screens.PlayScreen;
import ipca.projeto.a13219_a13220.Sprites.Player;

/**
 * Created by Bruno on 14/01/2018.
 */

public class RichGuy extends Enemy {

    private float stateTime;
    public Body body;
    private TextureRegion region;
    private float timeSeconds = 0f;
    private float period = 3f;
    private Animation running;
    private Animation jump;
    private boolean setToDestroy,destroyed;
    private boolean correrdireita;
    public float stateTimer;
    private Array<TextureRegion> frames;
    private Array<MoneyBag> moneyBags;
    Player player;
    public RichGuy(PlayScreen screen, float x, float y)
    {
        super(screen, x, y);
        this.player = screen.getPlayer();
        setToDestroy = false;
        correrdireita = true;
        moneyBags = new Array<MoneyBag>();
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

            for(MoneyBag money : moneyBags) {
                money.update(dt);
                if(money.isDestroyed())
                    moneyBags.removeValue(money, true);
            }

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
          region = (TextureRegion) running.getKeyFrame(stateTime, true);

            if(player.body.getPosition().x < body.getPosition().x && region.isFlipX() == false)
            {
                region.flip(true, false);
                correrdireita = false;
                setRegion(region);
            }
            else if(player.body.getPosition().x > body.getPosition().x && region.isFlipX() == true)
            {
                region.flip(true, false);
                correrdireita = true;
                setRegion(region);
            }

            timeSeconds += Gdx.graphics.getRawDeltaTime();
            if(timeSeconds > period){
                timeSeconds-=period;
                fire();
            }
        }


    }
    public void fire(){
        moneyBags.add(new MoneyBag(screen, body.getPosition().x, body.getPosition().y, correrdireita ? true : false));
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
        if (!destroyed) {
            for (MoneyBag money : moneyBags)
                money.draw(batch);
        }
    }



    @Override
    public void hitwithHammer(Martelo martelo) {

        setToDestroy = true;
        Hud.addScore(10);
    }


}
