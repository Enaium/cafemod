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

package cn.enaium.cafemod.impl

import cn.enaium.cafemod.Cafemod
import cn.enaium.cafemod.model.Field
import cn.enaium.cafemod.model.Method
import cn.enaium.cafemod.model.ZipEntry
import cn.enaium.cafemod.model.instruction.InstructionNode
import cn.enaium.cafemod.utility.cn
import cn.enaium.cafemod.utility.toInstructionNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.objectweb.asm.ClassReader
import org.objectweb.asm.util.TraceClassVisitor
import java.io.InputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.util.zip.ZipFile

/**
 * @author Enaium
 */
/**
 * Load a zip file by input file
 */
suspend fun Cafemod.load() = withContext(Dispatchers.IO) {
    val zip = ZipFile(input)

    val nodeMap = mutableMapOf<String, ZipEntry>()
    nodeMap[""] = root

    val zipEntries = zip.entries()
    while (zipEntries.hasMoreElements()) {
        val entry = zipEntries.nextElement()
        val path = entry.name.trimEnd('/')

        if (path.isEmpty()) continue

        val parts = path.split('/')
        var currentPath = ""
        var parent = root

        for ((index, part) in parts.withIndex()) {
            currentPath = if (currentPath.isEmpty()) part else "$currentPath/$part"

            val isLast = index == parts.lastIndex
            val type = if (isLast && !entry.isDirectory) {
                ZipEntry.Type.FILE
            } else {
                ZipEntry.Type.DIRECTORY
            }

            val node = nodeMap.getOrPut(currentPath) {
                val newNode = ZipEntry(part, path, type, parent, mutableListOf())
                parent.children.add(newNode)
                newNode
            }

            parent = node
        }
    }

    zip.close()
}

/**
 * Find an entry by zip entry path
 *
 * [path] zip entry path
 */
fun Cafemod.findEntry(path: String): ZipEntry? {
    if (path.isEmpty()) return root

    val parts = path.split('/')

    if (parts.isEmpty()) return root

    var current: ZipEntry = root

    for (part in parts) {
        current = current.children.find { it.name == part } ?: return null
    }

    return current
}

/**
 * Read a file from zip file by zip entry path
 *
 * [path] zip entry path
 */
fun Cafemod.readContent(path: String): InputStream? {
    val entry = findEntry(path) ?: return null
    if (entry.type != ZipEntry.Type.FILE) return null

    val zip = ZipFile(input)
    val zipEntry = zip.getEntry(path) ?: return null
    return zip.getInputStream(zipEntry)
}


/**
 * Get a trace bytecode by zip entry path
 *
 * [path] zip entry path
 */
fun Cafemod.getTracePrinted(path: String): String {
    readContent(path)?.use { content ->
        val sw = StringWriter()
        ClassReader(content).cn().accept(TraceClassVisitor(PrintWriter(sw)))
        return sw.toString()
    }
    return ""
}

/**
 * Get a member name and descriptors by zip entry path
 *
 * [path] zip entry path
 */
fun Cafemod.getMemberNds(path: String): Set<String> {
    val names = mutableSetOf<String>()

    readContent(path)?.use { content ->
        val cn = ClassReader(content).cn()
        names.addAll(cn.fields.map { "${it.name}:${it.desc}" })
        names.addAll(cn.methods.map { "${it.name}:${it.desc}" })
    }
    return names
}

fun Cafemod.getFields(path: String): List<Field> {
    readContent(path)?.use { content ->
        val cn = ClassReader(content).cn()
        return cn.fields.map { Field(it.name, it.desc, it.access) }
    }
    return emptyList()
}

fun Cafemod.getMethods(path: String): List<Method> {
    readContent(path)?.use { content ->
        val cn = ClassReader(content).cn()
        return cn.methods.map { Method(it.name, it.desc, it.access) }
    }
    return emptyList()
}

/**
 * Get a field info by zip entry path and nd
 *
 * [path] zip entry path
 * [nd] name nad descriptor
 */
fun Cafemod.getField(path: String, nd: String): Field? {
    readContent(path)?.use { content ->
        val cn = ClassReader(content).cn()
        return cn.fields.find { "${it.name}:${it.desc}" == nd }?.let {
            Field(it.name, it.desc, it.access)
        }
    }
    return null
}


/**
 * Get a method info by zip entry path and nd
 *
 * [path] zip entry path
 * [nd] name nad descriptor
 */
fun Cafemod.getMethod(path: String, nd: String): Method? {
    readContent(path)?.use { content ->
        val cn = ClassReader(content).cn()
        return cn.methods.find { "${it.name}:${it.desc}" == nd }?.let {
            Method(it.name, it.desc, it.access)
        }
    }
    return null
}

/**
 * Get a method instructions
 * [path] zip entry path
 * [nd] name nad descriptor
 */
fun Cafemod.getMethodInstructions(path: String, nd: String): Map<Int, InstructionNode>? {
    readContent(path)?.use { content ->
        val cn = ClassReader(content).cn()
        val find = cn.methods.find { "${it.name}:${it.desc}" == nd }
        return find?.instructions?.mapIndexed { index, node -> index to node.toInstructionNode() }?.toMap()
    }
    return null
}