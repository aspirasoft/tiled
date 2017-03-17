package sfllhkhan95.game;

/**
 * @author Muhammad Saifullah K
 * @version 1.0
 */
public abstract class GameCore {

    private GameOverListener overListener;
    private GameStartListener startListener;
    private GameUpdater updater;

    private boolean isOver = false;
    private boolean isPaused = false;
    private int fps = 33;
    private GameLoop gameLoop = new GameLoop();

    public void start() throws RuntimeException {
        isOver = false;
        isPaused = false;

        if (startListener != null)
            startListener.init();

        gameLoop.start();
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public void end() {
        isOver = true;
        isPaused = false;

        if (overListener != null) {
            overListener.onGameOver();
        }
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public boolean isOver() {
        return isOver;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setOverListener(GameOverListener overListener) {
        this.overListener = overListener;
    }

    public void setUpdater(GameUpdater updater) {
        this.updater = updater;
    }

    public void setStartListener(GameStartListener startListener) {
        this.startListener = startListener;
    }

    private class GameLoop extends Thread {

        @Override
        public void run() {
            long lastUpdate = System.currentTimeMillis();

            while (!isOver) {
                try {
                    long elapsed = System.currentTimeMillis() - lastUpdate;
                    if (!isPaused && updater != null) {
                        updater.update(elapsed);
                    }
                    Thread.sleep(1000 / fps - elapsed);

                } catch (InterruptedException ignored) {

                } finally {
                    lastUpdate = System.currentTimeMillis();
                }
            }
        }
    }
}