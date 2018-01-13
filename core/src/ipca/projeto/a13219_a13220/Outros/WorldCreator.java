package ipca.projeto.a13219_a13220.Outros;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import ipca.projeto.a13219_a13220.Potato;

/**
 * Created by Carlos Alves on 13/01/2018.
 */

public class WorldCreator {
    public WorldCreator(World world, TiledMap map)
    {
        BodyDef bdef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        for(int x = 0; x < map.getLayers().getCount(); x++)
        {
            for (MapObject object : map.getLayers().get(x).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / Potato.PPM, (rect.getY() + rect.getHeight() / 2) / Potato.PPM);
                body = world.createBody(bdef);
                polygonShape.setAsBox(rect.getWidth() / 2 / Potato.PPM, rect.getHeight() / 2 / Potato.PPM);
                fdef.shape = polygonShape;
                body.createFixture(fdef);
            }
        }
    }
}
