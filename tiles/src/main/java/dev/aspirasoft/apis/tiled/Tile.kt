package dev.aspirasoft.apis.tiled

/**
 * Basic building-block of a [TileMap].
 *
 * A tile is a rectangular area, which together with other tiles, makes up a
 * two-dimensional tile map. Each tile is assigned a string-based ID which
 * corresponds to a drawable in a lookup map.
 *
 * @property id     The id of the tile.
 * @property x      Distance (in pixels) of the tile from the left side of the device screen.
 * @property y      Distance (in pixels) of the tile from the top of the device screen.
 * @property width  Width of the tile in pixels.
 * @property height Height of the tile in pixels.
 *
 * @author saifkhichi96
 * @since 0.0.1
 */
class Tile(val x: Double, val y: Double, val width: Double, val height: Double) {

    var id: String = ""

    override fun toString(): String {
        return id
    }

    /**
     * Checks if a point is inside the tile.
     *
     * @param x The x-position of the point (in pixels).
     * @param y The y-position of the point (in pixels).
     */
    fun overlapsWith(x: Double, y: Double): Boolean {
        return x >= this.x && x <= (this.x + width) &&
                y >= this.y && y <= (this.y + height)
    }

    /**
     * Checks if any point of a box is inside the tile.
     *
     * @param x The x-position of the left side of the box (in pixels).
     * @param y The y-position of the top side of the box (in pixels).
     * @param w The width of the box (in pixels).
     * @param h The height of the box (in pixels).
     */
    fun overlapsWith(x: Double, y: Double, w: Double, h: Double): Boolean {
        val l1 = x
        val r1 = x + w
        val t1 = y
        val b1 = y + h

        val l2 = this.x
        val r2 = this.x + this.width
        val t2 = this.y
        val b2 = this.y + this.height

        return (r1 >= l2 && l1 <= r2 &&
                t1 <= b2 && b1 >= t2)
    }
}