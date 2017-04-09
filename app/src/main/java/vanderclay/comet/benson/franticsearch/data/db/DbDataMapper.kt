package vanderclay.comet.benson.franticsearch.data.db

import io.magicthegathering.javasdk.api.CardAPI
import vanderclay.comet.benson.franticsearch.data.domain.model.Card
import vanderclay.comet.benson.franticsearch.data.db.Card as DBCard

/**
 * This class is used to convert data from the database to a format
 * usable by the application
 * Created by gclay on 4/5/17.
 */

class DbDataMapper {

    fun convertFromDomain(card: Card): DBCard = with(card) {
        return DBCard(id, name, manaCost, convertedManaCost, colors.joinToString(), type, subtypes.joinToString(), rarity, text, power, toughness, imageUrl, reserved, owned)
    }

    fun convertFromDomain(cards: List<Card>): List<DBCard> = cards.map {
        convertFromDomain(it)
    }

    fun convertToDomain(dbCard: DBCard): Card = with(dbCard) {
        return Card(id, name, manaCost, convertedManaCost, colors?.split(',')!!, type, subtypes?.split(',')!!, rarity, text, power, toughness, imageUrl, reserved, owned)
    }

    fun convertToDomain(dbCards: List<DBCard>): List<Card> = dbCards.map {
        convertToDomain(it)
    }



}
