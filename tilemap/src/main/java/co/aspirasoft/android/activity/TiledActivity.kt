package co.aspirasoft.android.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.aspirasoft.android.view.R
import co.aspirasoft.android.view.TiledView

abstract class TiledActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.TiledActivityTheme)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE // Lock in landscape mode

        val tileImages = createLookupMap(HashMap())
        val view = TiledView(this, tileImages)
        val options = configureViewOptions(view)
        view.inflateWith(options)

        setContentView(view)
    }

    /**
     * Adds items to the lookup [map] for tile images.
     *
     * This abstract method must be implemented to add key-value pairs of
     * character literals pointing to drawable resources which can be used
     * to render a complete tile map from text.
     */
    abstract fun createLookupMap(map: HashMap<Char, Int>): HashMap<Char, Int>

    /**
     * Can be used to (optionally) configure [view] options.
     *
     * @return string containing text to generate tile map.
     */
    abstract fun configureViewOptions(view: TiledView): String

}