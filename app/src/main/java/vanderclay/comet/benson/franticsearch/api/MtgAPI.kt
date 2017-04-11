package vanderclay.comet.benson.franticsearch.api

import android.util.Log
import io.magicthegathering.javasdk.api.CardAPI
import io.magicthegathering.javasdk.api.SetAPI
import io.magicthegathering.javasdk.resource.Card
import io.magicthegathering.javasdk.resource.MtgSet
import org.joda.time.DateTime
import vanderclay.comet.benson.franticsearch.commons.convertStringToDateTime

class MtgAPI {

    companion object {
        private val TAG = "MtgAPI"
        /**
         *
         *
         *  CARD API CALLS
         *
         *  - All api calls that are related to cards
         *
         *
         */

        fun getCards(vararg extraFilters: String): List<Card> {
            val cardList = mutableListOf<Card>()
            var page = 1
            val filters = arrayListOf("page=$page", *extraFilters)
            var cardsReceived = CardAPI.getAllCards(filters)
            while(cardsReceived.isNotEmpty()) {
                Log.d(TAG, "Retrieved ${cardsReceived.size} cards")
                Log.d(TAG, "Page ${page}")
                cardList.addAll(cardsReceived)
                filters[0] = "page=${++page}"
                cardsReceived = CardAPI.getAllCards(filters)
            }
            Log.d(TAG, "Finished retrieving ${cardList.size} cards")
            return cardList
        }

        fun getCards(page: Int, vararg extraFilters: String): List<Card> {
            val filters = arrayListOf("page=$page", *extraFilters)
            Log.d(TAG, "Requesting page $page with filters ${extraFilters.joinToString()}")
            return CardAPI.getAllCards(filters)
        }

        fun getAllCards(): List<Card> {
            Log.d(TAG, "Retrieving all cards")
            return getCards()
        }

        /**
         * Get all cards passed date
         * @param after All cards recieved will be released after this date
         */
        fun getAllCards(after: DateTime): List<Card> {
            var cardList = mutableListOf<Card>()
            var sets = getAllSets(after)
            for(set in sets) {
                cardList.addAll(getCardsInSet(set.code))
            }
            return cardList
        }


        /**
         * Search for cards by name
         * @param cardName name of the card
         */
        fun searchForCards(name: String?):List<Card> {
            return getCards("name=$name")
        }

        /**
        *
         */
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

        /**
         *
         *
         * SET API Calls
         *  - All api calls related to sets
         *
         *
         */
        fun getSets(vararg extraFilters: String): List<MtgSet> {
            val setList = mutableListOf<MtgSet>()
            var page = 1
            val filters = arrayListOf("page=$page", *extraFilters)
            var setsReceived = SetAPI.getAllSets(filters)
            while(setsReceived.isNotEmpty()) {
                setList.addAll(setsReceived)
                filters[0] = "page=${++page}"
                setsReceived = SetAPI.getAllSets(filters)
            }
            return setList
        }

        fun getAllSets(): List<MtgSet> {
            return getSets()
        }

        fun getAllSets(after: DateTime?): List<MtgSet> {
            return getAllSets().filter({
                val releaseDate = convertStringToDateTime(it.releaseDate)
                releaseDate?.isAfter(after)!!
            })
        }

    }


}
