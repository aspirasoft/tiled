package sfllhkhan95.game.android;


import android.graphics.Point;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationPlayer {

    public static void translateAnimation(View object, Point from, Point to, int duration, Animation.AnimationListener callback) {
        TranslateAnimation move = new TranslateAnimation(from.x, to.x, from.y, to.y);
        move.setDuration(duration);
        move.setAnimationListener(callback);
        move.setFillAfter(true);
        object.startAnimation(move);
    }

}
