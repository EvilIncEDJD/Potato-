package ipca.projeto.a13219_a13220.Item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import ipca.projeto.a13219_a13220.HUD.Hud;
import ipca.projeto.a13219_a13220.Potato;
import ipca.projeto.a13219_a13220.Screens.PlayScreen;
import ipca.projeto.a13219_a13220.Sprites.Player;

/**
 * Created by Bruno on 14/01/2018.
 */

public class Batata extends Items
{
    boolean picked;
    boolean setToDestroy;
    private TextureRegion Default;

    public Batata(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        setBounds(getX(), getY(), 32/ Potato.PPM, 32 / Potato.PPM);
        Default = new TextureRegion(screen.getAtlas().findRegion("batata"),0,0,448,720);
        setRegion(Default);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setGravityScale(0);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / Potato.PPM);
        fdef.filter.categoryBits = Potato.ITEM_BIT;
        fdef.filter.maskBits = Potato.PLAYER_BIT |
                Potato.OBJECT_BIT |
                Potato.GROUND_BIT ;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void pick(Player player) {
        setToDestroy();
        Hud.addScore(1);

    }



    @Override
    public void update(float dt) {
        super.update(dt);

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

    }



}
