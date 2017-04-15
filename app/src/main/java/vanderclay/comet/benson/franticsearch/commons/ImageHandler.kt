package vanderclay.comet.benson.franticsearch.commons

import android.content.Context
import android.view.View
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
        val url = "http://gatherer.wizards.com/Handlers/Image.ashx?size=large&name=$manaType&type=symbol"
        val imageView = ImageView(context)
        imageView.layoutParams = ViewGroup.LayoutParams(50, 50)
        Picasso.with(context)
                .load(url)
                .into(imageView)
        manaContainer?.addView(imageView)
    }
}
