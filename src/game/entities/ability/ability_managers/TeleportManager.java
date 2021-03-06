package game.entities.ability.ability_managers;

import game.Game;
import game.InputHandler;
import game.animators.ability_animators.TeleportAnimator;
import game.entities.mob.Mob;
import game.entities.mob.Player;
import game.graphics.Screen;
import game.graphics.sprite.Sprite;
import game.levels.Level;

/**
 * Created by Matthew.c on 02/02/2017.
 */
public class TeleportManager extends AbilityManager{

    private Mob mob;
    private TeleportAnimator teleportAnimator;

    private boolean alreadyTP;

    public static int MANA_COST = 100;

    public TeleportManager(Screen screen, InputHandler input, Level level, Mob mob, Sprite[] characterSprites, Sprite teleportSprite){
        super(screen, input, level, 6);
        this.timeBetweenAnim = Player.TELEPORT_CAST_SPEED/numOfAnim;
        this.input = input;
        this.mob = mob;
        this.teleportAnimator = new TeleportAnimator(screen, 6, characterSprites, teleportSprite, this, timeBetweenAnim);
        this.inAnimation = false;
    }

    @Override
    public void tick() {
    }

    @Override
    public void renderSprite(double x, double y) {
        teleportAnimator.renderSprite((int)x, (int)y);
    }

    @Override
    public void castAbility(int x, int y) {
        if (!alreadyTP){
            mob.changeLocation(((input.getMouseX() - (screen.getWidth()*screen.getScale())/2)/3) -8 , ((input.getMouseY() - (screen.getHeight()*screen.getScale())/2)/3) - 8);
            alreadyTP = true;
        }
    }

    public void reset(){
        teleportAnimator.resetAnimation();
        alreadyTP = false;
    }
}
