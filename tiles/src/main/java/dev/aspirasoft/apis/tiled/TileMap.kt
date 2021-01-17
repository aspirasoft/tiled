package dev.aspirasoft.apis.tiled

/**
 * A two-dimensional grid of tiled images.
 *
 * @author saifkhichi96
 * @since 0.0.1
 */
class TileMap(
    private val columnCount: Int,
    private val rowCount: Int,
    val tileWidth: Double,
    val tileHeight: Double,
) {

    private val grid = ArrayList<ArrayList<Tile>>()

    init {
        for (c in 0 until columnCount) {
            val row = arrayListOf<Tile>()
            for (r in 0 until rowCount) {
                val tile = Tile(c * tileWidth, r * tileHeight, tileWidth, tileHeight)
                row.add(tile)
            }
            grid.add(row)
        }
    }

    /**
     * Width of the map in pixels.
     */
    val pixelWidth: Double = tileWidth * columnCount

    /**
     * Height of the map in pixels.
     */
    val pixelHeight: Double = tileHeight * rowCount

    /**
     * Sets id of a particular tile in the map.
     *
     * @param c The column number of the tile.
     * @param r The row number of the tile.
     * @param id The id value to set.
     * @throws IndexOutOfBoundsException Exception raised if given location is not in the map.
     */
    @Throws(IndexOutOfBoundsException::class)
    operator fun set(c: Int, r: Int, id: String) {
        grid[c][r].id = id
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
        return grid[c][r].id
    }

    /**
     * Gets a particular tile in the map.
     *
     * @param c The column number of the tile.
     * @param r The row number of the tile.
     * @return The tile at the given row and column location.
     * @throws IndexOutOfBoundsException Exception raised if given location is not in the map.
     */
    @Throws(IndexOutOfBoundsException::class)
    fun getTile(c: Int, r: Int): Tile {
        return grid[c][r]
    }

    override fun toString(): String {
        val s = StringBuilder()
        for (c in 0 until columnCount) {
            for (r in 0 until rowCount) {
                s.append(grid[c][r].id)
            }
            s.append('\n')
        }
        return s.toString()
    }

}