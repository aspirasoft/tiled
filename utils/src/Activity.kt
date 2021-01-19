import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.aspirasoft.apis.tiled.TiledView
import java.io.BufferedReader
import java.io.InputStreamReader

class ACTIVITY_NAME : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.LAYOUT_NAME)
        loadMapFromAssets("MAP_DATA_FILE")
    }

    /**
     * Loads map data from a file in assets folder.
     *
     * @param filename Name of the file in assets folder.
     * @throws Exception If the given file could not be opened.
     */
    @Throws(Exception::class)
    private fun loadMapFromAssets(filename: String) {
        val reader = BufferedReader(InputStreamReader(assets.open(filename), "UTF-8"))
        reader.use {
            var data = ""
            var line = reader.readLine()
            while (line != null) {
                data += line + "\n"
                line = reader.readLine()
            }

            onMapDataReady(data)
        }
    }

    /**
     * Receives the map data to finish tilemap setup.
     *
     * @param data The complete tilemap, encoded in a string.
     */
    private fun onMapDataReady(data: String) {
        val tiledView = findViewById<TiledView>(R.id.tiledView)
        createLookupTable(tiledView)
        tiledView.post { tiledView.setData(data) }
        tiledView.setOnTileClicked { column: Int, row: Int ->
            // TODO: Handle clicks on tiles here
        }
    }

    /**
     * Creates a lookup table for the tilemap.
     *
     * A lookup table maps keys to a drawable resources which should be
     * drawn in place of that key.
     *
     * @param tilemap The tilemap view for which to create the mapping.
     */
    private fun createLookupTable(tilemap: TiledView) {
        MAP_LOOKUP_TABLE
    }

}