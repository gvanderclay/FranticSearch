package vanderclay.comet.benson.franticsearch.ui.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.magicthegathering.javasdk.resource.Card
import kotlinx.android.synthetic.main.fragment_card.*
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.R.layout.activity_main
import vanderclay.comet.benson.franticsearch.ui.activities.MainActivity


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CardFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardFragment : Fragment() {


    var card: Card? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_card, container, false)
        val cardName = rootView.findViewById(R.id.cardName) as TextView
        cardName.text = card?.name
        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
    }

    override fun onDetach() {
        super.onDetach()
    }

    override public fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser){
            val a = getActivity()
            if(a != null) { }
        }
    }

//        @Override
//        public void setUserVisibleHint(boolean isVisibleToUser) {
//            super.setUserVisibleHint(isVisibleToUser);
//            if(isVisibleToUser) {
//                Activity a = getActivity();
//                if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//        }

    companion object {

        fun newInstance(card: Card): CardFragment {
            val fragment = CardFragment()
            fragment.card = card
            return fragment
        }
    }
}
