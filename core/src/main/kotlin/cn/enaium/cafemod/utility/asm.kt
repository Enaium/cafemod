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

package cn.enaium.cafemod.utility

import cn.enaium.cafemod.model.instruction.*
import org.objectweb.asm.ClassReader
import org.objectweb.asm.Label
import org.objectweb.asm.tree.*

/**
 * @author Enaium
 */
fun ClassReader.cn(): ClassNode {
    val cn = ClassNode()
    this.accept(cn, 0)
    return cn
}

fun AbstractInsnNode.toInstructionNode(): InstructionNode {
    val type = this.type
    val opcode = this.opcode

    val labelIndexes = mutableMapOf<Label, Int>()

    fun appendLabelIndex(label: Label): Int {
        return labelIndexes.getOrPut(label) {
            labelIndexes.size
        }
    }

    return when (this) {
        is FieldInsnNode -> {
            FieldInstructionNode(type, opcode, this.owner, this.name, this.desc)
        }

        is FrameNode -> {
            FrameInstructionNode(type, opcode)
        }

        is IincInsnNode -> {
            IincInstructionNode(type, opcode, this.`var`, this.incr)
        }

        is InsnNode -> {
            InsnInstructionNode(type, opcode)
        }

        is IntInsnNode -> {
            IntInstructionNode(type, opcode, this.operand)
        }

        is InvokeDynamicInsnNode -> {
            InvokeDynamicInstructionNode(type, opcode, this.name, this.desc)
        }

        is JumpInsnNode -> {
            JumpInstructionNode(type, opcode, appendLabelIndex(this.label.label))
        }

        is LabelNode -> {
            LabelInstructionNode(type, opcode, appendLabelIndex(this.label))
        }

        is LdcInsnNode -> {
            LdcInstructionNode(type, opcode, this.cst)
        }

        is LineNumberNode -> {
            LineNumberInstructionNode(type, opcode, this.line, appendLabelIndex(this.start.label))
        }

        is LookupSwitchInsnNode -> {
            LookupSwitchInstructionNode(type, opcode, appendLabelIndex(this.dflt.label))
        }

        is MethodInsnNode -> {
            MethodInstructionNode(type, opcode, this.owner, this.name, this.desc)
        }

        is MultiANewArrayInsnNode -> {
            MultiANewArrayInstructionNode(type, opcode, this.desc, this.dims)
        }

        is TableSwitchInsnNode -> {
            TableSwitchInstructionNode(
                type,
                opcode,
                this.min,
                this.max,
                appendLabelIndex(this.dflt.label),
                this.labels.map { appendLabelIndex(it.label) })
        }

        is TypeInsnNode -> {
            TypeInstructionNode(type, opcode, this.desc)
        }

        is VarInsnNode -> {
            VarInstructionNode(type, opcode, this.`var`)
        }

        else -> {
            throw RuntimeException("Unsupported instruction")
        }
    }
}