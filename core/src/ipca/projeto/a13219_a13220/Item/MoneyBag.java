package ipca.projeto.a13219_a13220.Item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ipca.projeto.a13219_a13220.Potato;
import ipca.projeto.a13219_a13220.Screens.PlayScreen;

/**
 * Created by Bruno on 15/01/2018.
 */

public class MoneyBag extends Sprite {
    PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation fireAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;

    Body body;
    public MoneyBag(PlayScreen screen, float x, float y, boolean fireRight){
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        setRegion( new TextureRegion(screen.getAtlas().findRegion("saco"),0,0,128,128));
        setBounds(x, y, 70 / Potato.PPM, 70 / Potato.PPM);

        defineSaco();
    }

    public void defineSaco(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 /Potato.PPM : getX() - 12 /Potato.PPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
            body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30 / Potato.PPM);
        fdef.filter.categoryBits = Potato.SACO_BIT;
        fdef.filter.maskBits = Potato.GROUND_BIT |
                Potato.PLAYER_BIT |
                Potato.OBJECT_BIT;

        fdef.shape = shape;
       // fdef.restitution = 0;
       // fdef.friction = 0;
        body.createFixture(fdef).setUserData(this);
        body.setLinearVelocity(new Vector2(fireRight ? 2 : -2, 4f));
    }

    public void update(float dt){
        stateTime += dt;

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        if((stateTime > 3 || setToDestroy) && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        }
        if(body.getLinearVelocity().y > 4f)
            body.setLinearVelocity(body.getLinearVelocity().x, 2f);
        if((fireRight && body.getLinearVelocity().x < 0) || (!fireRight && body.getLinearVelocity().x > 0))
            setToDestroy();
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }


}
