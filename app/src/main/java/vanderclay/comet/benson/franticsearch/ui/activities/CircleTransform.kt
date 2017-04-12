package vanderclay.comet.benson.franticsearch.ui.activities

import android.graphics.Bitmap
import com.squareup.picasso.Transformation

/**
 * Created by Gage Vander Clay on 4/11/2017.
 */

class CircleTransform : Transformation {
    private val TAG = "CircleTransform"

    override fun transform(source: Bitmap?): Bitmap {
        val xOffset = Math.ceil(source!!.width * .08).toInt()
        val yOffset = Math.ceil(source.height * .11).toInt()
        val xSize = Math.ceil(source.width * .84).toInt()
        val ySize = Math.ceil(source.height * .44).toInt()

        val squaredBitmap = Bitmap.createBitmap(source, xOffset, yOffset, xSize, ySize)

        if (squaredBitmap != source) {
            source.recycle()
        }

//        val bitMap = Bitmap.createBitmap(xSize, ySize, source.config)
//        val canvas = Canvas(bitMap)
//
//        val paint = Paint()
//
//        val shader = BitmapShader(bitMap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
//        paint.shader = shader
//        paint.isAntiAlias = true
//
//        val r = xSize / 2f
//        Log.d(TAG, "$r")
//
//        canvas.drawCircle(xSize / 2f, ySize / 2f, r, paint)
//
//        squaredBitmap.recycle()


        return squaredBitmap
    }

    override fun key(): String {
        return "circle"
    }
}