package ru.agrachev.core.domain.di

import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.java.KoinJavaComponent
import ru.agrachev.core.domain.repository.LocalRepository
import ru.agrachev.core.domain.repository.RemoteRepository
import kotlin.reflect.KClass

inline fun <reified H, reified T> Module.declareLocalRepository(
    storageFileName: String,
    serializationApi: LocalRepository.SerializationApi<H>,
    dataTransformer: LocalRepository.DataTransformer<H, T>,
) = declareRepositoryDefinition {
    declareLocal(
        storageFileName,
        serializationApi,
        dataTransformer,
    )
}

inline fun <reified Api : Any, reified Repo : RemoteRepository<Api>> Module.declareRemoteRepository(
    baseUrl: String,
    apiClass: KClass<Api>,
    noinline repoFactory: () -> KoinDefinition<Repo>,
) = declareRepositoryDefinition {
    declareRemote(baseUrl, apiClass, repoFactory)
}

inline fun Module.declareRepositoryDefinition(
    crossinline declarator: RepositoryDeclarator.(Module) -> Unit,
) {
    with(sharedScope.get<RepositoryDeclarator>()) {
        this.declarator(this@declareRepositoryDefinition)
    }
}

val sharedScope by lazy {
    KoinJavaComponent.getKoin()
        .getOrCreateScope<RepositoryDeclarator>(
            scopeId = "sharedScope",
        )
}
