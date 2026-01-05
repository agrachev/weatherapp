package ru.agrachev.core.data.di

import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import retrofit2.Retrofit
import ru.agrachev.core.data.local.DataStoreRepository
import ru.agrachev.core.data.processing.ProtoBufSerializer
import ru.agrachev.core.domain.di.RepositoryDeclarator
import ru.agrachev.core.domain.repository.LocalRepository
import ru.agrachev.core.domain.repository.RemoteRepository
import kotlin.reflect.KClass

internal class CoreDataModuleRepositoryDeclarator(
    private val module: Module,
) : RepositoryDeclarator {

    override fun <H, T> declareLocal(
        storageFileName: String,
        serializationApi: LocalRepository.SerializationApi<H>,
        dataTransformer: LocalRepository.DataTransformer<H, T>
    ): KoinDefinition<LocalRepository<T>> = with(module) {
        single<Serializer<H>> {
            ProtoBufSerializer(serializationApi)
        }
        single {
            dataStore(
                fileName = "${storageFileName}.pb",
                serializer = get<Serializer<H>>(),
            ).getValue(get(), this::id)
        }
        single<LocalRepository<T>> {
            DataStoreRepository(
                dataStore = get(),
                dataTransformer = dataTransformer,
            )
        }
    }

    override fun <Api : Any, Repo : RemoteRepository<Api>> declareRemote(
        baseUrl: String,
        apiClass: KClass<Api>,
        repoFactory: () -> KoinDefinition<Repo>
    ): KoinDefinition<Repo> = with(module) {
        declareRetrofitAndApi(baseUrl, apiClass)
        repoFactory()
    }

    private fun <Api : Any> Module.declareRetrofitAndApi(
        baseUrl: String,
        apiClass: KClass<Api>
    ) {
        val qualifier = named(baseUrl)
        single(qualifier) {
            get<Retrofit>(
                parameters = {
                    parametersOf(baseUrl)
                },
            )
        }
        single {
            get<Retrofit>(qualifier)
                .create(apiClass.java)
        } bind apiClass
    }
}
