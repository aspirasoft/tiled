package sfllhkhan95.game;

/**
 * GameCore should be extended by all games
 *
 * @author Muhammad Saifullah Khan
 * @version 1.0
 */
public abstract class GameCore {

    private final GameLoop gameLoop;
    private final int FPS;
    private GameStartListener startListener;
    private GamePauseListener pauseListener;
    private GameOverListener overListener;
    private FrameRefreshListener refreshListener;
    private boolean isOver = false;
    private boolean isPaused = false;

    protected GameCore() {
        this.FPS = 33;
        this.gameLoop = new GameLoop(this);
    }

    protected GameCore(int FPS) {
        this.FPS = FPS;
        this.gameLoop = new GameLoop(this);
    }

    FrameRefreshListener getRefreshListener() {
        return refreshListener;
    }

    public GameCore setRefreshListener(FrameRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        return this;
    }

    public GameCore setStartListener(GameStartListener startListener) {
        this.startListener = startListener;
        return this;
    }

    public GameCore setOverListener(GameOverListener overListener) {
        this.overListener = overListener;
        return this;
    }

    public GameCore setPauseListener(GamePauseListener pauseListener) {
        this.pauseListener = pauseListener;
        return this;
    }

    public void start() throws RuntimeException {
        isOver = false;
        isPaused = false;

        if (startListener != null) {
            startListener.onStart();
        }

        gameLoop.start();
    }

    public void pause() {
        if (isPaused) return;

        isPaused = true;
        if (pauseListener != null) {
            pauseListener.onPaused();
        }
    }

    public void resume() {
        if (!isPaused) return;

        isPaused = false;
        if (pauseListener != null) {
            pauseListener.onResume();
        }
    }

    public void end() {
        if (isOver) return;

        isOver = true;
        isPaused = false;

        if (overListener != null) {
            overListener.onOver();
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public boolean isPaused() {
        return isPaused;
    }

    int getFPS() {
        return FPS;
    }
}