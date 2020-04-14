package vanderclay.comet.benson.franticsearch.vision;

import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;

/**
 * A very simple Processor which receives detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {
    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private ConsecutiveTextMatchesListener listener;
    private ArrayList<String> ocrDetections = new ArrayList<>();
    public OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        mGraphicOverlay = ocrGraphicOverlay;
    }

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        //Make sure we actually have items avaliable for us to detect before we try to draw them.
        if(items.size() != 0) {
            TextBlock item = items.valueAt(0);
            OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
            mGraphicOverlay.add(graphic);
            checkMatches(items.valueAt(0).getValue());
        }
    }

    private void checkMatches(String newText){
        ocrDetections.add(newText);
        if(ocrDetections.size() == 5){
            ocrDetections.remove(0);
        }
        if(listener != null && ocrDetections.size() == 4){
            int flag = 0;
            for(String scannedText : ocrDetections){
                Log.d("V", newText + " : " + scannedText);
                if(!scannedText.equals(newText)){
                    flag ++;
                }
            }
            if(flag < 2) {
                listener.onConsecutiveMatches(newText);
            }
        }
    }

    public void setListener(ConsecutiveTextMatchesListener listener){
        this.listener = listener;
    }

    public interface ConsecutiveTextMatchesListener{
        void onConsecutiveMatches(String newText);
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }
}
