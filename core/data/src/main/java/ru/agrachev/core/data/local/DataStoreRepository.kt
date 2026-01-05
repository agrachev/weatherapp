package ru.agrachev.core.data.local

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import ru.agrachev.core.domain.repository.LocalRepository

internal class DataStoreRepository<H, T>(
    private val dataStore: DataStore<H>,
    private val dataTransformer: LocalRepository.DataTransformer<H, T>
) : LocalRepository<T> {

    override val data = with(dataTransformer) {
        dataStore.data.map {
            it.readTransform()
        }
    }

    override suspend fun updateData(data: T) {
        with(dataTransformer) {
            dataStore.updateData {
                it.writeTransform(data)
            }
        }
    }
}
