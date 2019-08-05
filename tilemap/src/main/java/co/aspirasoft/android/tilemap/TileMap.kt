package co.aspirasoft.android.tilemap

import android.widget.ImageView

internal class TileMap(private val columns: Int, private val rows: Int, private val tileSize: Float) {

    private var tiles: Array<Array<Tile?>?> = arrayOfNulls(columns)

    init {
        for (x in 0 until columns) tiles[x] = arrayOfNulls(rows)
    }

    /**
     * Height of the map in pixels.
     */
    val pixelHeight: Float get() = tileSize * rows

    /**
     * Width of the map in pixels.
     */
    val pixelWidth: Float get() = tileSize * columns

    fun addAt(position: Pair<Int, Int>, item: Pair<Char, ImageView>) {
        val col = position.first
        val row = position.second

        val tile = Tile(col * tileSize, row * tileSize, tileSize)
        tile.character = item.first
        tile.view = item.second
        tiles[col]!![row] = tile
    }

    fun updateAt(position: Pair<Int, Int>, item: Pair<Char, Int?>) {
        val col = position.first
        val row = position.second

        val tile = getTileAt(col, row)
        tile?.character = item.first

        if (item.second == null) {
            tile?.view?.setImageBitmap(null)
        } else {
            tile?.view?.setBackgroundResource(item.second!!)
        }
    }

    fun getTileAt(col: Int, row: Int): Tile? {
        return try {
            tiles[col]!![row]
        } catch (ex: Exception) {
            null
        }
    }

    override fun toString(): String {
        val s = StringBuilder()
        for (r in 0 until rows) {
            for (c in 0 until columns)
                s.append(tiles[c]!![r]?.character)

            s.append('\n')
        }
        return s.toString()
    }

}