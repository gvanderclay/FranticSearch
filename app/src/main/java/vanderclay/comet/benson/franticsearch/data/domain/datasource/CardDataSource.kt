package vanderclay.comet.benson.franticsearch.data.domain.datasource

import vanderclay.comet.benson.franticsearch.data.domain.model.Card

/**
 * Created by gclay on 4/7/17.
 */

interface CardDataSource {

    fun requestCardByName(name: String?): Card

    fun requestAllCards(): List<Card>
}