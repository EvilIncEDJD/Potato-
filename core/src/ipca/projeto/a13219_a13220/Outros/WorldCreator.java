package ipca.projeto.a13219_a13220.Outros;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ipca.projeto.a13219_a13220.Enemies.RichGuy;
import ipca.projeto.a13219_a13220.Item.Batata;
import ipca.projeto.a13219_a13220.Potato;
import ipca.projeto.a13219_a13220.Screens.PlayScreen;
import ipca.projeto.a13219_a13220.Sprites.Player;

/**
 * Created by Carlos Alves on 13/01/2018.
 */

public class WorldCreator {

    private Array<RichGuy> badGuy;
    private Array<Batata> batatas;

    Player player;

    public WorldCreator(PlayScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))

        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Potato.PPM, (rect.getY() + rect.getHeight() / 2) / Potato.PPM);
            body = world.createBody(bdef);
            polygonShape.setAsBox(rect.getWidth() / 2 / Potato.PPM, rect.getHeight() / 2 / Potato.PPM);
            fdef.shape = polygonShape;
            fdef.filter.categoryBits = Potato.GROUND_BIT;
            body.createFixture(fdef);
        }
//lava
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))

        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Potato.PPM, (rect.getY() + rect.getHeight() / 2) / Potato.PPM);
            body = world.createBody(bdef);
            polygonShape.setAsBox(rect.getWidth() / 2 / Potato.PPM, rect.getHeight() / 2 / Potato.PPM);
            fdef.shape = polygonShape;
            fdef.filter.categoryBits = Potato.OBJECT_BIT;
            body.createFixture(fdef);
        }


        badGuy = new Array<RichGuy>();
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            badGuy.add(new RichGuy(screen, rect.getX() / Potato.PPM, rect.getY() / Potato.PPM));
        }

        batatas = new Array<Batata>();
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            batatas.add(new Batata(screen, rect.getX() / Potato.PPM, rect.getY() / Potato.PPM));
        }


    }

    public Array<Batata> getBatatas() {
        return batatas;
    }

    public Array<RichGuy> getBadGuy() {
        return badGuy;
    }
}
