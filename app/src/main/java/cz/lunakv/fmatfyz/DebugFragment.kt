package cz.lunakv.fmatfyz

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DebugFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */

// Fragment controlling playback speeds from the client. Not available in release versions
class DebugFragment : Fragment(), View.OnClickListener{

    // adjust appropriate time based on which button was clicked
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.up_b_4 -> {
                shortPlayTime += 50
                activity!!.findViewById<TextView>(R.id.short_play_val).text = shortPlayTime.toString()
            }
            R.id.up_b_3 -> {
                longPlayTime += 50
                activity!!.findViewById<TextView>(R.id.long_play_val).text = longPlayTime.toString()
            }
            R.id.up_b_2 -> {
                charPauseTime += 50
                activity!!.findViewById<TextView>(R.id.short_pause_val).text = charPauseTime.toString()
            }
            R.id.up_b_1 -> {
                wordPauseTime += 50
                activity!!.findViewById<TextView>(R.id.long_pause_val).text = wordPauseTime.toString()
            }
            R.id.up_b_sym -> {
                symbolPauseTime += 50
                activity!!.findViewById<TextView>(R.id.symbol_pause_val).text = symbolPauseTime.toString()
            }
            R.id.up_b_warn -> {
                warningPauseTime += 50
                activity!!.findViewById<TextView>(R.id.warning_val).text = warningPauseTime.toString()
            }

            // all times have hard lower bounds set
            R.id.down_b_1 -> {
                wordPauseTime = if (wordPauseTime > 100) { wordPauseTime - 50 } else { 50 }
                activity!!.findViewById<TextView>(R.id.long_pause_val).text = wordPauseTime.toString()
            }
            R.id.down_b_2 -> {
                charPauseTime = if (charPauseTime > 100) { charPauseTime - 50 } else { 50 }
                activity!!.findViewById<TextView>(R.id.short_pause_val).text = charPauseTime.toString()
            }
            R.id.down_b_3 -> {
                longPlayTime = if (longPlayTime > 100) { longPlayTime - 50 } else { 50 }
                activity!!.findViewById<TextView>(R.id.long_play_val).text = longPlayTime.toString()
            }
            R.id.down_b_4 -> {
                shortPlayTime = if (shortPlayTime > 100) { shortPlayTime - 50 } else { 50 }
                activity!!.findViewById<TextView>(R.id.short_play_val).text = shortPlayTime.toString()
            }
            R.id.down_b_sym -> {
                symbolPauseTime = if (symbolPauseTime > 100) { shortPlayTime - 50 } else { 50 }
                activity!!.findViewById<TextView>(R.id.symbol_pause_val).text = symbolPauseTime.toString()
            }
            R.id.down_b_warn -> {
                warningPauseTime = if (warningPauseTime > 400) { warningPauseTime - 50 } else { 350 }
                activity!!.findViewById<TextView>(R.id.warning_val).text = warningPauseTime.toString()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debug, container, false)
    }

    // sets listeners and displays initial values
    override fun onStart() {
        super.onStart()

        activity!!.findViewById<TextView>(R.id.short_play_val).text = shortPlayTime.toString()
        activity!!.findViewById<TextView>(R.id.long_play_val).text = longPlayTime.toString()
        activity!!.findViewById<TextView>(R.id.short_pause_val).text = charPauseTime.toString()
        activity!!.findViewById<TextView>(R.id.long_pause_val).text = wordPauseTime.toString()
        activity!!.findViewById<TextView>(R.id.symbol_pause_val).text = symbolPauseTime.toString()
        activity!!.findViewById<TextView>(R.id.warning_val).text = warningPauseTime.toString()

        activity!!.findViewById<Button>(R.id.up_b_1).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.up_b_2).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.up_b_3).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.up_b_4).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.up_b_sym).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.up_b_warn).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.down_b_1).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.down_b_2).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.down_b_3).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.down_b_4).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.down_b_sym).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.down_b_warn).setOnClickListener(this)
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}
