package game;

import game.ai.AiManager;
import game.entities.mob.Player;
import game.graphics.*;

import game.graphics.ui.UserInterface;
import game.levels.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;

    private static Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

    private static int WIDTH = (screensize.width/2)-(screensize.width/15);
    private static int HEIGHT = (screensize.height/2)-(screensize.height/15);
    private static int SCALE;
    private static final String NAME = "Never Lost - Matthew Crankshaw - 14303742";

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    private UserInterface ui;
    private InputHandler input;
    private Level level;
    private Screen screen;
    private Player player;
    private AiManager ai;

    public static void main(String[] args) {
        //Adjust the scale, width and height based off of the monitor user is viewing it on
        if(screensize.width == 1366 && screensize.height == 768) {
            WIDTH = screensize.width;
            HEIGHT = screensize.height;
            SCALE = 1;
        }else if(screensize.width < 1280 && screensize.height < 720){
            WIDTH = screensize.width;
            HEIGHT = screensize.height;
            SCALE = 1;
        }else if(screensize.width < 1920 && screensize.height < 1080){
            WIDTH = screensize.width;
            HEIGHT = screensize.height;
            SCALE = 2;
        }else{
            WIDTH = (screensize.width/2);
            HEIGHT = (screensize.height/2);
            SCALE = 2;
        }

        //Start the game
        new Game().start();
    }

    public Game(){
        setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

        JFrame frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void init(){
        screen = new Screen(WIDTH, HEIGHT, SCALE);
        input = new InputHandler(this);
        level = new Level("/levels/TestingArena.png", screen);
        player = new Player(150, 150, level, screen, input);
        ai = new AiManager(player, level, screen);
        ui = new UserInterface(screen, player, ai);
        input.setUi(ui);
        level.add(player);
    }


    private synchronized void start(){
        new Thread(this).start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D/60;

        int frames = 0;
        int ticks = 0;

        long lastTimer = System.currentTimeMillis();

        double delta = 0;

        init();

        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while(delta >= 1) {
                ticks++;
                if(!input.escape.isPressed()) tick();
                delta -= 1;
            }

            try{
                Thread.sleep(2);
            }

            catch (InterruptedException e ) {
                e.printStackTrace();
            }


            frames++;
            render();


            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                System.out.println(frames + ", " + ticks);
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick(){
        //tickCount++;
        //if paused don't update these
        if(!ui.isGamePaused()) {
            level.tick();
            ai.tick();
        }
        ui.tick();
    }


    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        //Make sure game is not paused
        if(!ui.isGamePaused()){
            ui.setGamePaused(false);
            int playerx = (int) player.getX() - screen.getWidth() / 2;
            int playery = (int) player.getY() - screen.getHeight() / 2;
            level.render(screen, playerx, playery);
        }else{
            ui.setGamePaused(true);
        }
        ui.render();

        for (int i = 0; i < pixels.length; i ++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2 = (Graphics2D)g;
        g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        g2.drawImage(image, 0,0,getWidth(), getHeight(), null);
        g2.dispose();
        bs.show();
    }
}
