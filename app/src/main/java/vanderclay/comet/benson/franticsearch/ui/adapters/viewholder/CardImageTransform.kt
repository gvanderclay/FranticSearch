package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.graphics.Bitmap
import com.squareup.picasso.Transformation
import kotlin.math.ceil

class CardImageTransform : Transformation {

    override fun transform(source: Bitmap?): Bitmap {
        val xOffset = ceil(source!!.width * .12).toInt()
        val yOffset = ceil(source.height * .13).toInt()
        val xSize = ceil(source.width * .75).toInt()
        val ySize = ceil(source.height * .4).toInt()

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