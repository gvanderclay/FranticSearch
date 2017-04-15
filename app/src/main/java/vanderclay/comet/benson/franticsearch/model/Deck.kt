package vanderclay.comet.benson.franticsearch.model

import io.magicthegathering.javasdk.resource.Card

/**
 * Created by gclay on 4/14/17.
 */

class Deck(name: String) {
    var name = name

    var cards = mutableListOf<Card>()

    // index of the card that will be used for the cover
    var coverCardIndex = 0

    fun addCard(card: Card) {
        cards.add(card)
    }

    fun deleteCard(index: Int): Card {
        return cards.removeAt(index)
    }


}
