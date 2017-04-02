package vanderclay.comet.benson.franticsearch.API

import java.io.Serializable

class CardDO(
        //multiverse id of the card, and the primary key of the card itself.
        var id: Int?, //name of the card
        var name: String?, //mana cost of the card itself
        var manaCost: Int?, //converted mana cost of the card
        var cmc: Int?, //color identity of the card
        var colors: String?, //type of the card i.e. human or rogue
        var type: String?, //sub types of the cards
        var power: Int?, //the toughness of the card
        var toughness: Int?, //the loyalty of the card
        var loyalty: Int?, //the image name of the card
        var imageName: String?, //reserved
        var reserved: Boolean?, //Released Date
        var releaseDate: String?, //the starter
        var starter: String?, //do you own the card or not
        var owned: Boolean?) : Serializable {
    constructor() : this()
}

