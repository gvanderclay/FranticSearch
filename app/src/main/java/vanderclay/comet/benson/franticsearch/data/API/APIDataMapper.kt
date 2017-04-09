package vanderclay.comet.benson.franticsearch.data.API

import io.magicthegathering.javasdk.api.CardAPI
import vanderclay.comet.benson.franticsearch.data.domain.model.Card
import io.magicthegathering.javasdk.resource.Card as MtgCard

/**
 * Convert data from API to be usable by our application
 * Created by gclay on 4/7/17.
 */

class APIDataMapper {

    fun convertToDomain(card: MtgCard) = with(card) {
        Card(id, name, manaCost, cmc, colors.asList(), type, subtypes.toList(), rarity, text, power, toughness, imageUrl, isReserved, owned = false)
    }

    fun convertToDomain(cards: List<MtgCard>) = cards.map {
        convertToDomain(it)
    }

    fun convertFromDomain(card: Card): MtgCard = with(card) {
        return CardAPI.getCard(card.id?.toInt()!!)
    }

    fun convertFromDomain(cards: List<Card>): List<MtgCard> = cards.map {
        convertFromDomain(it)
    }

}
