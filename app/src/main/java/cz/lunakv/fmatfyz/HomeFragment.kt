package cz.lunakv.fmatfyz

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging

// Fragment that appears when app is launched. Contains the (un)subscribe button
class HomeFragment : Fragment(), View.OnClickListener{
    private var listener: OnFragmentInteractionListener? = null
    lateinit var fm: FileMan                                    // initialized in onStart()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onClick(v: View?) {
        if (fm.isSubscribed()) {
            disableWorker()
        } else {
            activateWorker()
        }
    }

    private fun activateWorker() {
        FirebaseMessaging.getInstance().subscribeToTopic("morse")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                } else {
                    fm.logSub()
                }
                Toast.makeText(activity!!.baseContext, msg, Toast.LENGTH_SHORT).show()
                updateText()
            }
    }

    private fun disableWorker() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("morse")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_unsubscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_unsubscribe_failed)
                } else {
                    fm.logUnsub()
                }
                Toast.makeText(activity!!.baseContext, msg, Toast.LENGTH_SHORT).show()
                updateText()
            }
    }

    // set text on screen based on whether the user is subscribed or not
    private fun updateText(){
        val view = activity!!.findViewById<TextView>(R.id.worker_info)
        val button = activity!!.findViewById<TextView>(R.id.worker_toggle)
        val ring = activity!!.findViewById<TextView>(R.id.home_ringText)
        view.text = getString(R.string.worker_running_text)
        button.text = getString(R.string.worker_running_button)
        ring.visibility = View.VISIBLE
        if (!fm.isSubscribed()){
            view.text = getString(R.string.worker_stopped_text)
            button.text = getString(R.string.worker_stopped_button)
            ring.visibility = View.INVISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart(){
        super.onStart()
        fm = FileMan(activity!!.applicationContext)
        activity!!.findViewById<Button>(R.id.worker_toggle).setOnClickListener(this)
        updateText()
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
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
