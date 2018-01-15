package ipca.projeto.a13219_a13220.Outros;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import ipca.projeto.a13219_a13220.Enemies.Enemy;
import ipca.projeto.a13219_a13220.Item.Items;
import ipca.projeto.a13219_a13220.Item.Martelo;
import ipca.projeto.a13219_a13220.Potato;
import ipca.projeto.a13219_a13220.Screens.PlayScreen;
import ipca.projeto.a13219_a13220.Sprites.Player;

/**
 * Created by Bruno on 14/01/2018.
 */

public class ObjectContact implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef)
        {
          /*  case Potato.PLAYER_BIT | Potato.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Potato.PLAYER_BIT)
                    ((Player) fixA.getUserData()).hit();
                else
                    ((Player) fixB.getUserData()).hit();
                break;*/
            case Potato.PLAYER_BIT | Potato.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Potato.PLAYER_BIT)
                    ((Player) fixA.getUserData()).hit();
                else
                    ((Player) fixB.getUserData()).hit();
                break;
            case Potato.ITEM_BIT | Potato.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == Potato.ITEM_BIT)
                    ((Items)fixA.getUserData()).pick((Player) fixB.getUserData());
                else
                    ((Items)fixB.getUserData()).pick((Player) fixA.getUserData());
                break;
            case Potato.ENEMY_BIT | Potato.MARTELO_BIT:
                if(fixA.getFilterData().categoryBits == Potato.ENEMY_BIT) {

                    ((Enemy) fixA.getUserData()).hitwithHammer((Martelo) fixB.getUserData());
                    ((Martelo)fixB.getUserData()).setToDestroy();
                }
                else {
                    ((Enemy) fixB.getUserData()).hitwithHammer((Martelo) fixA.getUserData());
                    ((Martelo)fixA.getUserData()).setToDestroy();
                }
                break;
            case Potato.PLAYER_BIT | Potato.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == Potato.PLAYER_BIT)
                    ((Player) fixA.getUserData()).AllowJump();
                else
                    ((Player) fixB.getUserData()).AllowJump();
                break;



        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
