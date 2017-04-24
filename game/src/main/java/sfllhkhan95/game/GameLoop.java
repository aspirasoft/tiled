package sfllhkhan95.game;

/**
 * @author Muhammad Saifullah Khan
 * @version 1.0
 */
class GameLoop extends Thread {

    private final GameCore game;

    GameLoop(GameCore game) {
        this.game = game;
    }

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