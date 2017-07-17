package sfllhkhan95.game;

/**
 * GameCore defines the core functionality of a game, and should be subclassed by all games.
 *
 * @author Muhammad Saifullah Khan
 * @version 1.0
 */
public abstract class GameCore {

    /**
     * The GameLoop attached to this game instance.
     */
    private final GameLoop gameLoop;

    /**
     * Refresh rate of the game.
     */
    private final int FPS;

    /**
     * Callback object for game start event.
     */
    private GameStartListener startListener;

    /**
     * Callback object for game pause event.
     */
    private GamePauseListener pauseListener;

    /**
     * Callback object for game over event.
     */
    private GameOverListener overListener;

    /**
     * Callback object for game refresh event.
     */
    private FrameRefreshListener refreshListener;

    /**
     * This flag indicates whether the game is over or not.
     */
    private boolean isOver = false;

    /**
     * This flag indicates whether the game is paused or not.
     */
    private boolean isPaused = false;

    /**
     * No-argument constructor. It creates a game instance with default refresh rate of
     * 33 frames per second.
     */
    protected GameCore() {
        this.FPS = 33;
        this.gameLoop = new GameLoop(this);
    }

    /**
     * This constructor creates a game instance with specified refresh rate.
     *
     * @param FPS refresh rate (in frames per second) of the game
     */
    protected GameCore(int FPS) {
        this.FPS = FPS;
        this.gameLoop = new GameLoop(this);
    }

    /**
     * Getter method for callback object for game refresh event.
     *
     * @return FrameRefreshListener attached to this game instance
     */
    FrameRefreshListener getRefreshListener() {
        return refreshListener;
    }

    /**
     * Attaches a FrameRefreshListener callback object to the game instance which is invoked
     * on every frame refresh.
     *
     * @param refreshListener callback object for game refresh event
     * @return current game instance
     */
    public GameCore setRefreshListener(FrameRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        return this;
    }

    /**
     * Attaches a GameStartListener callback object to the game instance which is invoked
     * when game first starts.
     *
     * @param startListener callback object for game start event
     * @return current game instance
     */
    public GameCore setStartListener(GameStartListener startListener) {
        this.startListener = startListener;
        return this;
    }

    /**
     * Attaches a GameOverListener callback object to the game instance which is invoked once
     * when game ends.
     *
     * @param overListener callback object for game over event
     * @return current game instance
     */
    public GameCore setOverListener(GameOverListener overListener) {
        this.overListener = overListener;
        return this;
    }

    /**
     * Attaches a GamePauseListener callback object to the game instance which is invoked
     * whenever the game is paused/resumed.
     *
     * @param pauseListener callback object for game pause event
     * @return current game instance
     */
    public GameCore setPauseListener(GamePauseListener pauseListener) {
        this.pauseListener = pauseListener;
        return this;
    }

    /**
     * Begins game execution in a new thread after executing the game start callback.
     *
     * @throws RuntimeException exception raised if new thread cannot be created
     */
    public void start() throws RuntimeException {
        isOver = false;
        isPaused = false;

        if (startListener != null) {
            startListener.onStart();
        }

        gameLoop.start();
    }

    /**
     * Pauses game execution and invokes game paused callback. If the game is already paused,
     * this method has no effect.
     */
    public void pause() {
        if (isPaused) return;

        isPaused = true;
        if (pauseListener != null) {
            pauseListener.onPaused();
        }
    }

    /**
     * Resumes game execution and invokes game resumed callback. If the game is already running,
     * this method has no effect.
     */
    public void resume() {
        if (!isPaused) return;

        isPaused = false;
        if (pauseListener != null) {
            pauseListener.onResume();
        }
    }

    /**
     * Terminates the game and invokes game over callback.
     */
    public void end() {
        if (isOver) return;

        isOver = true;
        isPaused = false;

        if (overListener != null) {
            overListener.onOver();
        }
    }

    /**
     * Checks whether the game is over or not.
     *
     * @return true if game is over, false otherwise
     */
    public boolean isOver() {
        return isOver;
    }

    /**
     * Checks whether the game is paused or not.
     *
     * @return true if game paused, false otherwise
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Getter method for game's refresh rate.
     *
     * @return refresh rate in frames per second
     */
    int getFPS() {
        return FPS;
    }
}