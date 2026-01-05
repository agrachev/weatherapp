package ru.agrachev.core.domain.di

import org.koin.core.definition.KoinDefinition
import ru.agrachev.core.domain.repository.RemoteRepository
import ru.agrachev.core.domain.repository.LocalRepository
import kotlin.reflect.KClass

interface RepositoryDeclarator {

    fun <H, T> declareLocal(
        storageFileName: String,
        serializationApi: LocalRepository.SerializationApi<H>,
        dataTransformer: LocalRepository.DataTransformer<H, T>,
    ): KoinDefinition<LocalRepository<T>>

    fun <Api : Any, Repo : RemoteRepository<Api>> declareRemote(
        baseUrl: String,
        apiClass: KClass<Api>,
        repoFactory: () -> KoinDefinition<Repo>,
    ): KoinDefinition<Repo>
}
