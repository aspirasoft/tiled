package sfllhkhan95.game;

/**
 * Interface definition for a callback to be invoked when the game ends.
 *
 * @author Muhammad Saifullah Khan
 * @version 1.0
 */
public interface GameOverListener {

    /**
     * Called when the game ends. i.e. when GameCore.isOver flag is set.
     *
     * @see GameCore
     */
    void onOver();

}
