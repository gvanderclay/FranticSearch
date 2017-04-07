package vanderclay.comet.benson.franticsearch;

import android.support.test.runner.AndroidJUnit4;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.magicthegathering.javasdk.resource.Card;
import vanderclay.comet.benson.franticsearch.commons.DateHandlerKt;
import vanderclay.comet.benson.franticsearch.data.API.MtgAPI;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void onlyGetsCardsAfterDate() throws Exception {
        DateTime dateTime = DateHandlerKt.convertStringToDateTime("2017-01-12");
        List<Card> cards = MtgAPI.Companion.getAllCards(dateTime);
        boolean isCorrectSet = true;
        for(Card card : cards) {


            DateTime cardReleaseDate = DateHandlerKt.convertStringToDateTime(card.getReleaseDate());
            isCorrectSet = cardReleaseDate.isAfter(dateTime);
            if(!isCorrectSet) {
                break;
            }
        }

        Assert.assertTrue(isCorrectSet);
    }
}
