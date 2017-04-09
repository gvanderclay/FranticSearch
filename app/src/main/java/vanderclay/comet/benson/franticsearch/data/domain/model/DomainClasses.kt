package vanderclay.comet.benson.franticsearch.data.domain.model

/**
 * Created by gclay on 4/7/17.
 */

data class Card (
        val id: String?,
        val name: String?,
        val manaCost: String?,
        val convertedManaCost: Double?,
        val colors: List<String?>,
        val type: String?,
        val subtypes: List<String?>,
        val rarity: String?,
        val text: String?,
        val power: String?,
        val toughness: String?,
        val imageUrl: String?,
        val reserved: Boolean?,
        val owned: Boolean?
)

data class Set(
    val code: String?,
    val name: String?,
    val releaseDate: String?
)