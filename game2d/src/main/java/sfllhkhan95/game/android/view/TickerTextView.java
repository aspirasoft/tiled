package sfllhkhan95.game.android.view;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Collections;

public class TickerTextView extends AppCompatTextView {

    public TickerType tickerType = TickerType.TICK_BY_CHARACTER;
    private OnTickerCompleteListener completeListener;
    private int delay = 60;
    private int shortPause = 120;
    private int longPause = 150;

    public TickerTextView(Context context) {
        super(context);
    }

    public TickerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TickerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTickerType(TickerType tickerType) {
        this.tickerType = tickerType;
    }

    private ArrayList<String> tokenize(String text) {
        String[] tokens;

        switch (tickerType) {
            case TICK_BY_SENTENCE:
                tokens = text.split(".");
                for (int i = 0; i < tokens.length; i++) {
                    tokens[i] += " ";
                }
                break;
            case TICK_BY_WORD:
                tokens = text.split(" ");
                for (int i = 0; i < tokens.length; i++) {
                    tokens[i] += " ";
                }
                break;
            default:
                char[] chars = text.toCharArray();
                tokens = new String[chars.length];
                for (int i = 0; i < chars.length; i++) {
                    tokens[i] = String.valueOf(chars[i]);
                }
                break;
        }

        ArrayList<String> strings = new ArrayList<>();
        Collections.addAll(strings, tokens);
        return strings;
    }

    public void setTickerText(final Activity activity, final int resid) {
        setText("");
        setTickerText(activity, getResources().getString(resid));
    }

    public void setTickerText(final Activity activity, String text) {
        new AsyncTask<ArrayList<String>, Void, Void>() {
            @Override
            protected Void doInBackground(ArrayList<String>... params) {
                final ArrayList<String> tokens = params[0];

                while (tokens.size() != 0) {
                    final String token = tokens.remove(0);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setText(getText() + token);
                        }
                    });
                    try {
                        if (token.contains(","))
                            Thread.sleep(shortPause);
                        else if (token.contains("."))
                            Thread.sleep(longPause);
                        else
                            Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (completeListener != null) {
                    completeListener.onTickerComplete();
                }
                return null;
            }

        }.execute(tokenize(text));

    }

    public void setCompleteListener(OnTickerCompleteListener completeListener) {
        this.completeListener = completeListener;
    }
}