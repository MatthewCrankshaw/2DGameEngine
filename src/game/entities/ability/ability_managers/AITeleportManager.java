package game.entities.ability.ability_managers;

import game.InputHandler;
import game.animators.ability_animators.TeleportAnimator;
import game.entities.mob.Enemy;
import game.entities.mob.Mob;
import game.graphics.Screen;
import game.graphics.sprite.AnimatedSprite;
import game.graphics.sprite.Sprite;
import game.levels.Level;

import java.util.Random;

public class AITeleportManager extends AbilityManager{
    private Mob mob;
    private TeleportAnimator teleportAnimator;

    private boolean alreadyTP;

    public AITeleportManager(Screen screen, InputHandler input, Level level, Mob mob){
        super(screen, input, level, 6);
        timeBetweenAnim = (Enemy.ENEMY_TELEPORT_SPEED)/numOfAnim;
        this.mob = mob;
        teleportAnimator = new TeleportAnimator(screen, 6, AnimatedSprite.teleportSprite, Sprite.teleportFloorSign, this, timeBetweenAnim);
        inAnimation = false;
    }

    @Override
    public void castAbility(int x, int y) {
        if (!alreadyTP){
            Random random = new Random();
            random.setSeed(System.nanoTime());
            int xa = (random.nextInt() % 100) + 100;
            int ya = (random.nextInt() % 100) + 100;

            mob.warpLocation(xa, ya);
            alreadyTP = true;
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void renderSprite(double x, double y) {
        teleportAnimator.renderSprite((int)x, (int)y);
    }

    public void reset(){
        teleportAnimator.resetAnimation();
        alreadyTP = false;
    }
}
