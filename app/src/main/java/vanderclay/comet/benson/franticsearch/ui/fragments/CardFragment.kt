package vanderclay.comet.benson.franticsearch.ui.fragments


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.magicthegathering.javasdk.resource.Card
import kotlinx.android.synthetic.main.fragment_card.*
import vanderclay.comet.benson.franticsearch.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardImageTransform

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CardFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardFragment : Fragment(), View.OnClickListener {

    // Reference to the card object the user pressed on
    var card: Card? = null

    // Reference to the firebase authorization object
    private var mAuth: FirebaseAuth? = null

    // Reference to the firebase Auth State Changed listener
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    // Reference to the Firebase Database
    private var mDatabase: DatabaseReference? = null

    /*Reference to the add Button in the view itself*/
    private var addButton: Button? = null

    /*Reference to the favorites buttuon in the view itself*/
    private var favButton: Button? = null

    /*Reference to the card set Text*/
    private var setText: TextView? = null

    /*Reference to the card converted mana cost*/
    private var cmcText: TextView? = null

    /*Reference to the card collector text*/
    private var collectorText: TextView? = null

    /*Reference to the power and toughness*/
    private var ptText: TextView? = null

    /*Reference to the card Image View*/
    private var cardImage: ImageView? = null

    /*Reference to the user currently signed in user*/
    private var user: FirebaseUser? = null

    /*Reference to the ability Text View in the Card Fragment*/
    private var abilityText: TextView? = null

    /*Reference to the Log Tag String for debugging*/
    val TAG: String = "CardFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, " Getting reference to buttons and references to ")
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_card, container, false)
        val cardName = rootView.findViewById(R.id.cardName) as TextView

        addButton = rootView.findViewById(R.id.addButton) as Button
        favButton = rootView.findViewById(R.id.favoriteButton) as Button

        //Set up the on click listeners
        addButton?.setOnClickListener(this)
        favButton?.setOnClickListener(this)

        setText = rootView.findViewById(R.id.cardSetText) as TextView
        cmcText = rootView.findViewById(R.id.cmcText) as TextView
        collectorText = rootView.findViewById(R.id.collectorText) as TextView
        ptText = rootView.findViewById(R.id.ptText) as TextView
        cardImage = rootView.findViewById(R.id.specificCardImage) as ImageView
//        cardImage = view?.findViewById(R.id.imageView) as ImageView
        abilityText = rootView.findViewById(R.id.abilityText) as TextView

        cardName.text = card?.name
        setText?.text = card?.set
        collectorText?.text = card?.number

        loadCardImage()

        if (card?.cmc != null) {
            cmcText?.text = round(card!!.cmc, Integer(2))
        } else {
            cmcText?.text = "0"
        }

        if (card?.power != null && card?.toughness != null) {
            ptText?.text = card?.power + "/" + card?.toughness
        } else {
            cmcText?.text = "0/0"
        }

        if (card?.originalText != null) {
            abilityText?.text = card?.originalText
        } else {
            abilityText?.text = " no ability "
        }

        //Firebase Setup
        this.mAuth = FirebaseAuth.getInstance()
        this.mDatabase = FirebaseDatabase.getInstance().getReference()

        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                //Create a reference to the current user to get access to there UID
                //Used for database insertions and such
                user = firebaseAuth.currentUser
            }
        }

        return rootView
    }

    private fun round(value: Double, places: Integer): String {
        var tempValue = value
        val factor: Long = Math.pow(10.0, places.toDouble()).toLong()
        tempValue *= factor
        var temp: Long = Math.round(tempValue)
        temp = temp / factor
        return temp.toString()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override public fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            val a = getActivity()
            if (a != null) {
            }
        }
    }

    // Bind a card to the ItemCardRow
    fun loadCardImage() {
        Picasso.with(activity)
                .load(this.card?.imageUrl)
                .placeholder(R.drawable.no_card)
                .into(cardImage)
    }

    /*
     * Implementation of the onclick listener in the card Fragment.
     */
    override fun onClick(view: View?) {
        val i = view?.id
        if (i == R.id.addButton) {
            Log.d(TAG, " Add Button Pressed... ")

        } else if (i == R.id.favoriteButton) {

            Log.d(TAG, " favorite Button Pressed ")

        }
    }

    private fun addButtonPressed() {
        if (user != null) {

        } else {
            showSnackBar("Wait a second for us to sign you in")
        }
    }

    private fun showSnackBar(message: String) {
//        @+id/CardFragment
        //Dunno why I had to add an extra line in this version of the snackbar ???
        val snackbar = Snackbar.make(CardFragment.rootView.findViewById(R.id.CardFragment),
                message,
                Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    companion object {
        fun newInstance(card: Card): CardFragment {
            val fragment = CardFragment()
            fragment.card = card
            return fragment
        }
    }
}
