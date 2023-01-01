package tom.gamedemo.worm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.HashSet;

import tom.game.ImageLoader;
import tom.game.ImageSFXs;
import tom.game.SoundClip;
import tom.game.SoundManager;
import tom.game.Sprite;
import tom.game.State;

public class WormState extends State {

    protected Worm player;

    protected HashSet<ComputerWorm> enemies;

    protected HashSet<Sprite> foods;
    protected SoundClip eatSound;
    protected SoundClip bombSound;
    protected SoundClip sound;
    protected int limitedTime = 60;

    protected int numberOfFood;

    protected int numberOfEnemy;

    public WormState(int width, int height, String name) {
        super(width, height, name);
    }

    @Override
    public void loadResource() {
        URL url = ClassLoader.getSystemResource("tom/gamedemo/worm/sand.gif");
        this.setBgImage(ImageSFXs.tileImage(ImageLoader.loadImage(url), this
                .getWidth(), this.getHeight()));
        url = ClassLoader.getSystemResource("tom/gamedemo/worm/alla.mid");
        this.setBgClip(SoundManager.loadSound(url));
        url = ClassLoader.getSystemResource("tom/gamedemo/worm/1up.mid");
        eatSound = SoundManager.loadSound(url);
        url = ClassLoader.getSystemResource("tom/gamedemo/worm/bomb.wav");
        bombSound = SoundManager.loadSound(url);
        enemies = new HashSet<ComputerWorm>();
        foods = new HashSet<Sprite>();
    }

    @Override
    public void init() {
        super.init();
        enemies.clear();
        for (int i = 0; i < numberOfEnemy; i++) {
            ComputerWorm worm = new ComputerWorm(Color.red,
                    this.getWidth() / 2, this.getHeight() / 2, this);
            worm.setLevel(1);
            enemies.add(worm);
        }
        foods.clear();
        for (int i = 0; i < numberOfFood; i++) {
            Sprite food = new Sprite(0, 0, 12, Color.BLACK, this);
            food.randomColor();
            food.randomXY();
            foods.add(food);
        }
        player = new Worm(Color.BLUE, this.getWidth() / 2,
                this.getHeight() / 2, this);
    }

    @Override
    protected void showStatus(Graphics g) {
        g.setColor(Color.BLUE);
        super.showStatus(g);
        g.drawString("Length : ", 5, 60);
        if (player != null) {
            g.drawString(String.valueOf(player.getSize()), 75, 60);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (player != null) {
            player.paint(g);
        }
        for (Sprite food : foods) {
            food.paint(g);
        }
        for (ComputerWorm worm : enemies) {
            worm.paint(g);
        }
    }

    @Override
    public void paintMenu(Graphics g) {
        this.showStatus(g);
    }

    @Override
    public boolean isCleared() {
        return this.getTime() >= limitedTime;
    }

    @Override
    public boolean isOvered() {
        return !player.isActioned();
    }

    @Override
    public void update() {
        super.update();
        for (ComputerWorm worm : enemies) {
            worm.update();
        }
        player.update();
        for (Sprite food : foods) {
            if (player.isCollided(food)) {
                player.eatFood();
                eatSound.play();
                food.randomXY();
            } else {
                for (Worm worm : enemies) {
                    if (worm.isCollided(food)) {
                        worm.eatFood();
                        eatSound.play();
                        food.randomXY();
                    }
                }
            }
        }
        if (this.isOvered()) {
            bombSound.play();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_ESCAPE) {
            if (this.getStatus() == State.CLEAR) {
                this.setNextState("next");
            } else if (this.getStatus() == State.PLAYING) {
                this.setStatus(State.PAUSE);
            } else {
                if (this.getStatus() == State.STOP) {
                    this.init();
                }
                this.setStatus(State.PLAYING);
            }
        } else if (this.getStatus() == State.PLAYING) {
            if (code == KeyEvent.VK_UP) {
                player.setDirection(Worm.MOVE_UP);
            } else if (code == KeyEvent.VK_DOWN) {
                player.setDirection(Worm.MOVE_DOWN);
            } else if (code == KeyEvent.VK_LEFT) {
                player.setDirection(Worm.MOVE_LEFT);
            } else if (code == KeyEvent.VK_RIGHT) {
                player.setDirection(Worm.MOVE_RIGHT);
            }
        }
    }

    public int getLimitedTime() {
        return limitedTime;
    }

    public void setLimitedTime(int limitedTime) {
        this.limitedTime = limitedTime;
    }

    public void setFoods(int numberOfFood) {
        this.numberOfFood = numberOfFood;
    }

    public HashSet<Sprite> getFoods() {
        return foods;
    }

    public void setNumberOfEnemy(int numberOfEnemy) {
        this.numberOfEnemy = numberOfEnemy;
    }

    public HashSet<ComputerWorm> getEnemies() {
        return enemies;
    }
}
