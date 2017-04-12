package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.graphics.Bitmap
import com.squareup.picasso.Transformation

/**
 * Created by Gage Vander Clay on 4/11/2017.
 */

class CardImageTransform : Transformation {
    private val TAG = "CardImageTransform"

    override fun transform(source: Bitmap?): Bitmap {
        val xOffset = Math.ceil(source!!.width * .12).toInt()
        val yOffset = Math.ceil(source.height * .13).toInt()
        val xSize = Math.ceil(source.width * .75).toInt()
        val ySize = Math.ceil(source.height * .4).toInt()

        val squaredBitmap = Bitmap.createBitmap(source, xOffset, yOffset, xSize, ySize)

        if (squaredBitmap != source) {
            source.recycle()
        }

        return squaredBitmap
    }

    override fun key(): String {
        return "cardImage"
    }
}