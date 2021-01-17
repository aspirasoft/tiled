package dev.aspirasoft.apis.tiled

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TileTest {

    @Test
    fun testOverlapsWithPoint() {
        val tile = Tile(0.0, 0.0, 100.0, 100.0)
        assertTrue(tile.overlapsWith(0.0, 0.0))
        assertTrue(tile.overlapsWith(0.0, 100.0))
        assertTrue(tile.overlapsWith(100.0, 0.0))
        assertTrue(tile.overlapsWith(100.0, 100.0))
        assertTrue(tile.overlapsWith(50.0, 50.0))

        assertFalse(tile.overlapsWith(-0.1, 0.0))
        assertFalse(tile.overlapsWith(0.0, 100.1))
        assertFalse(tile.overlapsWith(0.0, -0.1))
        assertFalse(tile.overlapsWith(100.1, 100.0))
    }

    @Test
    fun testOverlapsWithBox() {
        val smallTile = Tile(50.0, 50.0, 100.0, 50.0)
        assertTrue(smallTile.overlapsWith(000.0, 000.0, 050.1, 050.1)) // top-left
        assertTrue(smallTile.overlapsWith(000.0, 099.9, 050.1, 050.1)) // bottom-left
        assertTrue(smallTile.overlapsWith(149.9, 000.0, 050.0, 050.1)) // top-right
        assertTrue(smallTile.overlapsWith(149.9, 099.9, 050.0, 050.1)) // bottom-right
        assertTrue(smallTile.overlapsWith(075.0, 075.0, 010.0, 010.0)) // inside tile
        assertTrue(smallTile.overlapsWith(000.0, 000.0, 150.0, 100.0)) // tile inside

        assertFalse(smallTile.overlapsWith(000.0, 050.0, 049.9, 050.0)) // on left
        assertFalse(smallTile.overlapsWith(150.1, 050.0, 050.0, 050.0)) // on right
        assertFalse(smallTile.overlapsWith(050.0, 000.0, 100.0, 049.9)) // above
        assertFalse(smallTile.overlapsWith(050.0, 100.1, 100.0, 050.0)) // below
    }
}