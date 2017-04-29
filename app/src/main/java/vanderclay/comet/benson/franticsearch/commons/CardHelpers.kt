package vanderclay.comet.benson.franticsearch.commons

import io.magicthegathering.javasdk.resource.Card

fun mapToCard(map: Map<String, *>): Card {
    val card = Card()
    card.id = map["id"] as String
    card.multiverseid = (map["multiverseid"] as String).toInt()
    card.power = map["power"] as String?
    card.toughness = map["toughness"] as String?
    card.manaCost = map["mana"] as String?
    card.name = map["name"] as String?
    card.type = map["type"] as String?
    card.types = (map["types"] as String).split(' ').toTypedArray()
    card.set = map["set"] as String?
    card.rarity = map["rarity"] as String?
    card.imageUrl = map["imageUrl"] as String?
    card.originalText = map["originalText"] as String?
    if(card.imageUrl.isEmpty()){
        card.imageUrl = null
    }

    return card
}

fun cardToMap(card: Card): HashMap<String, String> {
    val map = HashMap<String, String>()
    with(card) {
        map.put("id", id)
        map.put("mana", manaCost)
        map.put("multiverseid", multiverseid.toString())
        map.put("power", power)
        map.put("toughness", toughness)
        map.put("name", name)
        map.put("type", type)
        map.put("types", types.joinToString())
        map.put("set", set)
        map.put("rarity", rarity)
        map.put("imageUrl", imageUrl)
        map.put("originalText", originalText)
    }
    return map
}
