package co.aspirasoft.android.demo

import co.aspirasoft.android.activity.TiledActivity
import co.aspirasoft.android.view.TiledView

class TileMapActivity : TiledActivity() {

    override fun createLookupMap(map: HashMap<Char, Int>): HashMap<Char, Int> {
        map['e'] = R.drawable.tl_e_pickup_eggplant
        map['f'] = R.drawable.tl_f_fence
        map['g'] = R.drawable.tl_g_platform_top
        map['a'] = R.drawable.tl_ha_finish_a
        map['b'] = R.drawable.tl_hb_finish_b
        map['c'] = R.drawable.tl_hc_finish_c
        map['d'] = R.drawable.tl_hd_finish_d
        map['i'] = R.drawable.tl_i_pipe
        map['l'] = R.drawable.tl_l_ladder
        map['m'] = R.drawable.tl_m_pipe_bottom
        map['n'] = R.drawable.tl_n_pickup_banana
        map['o'] = R.drawable.tl_o_pickup_tomato
        map['p'] = R.drawable.tl_p_pipe_top
        map['s'] = R.drawable.tl_s_sign
        map['t'] = R.drawable.tl_t_steel
        map['w'] = R.drawable.tl_w_platform

        return map
    }

    override fun configureViewOptions(view: TiledView): String {
        view.isManualScrollEnabled = true
        return getString(R.string.demo_map)
    }

}