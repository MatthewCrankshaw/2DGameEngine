package game.entities;

import game.entities.mob.Enemy;
import game.entities.particles.Particles;
import game.graphics.Screen;
import game.graphics.sprite.mob_sprites.PlayerSprite;
import game.levels.Level;

import java.util.ArrayList;

/**
 * Created by Matthew.c on 08/02/2017.
 */
public class Spawner{

    private Screen screen;
    private Level level;

    public enum Type {
        ENEMY_WIZARD, ENEMY_ZOMBIE, PARTICAL
    }

    public Spawner(Level level, Screen screen){
        this.level = level;
        this.screen = screen;
    }

    public void spawnEntities(int x, int y, Type type, int amount){
        for (int i = 0; i < amount; i++) {
            switch (type){
                case PARTICAL:
                    Entity entity = new Particles(level, x, y, 500);
                    level.add(entity);
                    break;
            }
        }
    }

    public ArrayList<Enemy> spawnEnemies(int x, int y, Type type, int amount){
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Enemy e;
            switch (type){
                case ENEMY_WIZARD:
                    e = new Enemy(x, y, level, screen, "Name", 1, PlayerSprite.enemySprites);
                    enemies.add(e);
                    level.add(e);
                    break;
                case ENEMY_ZOMBIE:
                    e = new Enemy(x, y, level, screen, "Name", 1, PlayerSprite.zombieSprites);
                    enemies.add(e);
                    level.add(e);
                    break;
            }
        }
        return enemies;
    }
}
