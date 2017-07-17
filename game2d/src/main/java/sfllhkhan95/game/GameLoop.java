package sfllhkhan95.game;

/**
 * GameLoop is the execution loop which is attached with each instance of subclasses of GameCore.
 * Every game runs inside this loop, which updates game, and pauses/stops with the game it is
 * attached with.
 *
 * @author Muhammad Saifullah Khan
 * @version 1.0
 */
class GameLoop extends Thread {

    /**
     * Game for which this GameLoop executes.
     */
    private final GameCore game;

    /**
     * Default constructor.
     *
     * @param game game for which this GameLoop executes
     */
    GameLoop(GameCore game) {
        this.game = game;
    }

    /**
     * Executes game loop, pausing it when game is paused and terminating when game ends.
     */
    @Override
    public void run() {
        long lastUpdate = System.currentTimeMillis();

        while (!game.isOver()) {
            try {
                long elapsed = System.currentTimeMillis() - lastUpdate;
                if (!game.isPaused() && game.getRefreshListener() != null) {
                    game.getRefreshListener().onUpdate(elapsed);
                }
                Thread.sleep(1000 / game.getFPS() - elapsed);
            } catch (InterruptedException ignored) {
                ; // no-op
            } finally {
                lastUpdate = System.currentTimeMillis();
            }
        }
    }
}