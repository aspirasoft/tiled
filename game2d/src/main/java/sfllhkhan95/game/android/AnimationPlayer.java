package sfllhkhan95.game.android;


import android.graphics.Point;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * AnimationPlayer provides an interface for playing some common animations.
 *
 * @author saifkhichi96
 * @version 1.0
 */
public class AnimationPlayer {

    /**
     * Animates and translates a view from a starting point to an ending point on screen.
     *
     * @param object the view to be translated
     * @param from coordinates of the initial point
     * @param to coordinates of the final point
     * @param duration duration of the translation animation
     * @param callback callback object to be invoked when animation finishes
     */
    public static void translateAnimation(View object, Point from, Point to, int duration, Animation.AnimationListener callback) {
        TranslateAnimation move = new TranslateAnimation(from.x, to.x, from.y, to.y);
        move.setDuration(duration);
        move.setAnimationListener(callback);
        move.setFillAfter(true);
        object.startAnimation(move);
    }

}
