package vanderclay.comet.benson.franticsearch.ui.fragments


import android.app.ActionBar
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.magicthegathering.javasdk.resource.Card
import kotlinx.android.synthetic.main.fragment_card.*
import vanderclay.comet.benson.franticsearch.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.toolbar.view.*
import vanderclay.comet.benson.franticsearch.commons.addManaSymbols
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardImageTransform
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus
import android.provider.SyncStateContract.Helpers.update
import android.view.Gravity
import android.widget.PopupWindow
import android.databinding.adapters.TextViewBindingAdapter.setText
import android.widget.TextView
import com.android.databinding.library.baseAdapters.BR.deck
import com.google.firebase.database.*
import vanderclay.comet.benson.franticsearch.model.Deck
import java.util.*
import kotlin.collections.HashMap


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
    private var addButton: ImageButton? = null

    /*Reference to the favorites buttuon in the view itself*/
    private var favButton: ImageButton? = null

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

    /*Reference to the shopping cart add Button*/
    private var cardButton: ImageButton? = null

    /*Reference to the user currently signed in user*/
    private var user: FirebaseUser? = null

    /*Reference to the ability Text View in the Card Fragment*/
    private var abilityText: TextView? = null

    private var manaContainer: LinearLayout? = null

    /*Reference to all of the user's current decks. Used in adding a card to deck button*/
    private var decks: Deck? = null

    //Tcg player link
    private val tcgPlayer = "http://shop.tcgplayer.com/magic/product/show?ProductName="

    /*End of the string for tcg player links*/
    private val productType = "newSearch=false&ProductType=All&IsProductNameExact=true"

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
        (activity as AppCompatActivity).supportActionBar?.title = card?.name
        decks = Deck("name")

        addButton = rootView.findViewById(R.id.addButton) as ImageButton
        favButton = rootView.findViewById(R.id.favoriteButton) as ImageButton
        cardButton = rootView.findViewById(R.id.cartButton) as ImageButton

        //Set up the on click listeners
        addButton?.setOnClickListener(this)
        favButton?.setOnClickListener(this)
        cardButton?.setOnClickListener(this)

        setText = rootView.findViewById(R.id.cardSetText) as TextView
        cmcText = rootView.findViewById(R.id.cmcText) as TextView
        collectorText = rootView.findViewById(R.id.collectorText) as TextView
        ptText = rootView.findViewById(R.id.ptText) as TextView
        cardImage = rootView.findViewById(R.id.specificCardImage) as ImageView
        abilityText = rootView.findViewById(R.id.abilityText) as TextView
        manaContainer = rootView.findViewById(R.id.cardManaContainer) as LinearLayout

        setText?.text = card?.set

        if (card?.number != null) {
            collectorText?.text = card?.number
        } else {
            collectorText?.text = "n/a"
        }

        addManaSymbols(card, context, manaContainer)
        loadCardImage()

        if (card?.cmc != null) {
            cmcText?.text = round(card!!.cmc, Integer(2))
        } else {
            cmcText?.text = "0"
        }

        if (card?.power != null && card?.toughness != null) {
            ptText?.text = card?.power + "/" + card?.toughness
        } else {
            ptText?.text = "0/0"
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
            addButtonPressed()
            Log.d(TAG, " Add Button Pressed... ")
        } else if (i == R.id.favoriteButton) {
            Log.d(TAG, " favorite Button Pressed ")
        } else if (i == R.id.cartButton) {
            cartButtonPressed()
        }
    }

    private fun cartButtonPressed() {
        val user = mAuth?.currentUser
        if (user != null) {
            var buyCardIntent = Intent(Intent.ACTION_VIEW)
            buyCardIntent.setData(Uri.parse(tcgPlayer + generateCardUri() + productType))
            startActivity(buyCardIntent)
        } else {
//            showSnackBar("Wait a second for us to sign you in")
        }
    }

    private fun addButtonPressed(){
        var  builderSingle: AlertDialog.Builder  = AlertDialog.Builder(activity)
        builderSingle.setTitle("Choose a Deck to add To")

        var arrayAdapter: ArrayAdapter<String> = ArrayAdapter(activity, android.R.layout.select_dialog_singlechoice)
        var userSelectedDeckToAddCardTo: HashMap<String, String> = HashMap<String, String>()
        getAllDecksForCardFragment(arrayAdapter, userSelectedDeckToAddCardTo)

        builderSingle.setNegativeButton("cancel", object: DialogInterface.OnClickListener {
             override fun onClick(dialog: DialogInterface?, which: Int) {
               dialog?.dismiss()
            }
        })

        builderSingle.setAdapter(arrayAdapter, object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val selection = arrayAdapter.getItem(which)
                val inner: AlertDialog.Builder = AlertDialog.Builder(activity)
                inner.setMessage(selection)
                inner.setTitle("You selected: ")
                addCardToDeck(userSelectedDeckToAddCardTo.get(arrayAdapter.getItem(which))!!,card?.multiverseid.toString() )
                inner.setPositiveButton("Ok!", object: DialogInterface.OnClickListener{
                    override fun onClick(dialog2: DialogInterface?, which: Int) {

                        dialog2?.dismiss()
                    }
                })
                inner.show()
            }
        })
        builderSingle.show()
    }

    fun addCardToDeck(key: String, cardMultiVerseId: String){

        val deckDatabaseRef = FirebaseDatabase
                .getInstance()
                .getReference("Decks")
                .child(FirebaseAuth.getInstance().currentUser?.uid)
                .child(key).push().setValue(cardMultiVerseId)

    }

    //Gets all the decks in a key value pair manner
    fun getAllDecksForCardFragment(arrayAdapter: ArrayAdapter<String>, whatDeckToAddTo: HashMap<String, String>){

        val deckDatabaseRef = FirebaseDatabase
                .getInstance()
                .getReference("Decks")
                .child(FirebaseAuth.getInstance().currentUser?.uid)
        val resultSet = LinkedList<String>()

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    val deckKey: String = snapshot.key
                    val map: HashMap<String, *> = snapshot.value as HashMap<String, *>
                    if(map.containsKey("deckName")){
                        whatDeckToAddTo.put( map.get("deckName") as String, deckKey)
                        arrayAdapter.add(map.get("deckName") as String)
                    }
                }
            }
        }
        deckDatabaseRef.addListenerForSingleValueEvent(valueEventListener)
    }

    private fun generateCardUri(): String {
        //split the string on every space in the anem
        val tokenizedName = card?.name?.split("\\s+")
        var resultString = tokenizedName?.joinToString("+")
        resultString += "&"
        return resultString!!
    }


    companion object {
        fun newInstance(card: Card): CardFragment {
            val fragment = CardFragment()
            fragment.card = card
            return fragment
        }

    }
}
