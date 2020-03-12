package cz.lunakv.fmatfyz


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// Shows simple notepad to write down messages in morse
class NotesFragment : Fragment(), View.OnClickListener {
    private var savedText: String? = null             // saves written text during fragment changes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onStart() {
        super.onStart()

        activity!!.findViewById<Button>(R.id.notes_dot).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.notes_dash).setOnClickListener(this)
        activity!!.findViewById<Button>(R.id.notes_slash).setOnClickListener(this)
        activity!!.findViewById<AppCompatImageButton>(R.id.notes_clear).setOnClickListener(this)
        activity!!.findViewById<AppCompatImageButton>(R.id.notes_back).setOnClickListener(this)

        if (savedText != null) {
            activity!!.findViewById<TextView>(R.id.notes_text).text = savedText
        }
    }

    override fun onStop() {
        super.onStop()
        savedText = activity!!.findViewById<TextView>(R.id.notes_text).text.toString()
    }

    override fun onClick(v: View?) {
        val text = activity!!.findViewById<TextView>(R.id.notes_text)
        val value = text.text.toString()
        text.text = when (v!!.id) {
            R.id.notes_dot -> "$value."
            R.id.notes_dash -> "$value-"
            R.id.notes_slash -> "$value/"
            R.id.notes_back -> value.dropLast(1)
            R.id.notes_clear -> ""
            else -> value
        }
    }
}
