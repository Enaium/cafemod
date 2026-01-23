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

import app.saucer.SaucerFilePicker
import app.saucer.bridge.JavascriptFunction
import app.saucer.bridge.JavascriptObject
import app.saucer.webview.SaucerWebview
import cn.enaium.cafemod.impl.*
import cn.enaium.cafemod.model.ZipEntry
import cn.enaium.cafemod.serializer.ZipEntrySerializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.runBlocking

/**
 * @author Enaium
 */
@JavascriptObject
class Bridge(val webview: SaucerWebview) {
    private var cafemod: Cafemod? = null

    @JavascriptFunction
    private fun loadFile(): Map<Any, Any> {
        val file = SaucerFilePicker.create().filter("*.jar").pickFile()
        return if (file != null) {
            cafemod = Cafemod(file.absolutePath)
            runBlocking { cafemod?.load() }
            val mapper = jacksonObjectMapper().registerModule(
                SimpleModule().addSerializer(
                    ZipEntry::class.java,
                    ZipEntrySerializer(1)
                )
            )
            val entry = cafemod?.findEntry("") ?: throw RuntimeException("Load Failed")
            mapper.writeValueAsString(entry).obj()
        } else {
            throw RuntimeException("File does not exist")
        }
    }

    @JavascriptFunction
    private fun findEntry(path: String, depth: Int): Map<Any, Any> {
        val entry = cafemod?.findEntry(path) ?: throw RuntimeException("Path is not exist")
        val mapper = jacksonObjectMapper().registerModule(
            SimpleModule().addSerializer(
                ZipEntry::class.java,
                ZipEntrySerializer(depth)
            )
        )
        return mapper.writeValueAsString(entry).obj()
    }

    @JavascriptFunction
    private fun getTracePrinted(path: String): String {
        return cafemod?.getTracePrinted(path) ?: throw RuntimeException("Path is not exist")
    }

    @JavascriptFunction
    private fun getFields(path: String): List<Any> {
        return cafemod?.getFields(path)?.array() ?: throw RuntimeException("Path is not exist")
    }

    @JavascriptFunction
    private fun getMethods(path: String): List<Any> {
        return cafemod?.getMethods(path)?.array() ?: throw RuntimeException("Path is not exist")
    }
}


private fun String.obj(): Map<Any, Any> {
    return jackson.readValue<Map<Any, Any>>(this)
}

private fun Any.obj(): Map<Any, Any> {
    return jackson.readValue<Map<Any, Any>>(jackson.writeValueAsString(this))
}

private fun Any.array(): List<Any> {
    return jackson.readValue<List<Any>>(jackson.writeValueAsString(this))
}