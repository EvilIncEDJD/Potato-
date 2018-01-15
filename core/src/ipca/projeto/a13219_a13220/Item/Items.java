package ipca.projeto.a13219_a13220.Item;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import ipca.projeto.a13219_a13220.Potato;
import ipca.projeto.a13219_a13220.Screens.PlayScreen;
import ipca.projeto.a13219_a13220.Sprites.Player;

/**
 * Created by Bruno on 14/01/2018.
 */

public abstract class Items extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected boolean toDestroy;
    protected boolean picked;
    protected Body body;

    public Items(PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        toDestroy = false;
        picked = false;

        setPosition(x, y);

        defineItem();
    }

    public abstract void defineItem();
    public abstract void pick(Player player);

    public void update(float dt){
        if(toDestroy && !picked){
            world.destroyBody(body);
            picked = true;
        }
    }

    public void draw(Batch batch){
        if(!picked)
            super.draw(batch);
    }

    public void setToDestroy(){
        toDestroy = true;
    }



}
