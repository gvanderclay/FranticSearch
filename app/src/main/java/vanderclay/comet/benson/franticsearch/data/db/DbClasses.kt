package vanderclay.comet.benson.franticsearch.data.db

data class CardDO(
        val id: String?,
        val name: String?,
        val publisher: String?,
        val manaCost: Float?,
        val convertedManaCost: String?,
        val colors: String?,
        val type: String?,
        val power: String?,
        val toughness: String?,
        val loyalty: String?,
        val imageName: String?,
        val reserved: Boolean?,
        val releaseDate: String?,
        val starter: String?,
        val owned: Boolean?
)


