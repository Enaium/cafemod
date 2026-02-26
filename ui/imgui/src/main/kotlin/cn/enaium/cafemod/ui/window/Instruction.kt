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

package cn.enaium.cafemod.ui.window

import cn.enaium.cafemod.impl.getMethodInstructions
import cn.enaium.cafemod.model.instruction.*
import cn.enaium.cafemod.utility.ImRemember.remember
import cn.enaium.cafemod.utility.inject
import cn.enaium.cafemod.workspace.Workspace
import imgui.ImGui
import imgui.flag.*
import imgui.type.ImBoolean
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.util.Printer

/**
 * @author Enaium
 */
fun Instruction(open: ImBoolean, path: String, nd: String) {
    val workspace: Workspace = inject()

    val instructions: List<InstructionNode> by remember {
        workspace.cafemod?.getMethodInstructions(path, nd) ?: emptyList()
    }

    ImGui.setNextWindowSize(600f, 400f, ImGuiCond.Once)

    if (ImGui.begin(
            nd,
            open,
            ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoCollapse or ImGuiWindowFlags.NoDocking or ImGuiWindowFlags.NoMove
        )
    ) {
        if (ImGui.beginTable(
                "table", 2,
                ImGuiTableFlags.Borders or ImGuiTableFlags.RowBg or ImGuiTableFlags.Resizable or ImGuiTableFlags.ScrollX
            )
        ) {
            ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthFixed or ImGuiTableColumnFlags.NoResize, 100f)
            ImGui.tableSetupColumn("")
            ImGui.tableHeadersRow()

            instructions.forEachIndexed { index, node ->
                ImGui.tableNextRow()
                ImGui.tableNextColumn()
                ImGui.selectable(
                    "##i_$index",
                    ImBoolean(),
                    ImGuiSelectableFlags.SpanAllColumns or ImGuiSelectableFlags.AllowOverlap
                )
                ImGui.sameLine()
                ImGui.text(index.toString().padStart(5, '0'))
                ImGui.tableNextColumn()
                if (node.opcode == -1) {
                    when (node.type) {
                        AbstractInsnNode.LABEL -> {
                            "LABEL"
                        }

                        AbstractInsnNode.FRAME -> {
                            "FRAME"
                        }

                        AbstractInsnNode.LINE -> {
                            "LINE"
                        }

                        else -> {
                            null
                        }
                    }
                } else {
                    Printer.OPCODES[node.opcode]
                }?.also {
                    ImGui.text(it)
                }
                ImGui.sameLine()
                when (node) {
                    is FieldInstructionNode -> {
                        ImGui.text("${node.owner}.${node.name}: ${node.descriptor}")
                    }

                    is FrameInstructionNode -> {
                        when (node.frame) {
                            Opcodes.F_NEW, Opcodes.F_FULL -> ImGui.text("FULL")
                            Opcodes.F_APPEND -> ImGui.text("APPEND")
                            Opcodes.F_CHOP -> ImGui.text("CHOP")
                            Opcodes.F_SAME -> ImGui.text("SAME")
                            Opcodes.F_SAME1 -> ImGui.text("SAME 1")
                            else -> {}
                        }
                    }

                    is IincInstructionNode -> ImGui.text("${node.varIndex} ${node.increment}")
                    is IntInstructionNode -> ImGui.text("${node.operand}")
                    is InvokeDynamicInstructionNode -> ImGui.text("${node.name}${node.desc}")
                    is JumpInstructionNode -> ImGui.text("LABEL ${node.labelIndex}")
                    is LabelInstructionNode -> ImGui.text("${node.labelIndex}")
                    is LdcInstructionNode -> ImGui.text("${node.cst}")
                    is LineNumberInstructionNode -> ImGui.text("${node.line} LABEL ${node.startLabelIndex}")
                    is LookupSwitchInstructionNode -> ImGui.text("LABEL ${node.dfltLabelIndex}")
                    is MethodInstructionNode -> ImGui.text("${node.owner}.${node.name}${node.desc}")
                    is TableSwitchInstructionNode -> ImGui.text(
                        "${node.min} ${node.max} LABEL ${node.dfltLabelIndex} ${
                            node.labelIndexes.joinToString(
                                " "
                            ) { "LABEL $it" }
                        }")

                    is TypeInstructionNode -> ImGui.text(node.desc)
                    is VarInstructionNode -> ImGui.text("${node.`var`}")
                    else -> {}
                }
            }
            ImGui.endTable()
        }
        ImGui.end()
    }
}