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

package cn.enaium.cafemod

import cn.enaium.cafemod.impl.*
import cn.enaium.cafemod.model.Field
import cn.enaium.cafemod.model.Method
import cn.enaium.cafemod.model.Rpc
import cn.enaium.cafemod.model.ZipEntry
import cn.enaium.cafemod.model.instruction.InstructionNode
import cn.enaium.cafemod.serializer.ZipEntrySerializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Enaium
 */
fun main() {
    val reader = System.`in`.bufferedReader()
    val writer = System.out.bufferedWriter()

    val methods = mutableMapOf<String, suspend (params: Array<Any>?) -> Rpc>()

    var cafemod: Cafemod? = null

    suspend fun loadFiles(path: String): ZipEntry {
        cafemod = Cafemod(path)
        cafemod.load()
        return cafemod.findEntry("") ?: throw RuntimeException("Load Failed")
    }

    fun findEntry(path: String): ZipEntry {
        return cafemod?.findEntry(path) ?: throw RuntimeException("Path is not exist")
    }

    fun getTracePrinted(path: String): String {
        return cafemod?.getTracePrinted(path) ?: throw RuntimeException("Path is not exist")
    }

    fun getFields(path: String): List<Field> {
        return cafemod?.getFields(path) ?: throw RuntimeException("Path is not exist")
    }

    fun getMethods(path: String): List<Method> {
        return cafemod?.getMethods(path) ?: throw RuntimeException("Path is not exist")
    }

    fun getMethodInstructions(path: String, nd: String): List<InstructionNode> {
        return cafemod?.getMethodInstructions(path, nd) ?: throw RuntimeException("Path is not exist")
    }

    methods[::loadFiles.name] = { params ->
        params ?: throw RuntimeException("Path is not exist")
        val mapper = jacksonObjectMapper().registerModule(
            SimpleModule().addSerializer(
                ZipEntry::class.java,
                ZipEntrySerializer(1)
            )
        )
        Rpc(
            Rpc.Type.RESPONSE,
            ::loadFiles.name,
            result = mapper.writeValueAsString(
                loadFiles(
                    params[0].toString()
                )
            )
        )
    }

    methods[::findEntry.name] = { params ->
        params ?: throw RuntimeException("Path is not exist")
        val entry = findEntry(params[0].toString())
        val mapper = jacksonObjectMapper().registerModule(
            SimpleModule().addSerializer(
                ZipEntry::class.java,
                ZipEntrySerializer(params[1].toString().toInt())
            )
        )
        Rpc(Rpc.Type.RESPONSE, ::findEntry.name, result = mapper.writeValueAsString(entry))
    }

    methods[::getTracePrinted.name] = { params ->
        params ?: throw RuntimeException("Path is not exist")
        Rpc(
            Rpc.Type.RESPONSE,
            ::getTracePrinted.name,
            result = getTracePrinted(params[0].toString())
        )
    }

    methods[::getFields.name] = { params ->
        params ?: throw RuntimeException("Path is not exist")
        Rpc(
            Rpc.Type.RESPONSE,
            ::getFields.name,
            result = jackson.writeValueAsString(getFields(params[0].toString()))
        )
    }

    methods[::getMethods.name] = { params ->
        params ?: throw RuntimeException("Path is not exist")
        Rpc(
            Rpc.Type.RESPONSE,
            ::getMethods.name,
            result = jackson.writeValueAsString(getMethods(params[0].toString()))
        )
    }

    methods[::getMethodInstructions.name] = { params ->
        params ?: throw RuntimeException("Path is not exist")
        Rpc(
            Rpc.Type.RESPONSE,
            ::getMethodInstructions.name,
            result = jackson.writeValueAsString(getMethodInstructions(params[0].toString(), params[1].toString()))
        )
    }

    while (true) {
        val line = reader.readLine()
        try {
            val rpc = jackson.readValue<Rpc>(line)
            when (rpc.type) {
                Rpc.Type.REQUEST -> {
                    CoroutineScope(Dispatchers.Default).launch {
                        writer.write(
                            jackson.writeValueAsString(
                                methods[rpc.method]?.invoke(rpc.params)
                                    ?: throw IllegalArgumentException("Method ${rpc.method} not found")
                            )
                        )
                        writer.write("\n")
                        writer.flush()
                    }
                }

                else -> {}
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
