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

package cn.enaium.cafemod.model

/**
 * @author Enaium
 */
data class Rpc(
    val type: Type,
    val method: String,
    val params: Array<Any>? = null,
    val result: String? = null,
) {
    enum class Type {
        REQUEST,
        RESPONSE,
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rpc

        if (type != other.type) return false
        if (method != other.method) return false
        if (!params.contentEquals(other.params)) return false
        if (result != other.result) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = type.hashCode()
        result1 = 31 * result1 + method.hashCode()
        result1 = 31 * result1 + (params?.contentHashCode() ?: 0)
        result1 = 31 * result1 + (result?.hashCode() ?: 0)
        return result1
    }
}