package vanderclay.comet.benson.franticsearch.api

import android.util.Log
import io.magicthegathering.javasdk.api.CardAPI
import io.magicthegathering.javasdk.api.SetAPI
import io.magicthegathering.javasdk.resource.Card
import io.magicthegathering.javasdk.resource.MtgSet
import org.jetbrains.anko.doAsyncResult
import org.joda.time.DateTime
import vanderclay.comet.benson.franticsearch.commons.convertStringToDateTime

class MtgApi {

    private val mtgApiTag = "MtgAPI"

    fun getCards(vararg extraFilters: String): List<Card> {
        val cardList = mutableListOf<Card>()
        var page = 1
        val filters = arrayListOf("page=$page", *extraFilters)
        var cardsReceived = CardAPI.getAllCards(filters)
        while (cardsReceived.isNotEmpty()) {
            cardList.addAll(cardsReceived)
            filters[0] = "page=${++page}"
            cardsReceived = CardAPI.getAllCards(filters)
        }
        Log.d(mtgApiTag, "Finished retrieving ${cardList.size} cards")
        return cardList
    }

    fun getCards(page: Int, vararg extraFilters: String): List<Card> {
        val filters = arrayListOf("page=$page", *extraFilters)
        return CardAPI.getAllCards(filters)
    }

    fun getAllCards(): List<Card> {
        Log.d(mtgApiTag, "Retrieving all cards")
        return getCards()
    }

    fun getAllCards(after: DateTime): List<Card> {
        var cardList = mutableListOf<Card>()
        var sets = getAllSets(after)
        for (set in sets) {
            cardList.addAll(getCardsInSet(set.code))
        }
        return cardList
    }

    fun searchForCards(name: String?): List<Card> {
        return getCards("name=$name")
    }

    fun getCard(id: Int): Card = doAsyncResult { CardAPI.getCard(id) }.get()

    fun getCardsInSet(setCode: String): List<Card> {
        return getCards("set=$setCode")
    }

    fun getAllCardTypes(): List<String?> {
        return CardAPI.getAllCardTypes()
    }

    fun getAllCardSuperTypes(): List<String?> {
        return CardAPI.getAllCardSupertypes()
    }

    fun getAllCardSubTypes(): List<String?> {
        return CardAPI.getAllCardSubtypes()
    }

    fun getSets(vararg extraFilters: String): List<MtgSet> {
        val setList = mutableListOf<MtgSet>()
        var page = 1
        val filters = arrayListOf("page=$page", *extraFilters)
        var setsReceived = SetAPI.getAllSets(filters)
        while (setsReceived.isNotEmpty()) {
            setList.addAll(setsReceived)
            filters[0] = "page=${++page}"
            setsReceived = SetAPI.getAllSets(filters)
        }
        return setList
    }

    fun getAllSets(): List<MtgSet> {
        return getSets()
    }

    private fun getAllSets(after: DateTime?): List<MtgSet> {
        return getAllSets().filter {
            val releaseDate = convertStringToDateTime(it.releaseDate)
            releaseDate?.isAfter(after)!!
        }
    }

}
