package vanderclay.comet.benson.franticsearch.data.API

import android.util.Log
import io.magicthegathering.javasdk.api.CardAPI
import vanderclay.comet.benson.franticsearch.data.domain.model.Card
import io.magicthegathering.javasdk.resource.Card as MtgCard

/**
 * Convert data from API to be usable by our application
 * Created by gclay on 4/7/17.
 */

class APIDataMapper {
    private val TAG = "APIDataMapper"

    fun convertToDomain(card: MtgCard) = with(card) {
        Log.d(TAG, """Converting
                    name ${card.name}
                    multiverseId ${card.multiverseid}
                    id ${card.id}
                    manaCost ${card.manaCost}
                    cmc ${card.cmc}
                    subtypes ${card.subtypes}
                    colorIdentity ${card.colorIdentity}
                    types ${card.types}
                    subtypes ${card.subtypes}
                    rarity ${card.rarity}
                    text ${card.text}
                    power ${card.power}
                    toughness ${card.toughness}
                    imageURL ${card.imageUrl}
                    reserved ${card.isReserved}

                    """)
        Card(
                id,
                multiverseid,
                name,
                manaCost,
                cmc,
                if(colorIdentity != null) colorIdentity.asList() else listOf(),
                if(types != null) types.asList() else listOf(),
                if(subtypes != null) subtypes.toList() else listOf(),
                rarity,
                text,
                power,
                toughness,
                imageUrl,
                isReserved,
                owned = false
        )
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
