package vanderclay.comet.benson.franticsearch.data.domain

/**
 * Created by gclay on 4/7/17.
 */

data class Card(
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

data class Set(
    val code: String?,
    val name: String?,
    val releaseDate: String?
)