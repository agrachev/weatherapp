package ru.agrachev.core.domain.repository

import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import java.io.OutputStream

interface LocalRepository<Type> {

    val data: Flow<Type?>

    suspend fun updateData(data: Type)

    interface DataTransformer<Holder, Type> {

        suspend fun Holder.readTransform(): Type?

        suspend fun Holder.writeTransform(writeData: Type): Holder
    }

    interface SerializationApi<Holder> {

        fun getDefaultInstance(): Holder

        fun parseFrom(input: InputStream): Holder

        fun writeTo(t: Holder, output: OutputStream)
    }
}
