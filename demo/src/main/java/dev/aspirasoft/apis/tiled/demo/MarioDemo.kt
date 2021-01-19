package dev.aspirasoft.apis.tiled.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.aspirasoft.apis.tiled.TiledView
import java.io.BufferedReader
import java.io.InputStreamReader

class MarioDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        loadMapFromAssets("mario.txt")
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
        tilemap.addImage("a", R.drawable.t_mario_a)
        tilemap.addImage("b", R.drawable.t_mario_b)
        tilemap.addImage("c", R.drawable.t_mario_c)
        tilemap.addImage("d", R.drawable.t_mario_d)
        tilemap.addImage("e", R.drawable.t_mario_e)
        tilemap.addImage("f", R.drawable.t_mario_f)
        tilemap.addImage("g", R.drawable.t_mario_g)
        tilemap.addImage("h", R.drawable.t_mario_h)
        tilemap.addImage("i", R.drawable.t_mario_i)
        tilemap.addImage("j", R.drawable.t_mario_j)
        tilemap.addImage("k", R.drawable.t_mario_k)
        tilemap.addImage("l", R.drawable.t_mario_l)
        tilemap.addImage("m", R.drawable.t_mario_m)
        tilemap.addImage("n", R.drawable.t_mario_n)
        tilemap.addImage("o", R.drawable.t_mario_o)
        tilemap.addImage("p", R.drawable.t_mario_p)
        tilemap.addImage("q", R.drawable.t_mario_q)
        tilemap.addImage("r", R.drawable.t_mario_r)
        tilemap.addImage("s", R.drawable.t_mario_s)
        tilemap.addImage("t", R.drawable.t_mario_t)
        tilemap.addImage("u", R.drawable.t_mario_u)
        tilemap.addImage("v", R.drawable.t_mario_v)
        tilemap.addImage("w", R.drawable.t_mario_w)
        tilemap.addImage("x", R.drawable.t_mario_x)
        tilemap.addImage("y", R.drawable.t_mario_y)
        tilemap.addImage("z", R.drawable.t_mario_z)
        tilemap.addImage("aa", R.drawable.t_mario_aa)
        tilemap.addImage("bb", R.drawable.t_mario_bb)
        tilemap.addImage("cc", R.drawable.t_mario_cc)
        tilemap.addImage("dd", R.drawable.t_mario_dd)
        tilemap.addImage("ee", R.drawable.t_mario_ee)
        tilemap.addImage("ff", R.drawable.t_mario_ff)
        tilemap.addImage("gg", R.drawable.t_mario_gg)
        tilemap.addImage("hh", R.drawable.t_mario_hh)
        tilemap.addImage("ii", R.drawable.t_mario_ii)
        tilemap.addImage("jj", R.drawable.t_mario_jj)
        tilemap.addImage("kk", R.drawable.t_mario_kk)
        tilemap.addImage("ll", R.drawable.t_mario_ll)
        tilemap.addImage("mm", R.drawable.t_mario_mm)
        tilemap.addImage("nn", R.drawable.t_mario_nn)
        tilemap.addImage("oo", R.drawable.t_mario_oo)
        tilemap.addImage("pp", R.drawable.t_mario_pp)
        tilemap.addImage("qq", R.drawable.t_mario_qq)
        tilemap.addImage("rr", R.drawable.t_mario_rr)
        tilemap.addImage("ss", R.drawable.t_mario_ss)
        tilemap.addImage("tt", R.drawable.t_mario_tt)
        tilemap.addImage("uu", R.drawable.t_mario_uu)
        tilemap.addImage("vv", R.drawable.t_mario_vv)
        tilemap.addImage("ww", R.drawable.t_mario_ww)
        tilemap.addImage("xx", R.drawable.t_mario_xx)
    }

}