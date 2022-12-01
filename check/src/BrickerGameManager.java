import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.Ball;
import gameobjects.GraphicLifeCounter;
import gameobjects.NumericLifeCounter;
import gameobjects.Paddle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 35;
    private static final int INITIAL_LIFE = 3;
    private static final float BALL_SPEED = 250;
    private static final Renderable BORDER_RENDERABLE =
            new RectangleRenderable(new Color(80, 140, 250));
    private static final String WIN_MSG = "You won!\n";
    private static final String LOOSE_MSG = "You lost!\n";
    private static final String GO_AT_IT_AGAIN = "would you like to play again?";
    private static final String BALL_IMG_PATH = "assets/ball.png";
    private static final String BALL_SOUND_PATH = "assets/blop_cut_silenced.wav";


    Vector2 winDim;
    Random rand;
    GameObject ball;
    private UserInputListener inputListener;
    private WindowController winController;
    private Counter lifeCounter;


    public BrickerGameManager(String windowTitle, Vector2 windowDim){
        super(windowTitle, windowDim);
        rand = new Random();
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        winDim = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.winController = windowController;
        this.lifeCounter = new Counter(INITIAL_LIFE);
        windowController.setTargetFramerate(80);


        // create ball
        Renderable ballImage = imageReader.readImage(BALL_IMG_PATH, true);
        Sound collisionSound = soundReader.readSound(BALL_SOUND_PATH);
        this.ball = createBall(Vector2.DOWN.mult(BALL_SPEED), ballImage, collisionSound);
        this.gameObjects().addGameObject(ball);

        // create paddle
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        GameObject paddle = new Paddle(Vector2.ZERO,new Vector2(200,20), paddleImage, inputListener, winDim);
        paddle.setCenter(new Vector2(winDim.x()/2, winDim.y()-20));
        this.gameObjects().addGameObject(paddle);

        // create borders
        createBorders(winDim);

        // creates numericLife
        Vector2 numericLifeLoc = new Vector2(20, winDim.y()-30);
        GameObject numericLife = new NumericLifeCounter(lifeCounter,numericLifeLoc,
                new Vector2(20,20),gameObjects());
        this.gameObjects().addGameObject(numericLife, Layer.BACKGROUND+1);

        // create graphicLife
        Renderable heartImg = imageReader.readImage("assets\\heart.png", true);
        Vector2 heartShape = new Vector2(20,20), heartsLocation = numericLifeLoc.add(new Vector2(-20,-50));
        GameObject graphicLifeCounter = new GraphicLifeCounter(heartsLocation,heartShape,
                lifeCounter, heartImg, gameObjects(), 3);
        gameObjects().addGameObject(graphicLifeCounter, Layer.BACKGROUND+1);

        // creates background
        createGameObjectBackgraound(imageReader, windowController, "assets/DARK_BG2_small.jpeg");

        // creates bricks

    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();

    }

    /**
     * check if game ended in win or loose Or reduction of HP is needed
     * operate accordingly - remove HP or prompt won/loss massage
     */
    private void checkForGameEnd() {
        String msg = "";
        if(inputListener.isKeyPressed(KeyEvent.VK_K)){
            msg += WIN_MSG;
        }
        if(ball.getCenter().y() > winDim.y()){
            if(!removeHP()){ // if no more lives:
                msg += LOOSE_MSG;
            }
        }
        if(!msg.equals("")){
            msg += GO_AT_IT_AGAIN;
            if(winController.openYesNoDialog(msg)){
                winController.resetGame();
            }
            else winController.closeWindow();
        }
    }

    /**
     * remove 1 HP from lifeCounter
     * @return true upon success, false for failure (0 hp)
     */
    private boolean removeHP() {
        lifeCounter.decrement();
        ball.setCenter(winDim.mult(0.5f));
        return lifeCounter.value() != 0;
    }


    /**
     *
     * @param velocity
     * @param ballImage
     * @param collisionSound
     * @return
     */
    private GameObject createBall(Vector2 velocity, Renderable ballImage,  Sound collisionSound) {
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(20,20), ballImage, collisionSound);
        ball.setVelocity(velocity);
        ball.setCenter(winDim.mult(0.5f));

        float velocity_X = BALL_SPEED, velocity_Y  = BALL_SPEED;
        if (rand.nextBoolean())
            velocity_X*=-1;
        if (rand.nextBoolean())
            velocity_Y*=-1;

        ball.setVelocity(new Vector2(velocity_X, velocity_Y));

        return ball;
    }

    private void createBorders(Vector2 windowDimensions) {
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        BORDER_RENDERABLE)
        );
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(windowDimensions.x()-BORDER_WIDTH, 0),
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        BORDER_RENDERABLE)
        );
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(Vector2.ZERO),
                        new Vector2(windowDimensions.x(), BORDER_WIDTH),
                        BORDER_RENDERABLE)
        );
    }

    /*
    straight forwards - from a path ,open image and add it to the background as an image
     */
    private void createGameObjectBackgraound(ImageReader imageReader, WindowController windowController, String path) {
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                imageReader.readImage(path,false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }
}
