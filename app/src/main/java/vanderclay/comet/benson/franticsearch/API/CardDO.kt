package vanderclay.comet.benson.franticsearch.API

data class CardDO(
        val id: Int?,
        val name: String?,
        val publisher: String?,
        val manaCost: Int?,
        val convertedManaCost: Int?,
        val colors: String?,
        val type: String?,
        val power: Int?,
        val toughness: Int?,
        val loyalty: Int?,
        val imageName: String?,
        val reserved: Boolean?,
        val releaseDate: String?,
        val starter: String?,
        val owned: Boolean?
)


