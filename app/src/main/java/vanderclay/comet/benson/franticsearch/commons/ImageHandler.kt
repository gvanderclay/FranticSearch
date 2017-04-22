package vanderclay.comet.benson.franticsearch.commons

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import io.magicthegathering.javasdk.resource.Card

/**
 * Created by gclay on 4/15/17.
 */

private val reg = Regex("[\\{\\}]")

fun addManaSymbols(card: Card?, context: Context?, manaContainer: LinearLayout?) {
    // our regex includes empty string so filter them out
    if(card?.manaCost == null) {
        return
    }
    val manaSymbols = reg.split(card.manaCost).filter(String::isNotEmpty)
    for (manaType in manaSymbols) {
        loadManaType(manaType, context, manaContainer)
    }
}


fun addManaSymbols(manaSymbols: MutableSet<String>, context: Context?, manaContainer: LinearLayout?) {
   for(manaType in manaSymbols) {
       loadManaType(manaType, context, manaContainer)
   }
}

fun loadManaType(manaType: String, context: Context?, manaContainer: LinearLayout?) {
    val url = getManaUrl(manaType)
    val imageView = ImageView(context)
    imageView.layoutParams = ViewGroup.LayoutParams(50, 50)
    Picasso.with(context)
            .load(url)
            .into(imageView)
    manaContainer?.addView(imageView)
}

fun getManaUrl(manaType: String) = "http://gatherer.wizards.com/Handlers/Image.ashx?size=large&name=$manaType&type=symbol"