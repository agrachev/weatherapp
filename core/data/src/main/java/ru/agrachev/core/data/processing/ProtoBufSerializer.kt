package ru.agrachev.core.data.processing

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import ru.agrachev.core.domain.repository.LocalRepository
import java.io.InputStream
import java.io.OutputStream

internal class ProtoBufSerializer<H>(
    private val api: LocalRepository.SerializationApi<H>,
) : Serializer<H> {

    override val defaultValue = api.getDefaultInstance()

    override suspend fun readFrom(input: InputStream) =
        try {
            api.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: H, output: OutputStream) =
        api.writeTo(t, output)
}
