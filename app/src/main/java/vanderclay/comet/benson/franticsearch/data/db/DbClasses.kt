package vanderclay.comet.benson.franticsearch.data.db

data class Card(
        val id: String?,
        val multiverseId: Int,
        val name: String?,
        val manaCost: String?,
        val convertedManaCost: Double,
        val colors: String?,
        val types: String?,
        val subtypes: String?,
        val rarity: String?,
        val text: String?,
        val power: String?,
        val toughness: String?,
        val imageUrl: String?,
        val reserved: Boolean?,
        val owned: Boolean?
)


