package dev.aspirasoft.apis.tiled


import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.UiThread
import java.text.ParseException
import kotlin.math.ceil
import kotlin.math.roundToInt


/**
 * TiledView enables you to load a character based tile map from a string.

 * @property lookupTable Lookup map for tile images.
 *
 * @author saifkhichi96
 * @since 0.0.1
 */
class TiledView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attr: AttributeSet) : this(context, attr, 0)

    private val lookupTable: HashMap<String, Int> = HashMap()

    private lateinit var tileMap: TileMap

    var pixelWidth: Double = 0.0
    var pixelHeight: Double = 0.0

    var isManualScrollEnabled: Boolean = false
        @SuppressLint("ClickableViewAccessibility")
        set(value) {
            field = value
            try {
                if (!value) {
                    getChildAt(0).setOnTouchListener { _, _ -> true } // Disable scroll with touch
                } else {
                    getChildAt(0).setOnTouchListener(null) // Enable scroll with touch
                }
            } catch (ignore: Exception) {

            }
        }

    private var onTileClicked: ((c: Int, r: Int) -> Unit)? = null

    var scrollDirection: Int = 0
        set(value) {
            field = value
            (when (value) {
                0 -> HorizontalScrollView(context)
                1 -> ScrollView(context)
                else -> LinearLayout(context)
            }).apply {
                this.isVerticalScrollBarEnabled = false
                this.isHorizontalScrollBarEnabled = false
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                this@TiledView.removeAllViews()
                this@TiledView.addView(this)
            }
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TiledView,
            0, 0).apply {

            try {
                scrollDirection = getInt(R.styleable.TiledView_orientation, 0)
                isManualScrollEnabled = getBoolean(R.styleable.TiledView_allowManualScroll, false)
            } finally {
                recycle()
            }
        }
    }

    override fun post(action: Runnable?): Boolean {
        return super.post {
            pixelWidth = width.toDouble()
            pixelHeight = height.toDouble()
            action?.run()
        }
    }

    fun addImage(key: String, value: Int) {
        lookupTable[key] = value
    }

    @UiThread
    @Throws(ParseException::class)
    fun setData(data: String) {
        val lines = data.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (lines.isEmpty()) throw ParseException("Cannot construct a map from empty string.", 0)

        val rowCount = lines.size
        val columnCount = lines[0].split('|').dropLastWhile { it.isEmpty() }.size
        for ((i, line) in lines.withIndex()) {
            if (line.isEmpty() || lines[0].split('|').dropLastWhile { it.isEmpty() }.size != columnCount)
                throw ParseException("Input format bad.", i)
        }

        val tileSize = calculateTileSize(rowCount, columnCount)
        this.tileMap = TileMap(columnCount, rowCount, tileSize.first, tileSize.second)

        val grid = LinearLayout(context)
        grid.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        grid.orientation = VERTICAL

        for ((r, text) in lines.withIndex()) {
            val row = LinearLayout(context)
            row.layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                this.tileMap.tileHeight.roundToInt()
            )
            row.orientation = HORIZONTAL

            val line = text.trim()
            for ((c, id) in line.split('|').dropLastWhile { it.isEmpty() }.withIndex()) {
                val tileImage = ImageView(context)
                tileImage.scaleType = ImageView.ScaleType.FIT_XY
                tileImage.layoutParams = ViewGroup.LayoutParams(
                    ceil(this.tileMap.tileWidth).roundToInt(),
                    ceil(this.tileMap.tileHeight).roundToInt(),
                )

                row.addView(tileImage)
                this.tileMap[c, r] = id
                lookupTable[id]?.let { tileImage.setImageResource(it) }

                tileImage.setOnClickListener {
                    onTileClicked?.invoke(c, r)
                }
            }

            grid.addView(row)
        }

        (this.getChildAt(0) as ViewGroup).apply {
            this.removeAllViews()
            this.addView(grid)
        }
    }

    fun setOnTileClicked(listener: ((c: Int, r: Int) -> Unit)) {
        onTileClicked = listener
    }

    /**
     * Sets id and image of a particular tile in the map.
     *
     * @param c The column number of the tile.
     * @param r The row number of the tile.
     * @param id The id value to set.
     * @throws IndexOutOfBoundsException Exception raised if given location is not in the map.
     */
    @UiThread
    @Throws(IllegalArgumentException::class, IndexOutOfBoundsException::class)
    operator fun set(c: Int, r: Int, id: String) {
        this.tileMap[c, r] = id

        val tileImage = getViewAt(c, r)
        val resId = lookupTable[id]
        if (resId != null) tileImage.setImageResource(resId)
        else tileImage.setImageDrawable(null)
    }

    /**
     * Gets id of a particular tile in the map.
     *
     * @param c The column number of the tile.
     * @param r The row number of the tile.
     * @return The id of the tile at the given row and column location.
     * @throws IndexOutOfBoundsException Exception raised if given location is not in the map.
     */
    @Throws(IndexOutOfBoundsException::class)
    operator fun get(c: Int, r: Int): String {
        return tileMap[c, r]
    }

    /**
     * Column in which horizontal position [x] (in pixels) lies.
     */
    fun getColumn(x: Double): Int {
        return (x / tileMap.tileWidth).toInt()
    }

    /**
     * Row in which vertical position [y] (in pixels) lies.
     */
    fun getRow(y: Double): Int {
        return (y / tileMap.tileHeight).toInt()
    }

    private fun calculateTileSize(rowCount: Int, columnCount: Int): Pair<Double, Double> {
        val w = ceil(pixelWidth / columnCount)
        val h = ceil(pixelHeight / rowCount)
        return when (scrollDirection) {
            0 -> Pair(h, h)
            1 -> Pair(w, w)
            else -> Pair(w, h)
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    private fun getViewAt(c: Int, r: Int): ImageView {
        try {
            val layout = getChildAt(0) as ViewGroup
            val grid = layout.getChildAt(0) as ViewGroup
            val row = grid.getChildAt(r) as ViewGroup
            return row.getChildAt(c) as ImageView
        } catch (ex: Exception) {
            throw IndexOutOfBoundsException(ex.message)
        }
    }

}