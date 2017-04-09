package vanderclay.comet.benson.franticsearch.data.API

import vanderclay.comet.benson.franticsearch.data.db.CardDB
import vanderclay.comet.benson.franticsearch.data.domain.datasource.CardDataSource
import vanderclay.comet.benson.franticsearch.data.domain.model.Card
import java.lang.UnsupportedOperationException

/**
 * Created by gclay on 4/7/17.
 */

class CardAPI(val dataMapper: APIDataMapper = APIDataMapper(),
              val cardDB: CardDB = CardDB()): CardDataSource {

    override fun requestCardByName(name: String?) = throw UnsupportedOperationException()

    override fun requestAllCards(): List<Card> {
        val apiCards = MtgAPI.getAllCards()
        val converted = dataMapper.convertToDomain(apiCards)
        cardDB.saveCards(converted)
        return cardDB.requestAllCards()
    }

}