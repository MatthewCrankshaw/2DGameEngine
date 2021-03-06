package game.entities.ability;

import game.entities.Entity;
import game.graphics.Screen;
import game.levels.Level;

/**
 * Created by Matthew.c on 01/02/2017.
 */
public abstract class Ability extends Entity {

    protected boolean exploding;
    protected boolean fizzlingOut;
    protected Screen screen;
    protected int damage;

    public Ability(Level level, Screen screen, int damage){
        super(level);
        this.exploding = false;
        this.fizzlingOut = false;
        this.screen = screen;
        this.damage = damage;
    }

    public boolean isExploding(){
        return exploding;
    }

    public boolean isFizzlingOut(){
        return fizzlingOut;
    }

    public int getDamage() {
        return damage;
    }

    public void setExploding() {
        this.exploding = true;
    }

    public abstract void explode();

    public abstract void fizzleOut();


}
