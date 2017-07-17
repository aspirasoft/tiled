package sfllhkhan95.game;

/**
 * Interface definition for a callback to be invoked when screen is refreshed to
 * update game logic and UI.
 *
 * @author Muhammad Saifullah Khan
 * @version 1.0
 */
public interface FrameRefreshListener {

    /**
     * Called on screen refresh while the game is not yet over and is not paused.
     * Should be used to update game state.
     *
     * @param elapsedTime time (in ms) since last screen refresh
     */
    void onUpdate(long elapsedTime);

}