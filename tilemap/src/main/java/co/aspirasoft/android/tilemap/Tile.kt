package co.aspirasoft.android.tilemap

import android.widget.ImageView

/**
 * A tile in the [TiledView].
 *
 * A tile is a square area displaying a particular image and is used together with
 * many other tileImages to create a tile map. This class is only internally available
 * and cannot be accessed from outside this package.
 *
 * @property x      Horizontal position (in pixels) of the top-left corner of the
 *                  tile on device screen.
 * @property y      Vertical position (in pixels) of the top-left corner of the
 *                  tile on device screen.
 * @property size   Length (in pixels) of a side of the tile.
 */
internal class Tile(private val x: Float, private val y: Float, private val size: Float) {

    var character = ' '

    var view: ImageView? = null

}