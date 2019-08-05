package co.aspirasoft.android.view


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.support.annotation.UiThread
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import co.aspirasoft.android.tilemap.TileMap


/**
 * TiledView enables you to load a character based tile map from a string.
 *
 * @author saifkhichi96
 * @version 1.0.0
 * @property images Lookup map for tile images.
 */
@SuppressLint("ViewConstructor")
class TiledView(context: Context, private val images: HashMap<Char, Int>) : HorizontalScrollView(context) {

    private var map: TileMap? = null
    private var tileSize: Float = 0f

    val pixelHeight: Float get() = map?.pixelHeight ?: 0f
    val pixelWidth: Float get() = map?.pixelWidth ?: 0f

    var isManualScrollEnabled: Boolean = false
        set(value) {
            field = value
            if (!value) {
                setOnTouchListener { _, _ -> true } // Disable scroll with touch
            } else {
                setOnTouchListener(null) // Enable scroll with touch
            }
        }

    init {
        this.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )

        this.isVerticalScrollBarEnabled = false;
        this.isHorizontalScrollBarEnabled = false;
    }

    @UiThread
    fun inflateWith(string: String): Boolean {
        val lines = string.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (lines.isEmpty()) return false

        val rows = lines.size
        val columns = lines[0].trim().length
        for (line in lines) {
            if (line.isEmpty() || line.trim().length != columns) return false
        }

        this.tileSize = calculateTileSize(rows)
        this.map = TileMap(columns, rows, tileSize)

        val grid = LinearLayout(context)
        grid.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
        grid.orientation = LinearLayout.VERTICAL

        for ((row, text) in lines.withIndex()) {
            val gridRow = LinearLayout(context)
            gridRow.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, tileSize.toInt())
            gridRow.orientation = LinearLayout.HORIZONTAL

            val line = text.trim { it <= ' ' }
            for (col in 0 until line.length) {
                val imageView = ImageView(context)
                imageView.layoutParams = ViewGroup.LayoutParams(tileSize.toInt(), tileSize.toInt())

                val char = line[col]
                val resId = images[char]
                if (resId != null) imageView.setImageResource(resId)

                this.map?.addAt(Pair(col, row), Pair(char, imageView))
                gridRow.addView(imageView)
            }

            grid.addView(gridRow)
        }

        this.removeAllViews()
        this.addView(grid)
        return true
    }

    @UiThread
    fun updateTile(col: Int, row: Int, char: Char) {
        map?.updateAt(Pair(col, row), Pair(char, images[char]))
    }

    /**
     * Column in which horizontal position [x] (in pixels) lies.
     */
    fun getColumn(x: Float): Int {
        return (x / tileSize).toInt()
    }

    /**
     * Row in which vertical position [y] (in pixels) lies.
     */
    fun getRow(y: Float): Int {
        return (y / tileSize).toInt()
    }

    fun getCharAt(col: Int, row: Int): Char? {
        return map?.getTileAt(col, row)?.character
    }

    fun getViewAt(col: Int, row: Int): ImageView? {
        return map?.getTileAt(col, row)?.view

    }

    private fun calculateTileSize(rows: Int): Float {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)

        return (size.y / rows.toFloat())
    }

    override fun toString(): String {
        return map.toString()
    }

}