package sfllhkhan95.game.android.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import sfllhkhan95.game.R;


/**
 *
 */
public class NarratedSlideshowView extends RelativeLayout implements OnTickerCompleteListener {

    private final Handler handler = new Handler();
    private final Activity context;

    private final ArrayList<Integer> slides = new ArrayList<>();
    private final ArrayList<Integer> narrationText = new ArrayList<>();
    private final int SLIDE_DELAY = 1500;

    private OnSlideshowFinishedListener finishedListener;
    private TickerTextView textViewer;
    private ImageSwitcher sliderViewer;
    private ImageView narrator;
    private int currentSlide;
    private boolean INTERRUPTED;

    public NarratedSlideshowView(Context context) {
        super(context);

        this.context = (Activity) context;
        inflateChildren(context);
    }

    public NarratedSlideshowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = (Activity) context;
        inflateChildren(context);
    }

    public NarratedSlideshowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = (Activity) context;
        inflateChildren(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NarratedSlideshowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        this.context = (Activity) context;
        inflateChildren(context);
    }

    private void inflateChildren(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.narrated_slideshow, this);

        this.textViewer = (TickerTextView) findViewById(R.id.narration_text);
        this.textViewer.setCompleteListener(this);
        this.textViewer.setVisibility(GONE);

        this.sliderViewer = (ImageSwitcher) findViewById(R.id.slide_viewer);
        this.sliderViewer.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                return imageView;
            }
        });
        this.sliderViewer.setInAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
        this.sliderViewer.setOutAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
        this.sliderViewer.setAlpha(Float.parseFloat("50.0"));

        this.narrator = (ImageView) findViewById(R.id.narrator);
        this.narrator.setVisibility(GONE);
    }

    @Override
    public void onTickerComplete() {
        if (INTERRUPTED) return;

        if (hasNext()) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    narrateSlide(++currentSlide);
                }
            });
        } else if (finishedListener != null) {
            finishedListener.onSlideshowFinished();
        }
    }

    public void setSpeaker(int drawableId) {
        this.narrator.setImageResource(drawableId);
    }

    public void addSlide(int imageId, int textId) {
        slides.add(imageId);
        narrationText.add(textId);
    }

    public void play() {
        currentSlide = 0;
        textViewer.setVisibility(View.VISIBLE);
        narrator.setVisibility(View.VISIBLE);

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                narrateSlide(currentSlide);
            }
        });
    }

    private void narrateSlide(final int slideId) {
        sliderViewer.setImageResource(slides.get(slideId));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewer.setTickerText(context, narrationText.get(slideId));
            }
        }, SLIDE_DELAY);
    }

    private boolean hasNext() {
        return currentSlide < slides.size() - 1;
    }

    public void stop() {
        INTERRUPTED = true;
    }

    public void setFinishedListener(OnSlideshowFinishedListener finishedListener) {
        this.finishedListener = finishedListener;
    }

    public interface OnSlideshowFinishedListener {

        void onSlideshowFinished();

    }
}