/*
 * Copyright (c) 2026 Enaium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.enaium.cafemod.serializer

import cn.enaium.cafemod.model.ZipEntry
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class ZipEntrySerializer(
    private val maxDepth: Int
) : JsonSerializer<ZipEntry>() {

    override fun serialize(
        value: ZipEntry,
        gen: JsonGenerator,
        serializers: SerializerProvider
    ) {
        serialize(value, gen, serializers, 0)
    }

    private fun serialize(
        value: ZipEntry,
        gen: JsonGenerator,
        serializers: SerializerProvider,
        depth: Int
    ) {
        gen.writeStartObject()
        gen.writeStringField("name", value.name)
        gen.writeStringField("path", value.path)
        gen.writeStringField("type", value.type.name)

        if (depth < maxDepth) {
            value.parent?.let {
                gen.writeFieldName("parent")
                serialize(it, gen, serializers, depth + 1)
            }

            gen.writeFieldName("children")
            gen.writeStartArray()
            value.children.forEach {
                serialize(it, gen, serializers, depth + 1)
            }
            gen.writeEndArray()
        }

        gen.writeEndObject()
    }
}
