package dev.aspirasoft.apis.tiled

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class TileMapTest {

    private val tilemap = TileMap(3, 3, 35.0, 25.0)

    init {
        tilemap[0, 0] = 'a'
        tilemap[0, 1] = 'b'
        tilemap[0, 2] = 'c'
        tilemap[1, 0] = 'd'
        tilemap[1, 1] = 'e'
        tilemap[1, 2] = 'f'
        tilemap[2, 0] = 'g'
        tilemap[2, 1] = 'h'
        tilemap[2, 2] = 'i'
    }

    @Test
    fun testTileMapWidth() {
        assertEquals(105.0, tilemap.pixelWidth)
    }

    @Test
    fun testTileMapHeight() {
        assertEquals(75.0, tilemap.pixelHeight)
    }

    @Test
    fun testTileMapData() {
        assertEquals('a', tilemap[0, 0])
        assertEquals('b', tilemap[0, 1])
        assertEquals('c', tilemap[0, 2])
        assertEquals('d', tilemap[1, 0])
        assertEquals('e', tilemap[1, 1])
        assertEquals('f', tilemap[1, 2])
        assertEquals('g', tilemap[2, 0])
        assertEquals('h', tilemap[2, 1])
        assertEquals('i', tilemap[2, 2])

        assertThrows(IndexOutOfBoundsException::class.java) { tilemap[-1, 0] }
        assertThrows(IndexOutOfBoundsException::class.java) { tilemap[0, -1] }
        assertThrows(IndexOutOfBoundsException::class.java) { tilemap[2, 3] }
        assertThrows(IndexOutOfBoundsException::class.java) { tilemap[3, 2] }
    }
}