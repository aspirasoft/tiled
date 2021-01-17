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

        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(assets.open("mario.txt"), "UTF-8"));

            // do reading, usually loop until end of file reading
            var map = ""
            var line = reader.readLine()
            while (line != null) {
                map += line + "\n"
                line = reader.readLine()
            }

            val mapData = arrayOf(map)
            arrayOf(R.id.tiledView).withIndex().forEach { (index, id) ->
                val tiledView = findViewById<TiledView>(id)
                createLookupMap().forEach { (id, image) -> tiledView.addImage(id, image) }
                tiledView.post {
                    tiledView.setData(mapData[index])
                }
                tiledView.setOnTileClicked { c: Int, r: Int ->
                    val char = tiledView[c, r]
                    val fruits = listOf("e", "n", "o")
                    if (char in fruits) {
                        tiledView[c, r] = ""
                    }
                }
            }
        } catch (ex: Exception) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (ex: Exception) {
                    //log the exception
                }
            }
        }
    }

    private fun createLookupMap(): HashMap<String, Int> {
        val map = HashMap<String, Int>()
        map["a"] = R.drawable.t_mario_a
        map["b"] = R.drawable.t_mario_b
        map["c"] = R.drawable.t_mario_c
        map["d"] = R.drawable.t_mario_d
        map["e"] = R.drawable.t_mario_e
        map["f"] = R.drawable.t_mario_f
        map["g"] = R.drawable.t_mario_g
        map["h"] = R.drawable.t_mario_h
        map["i"] = R.drawable.t_mario_i
        map["j"] = R.drawable.t_mario_j
        map["k"] = R.drawable.t_mario_k
        map["l"] = R.drawable.t_mario_l
        map["m"] = R.drawable.t_mario_m
        map["n"] = R.drawable.t_mario_n
        map["o"] = R.drawable.t_mario_o
        map["p"] = R.drawable.t_mario_p
        map["q"] = R.drawable.t_mario_q
        map["r"] = R.drawable.t_mario_r
        map["s"] = R.drawable.t_mario_s
        map["t"] = R.drawable.t_mario_t
        map["u"] = R.drawable.t_mario_u
        map["v"] = R.drawable.t_mario_v
        map["w"] = R.drawable.t_mario_w
        map["x"] = R.drawable.t_mario_x
        map["y"] = R.drawable.t_mario_y
        map["z"] = R.drawable.t_mario_z
        map["aa"] = R.drawable.t_mario_aa
        map["bb"] = R.drawable.t_mario_bb
        map["cc"] = R.drawable.t_mario_cc
        map["dd"] = R.drawable.t_mario_dd
        map["ee"] = R.drawable.t_mario_ee
        map["ff"] = R.drawable.t_mario_ff
        map["gg"] = R.drawable.t_mario_gg
        map["hh"] = R.drawable.t_mario_hh
        map["ii"] = R.drawable.t_mario_ii
        map["jj"] = R.drawable.t_mario_jj
        map["kk"] = R.drawable.t_mario_kk
        map["ll"] = R.drawable.t_mario_ll
        map["mm"] = R.drawable.t_mario_mm
        map["nn"] = R.drawable.t_mario_nn
        map["oo"] = R.drawable.t_mario_oo
        map["pp"] = R.drawable.t_mario_pp
        map["qq"] = R.drawable.t_mario_qq
        map["rr"] = R.drawable.t_mario_rr
        map["ss"] = R.drawable.t_mario_ss
        map["tt"] = R.drawable.t_mario_tt
        map["uu"] = R.drawable.t_mario_uu
        map["vv"] = R.drawable.t_mario_vv
        map["ww"] = R.drawable.t_mario_ww
        map["xx"] = R.drawable.t_mario_xx
        return map
    }

}