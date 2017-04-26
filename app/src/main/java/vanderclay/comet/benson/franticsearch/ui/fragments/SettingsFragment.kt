package vanderclay.comet.benson.franticsearch.ui.fragments


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cachapa.expandablelayout.ExpandableLayout;

import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.ui.activities.MainActivity


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SettingsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment(), View.OnClickListener{

    private var expandableLayout0: ExpandableLayout? = null
    private var expandableLayout1: ExpandableLayout? = null
    private var TAG = "SETTINGS"

    @Nullable
    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_settings, container, false)

        expandableLayout1 = rootView.findViewById(R.id.expandable_layout_1) as ExpandableLayout
        rootView.findViewById(R.id.expand_button).setOnClickListener(this)

        rootView.findViewById(R.id.logoutBtn).setOnClickListener {
            logout()
            Log.d(TAG, "logout")
        }

        return rootView
    }

    override fun onClick(view: View) {
       if (expandableLayout1!!.isExpanded) {
            expandableLayout1!!.collapse()
        } else {
            expandableLayout1!!.expand()
        }
    }

    private fun logout() {
        (activity as MainActivity).logout()
    }


    companion object {
        fun newInstance(): SettingsFragment {
            val fragment = SettingsFragment()
//            val args = Bundle()
            return fragment
        }
    }

}// Required empty public constructor
