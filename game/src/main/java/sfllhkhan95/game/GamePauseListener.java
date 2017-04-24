package sfllhkhan95.game;

/**
 * Interface definition for callbacks to be invoked when game is paused or resumed.
 *
 * @author Muhammad Saifullah Khan
 * @version 1.0
 */
public interface GamePauseListener {

    /**
     * Called when the game is paused.
     *
     * @see GameCore
     */
    void onPaused();

    /**
     * Called when the game resumes.
     *
     * @see GameCore
     */
    void onResume();

}
