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

package cn.enaium.cafemod.ui.component

import cn.enaium.cafemod.impl.getFields
import cn.enaium.cafemod.impl.getMethods
import cn.enaium.cafemod.model.Field
import cn.enaium.cafemod.model.Method
import cn.enaium.cafemod.model.ZipEntry
import cn.enaium.cafemod.ui.window.Instruction
import cn.enaium.cafemod.utility.ImRemember.remember
import cn.enaium.cafemod.utility.inject
import cn.enaium.cafemod.workspace.Workspace
import imgui.ImGui
import imgui.flag.ImGuiSelectableFlags
import imgui.flag.ImGuiTableColumnFlags
import imgui.flag.ImGuiTableFlags
import imgui.type.ImBoolean
import org.objectweb.asm.Opcodes

/**
 * @author Enaium
 */
fun ClassMembers(zipEntry: ZipEntry) {

    val workspace: Workspace = inject()

    val fields: List<Field> by remember { workspace.cafemod?.getFields(zipEntry.path) ?: emptyList() }
    val methods: List<Method> by remember { workspace.cafemod?.getMethods(zipEntry.path) ?: emptyList() }
    val instruction by remember { ImBoolean(false) }
    var currentPath: String? by remember { null }
    var currentNd: String? by remember { null }

    if (ImGui.beginTable(
            "table", 5,
            ImGuiTableFlags.Borders or ImGuiTableFlags.RowBg or ImGuiTableFlags.Resizable or ImGuiTableFlags.ScrollX
        )
    ) {

        ImGui.tableSetupColumn("", ImGuiTableColumnFlags.WidthFixed or ImGuiTableColumnFlags.NoResize, 30f)
        ImGui.tableSetupColumn("Type", ImGuiTableColumnFlags.WidthFixed or ImGuiTableColumnFlags.NoResize, 200f)
        ImGui.tableSetupColumn("Access", ImGuiTableColumnFlags.WidthFixed or ImGuiTableColumnFlags.NoResize, 160f)
        ImGui.tableSetupColumn("Name")
        ImGui.tableSetupColumn("Descriptor")
        ImGui.tableHeadersRow()

        fun access(access: Int) {
            ImGui.tableNextColumn()
            if ((access and Opcodes.ACC_STATIC) !== 0) {
                ImGui.text("Static")
            }
            ImGui.sameLine()
            if ((access and Opcodes.ACC_FINAL) !== 0) {
                ImGui.text("Final")
            }
            ImGui.tableNextColumn()
            if ((access and Opcodes.ACC_PUBLIC) !== 0) {
                ImGui.text("Public")
            } else if ((access and Opcodes.ACC_PRIVATE) !== 0) {
                ImGui.text("Private")
            } else if ((access and Opcodes.ACC_PROTECTED) !== 0) {
                ImGui.text("Protected")
            }
        }

        fields.forEach { field ->
            ImGui.tableNextRow()
            ImGui.tableNextColumn()
            ImGui.selectable(
                "##r_f_${field.name}:${field.desc}",
                ImBoolean(),
                ImGuiSelectableFlags.SpanAllColumns or ImGuiSelectableFlags.AllowOverlap
            )
            ImGui.sameLine()
            ImGui.text("F")
            access(field.access)
            ImGui.tableNextColumn()
            ImGui.text(field.name)
            ImGui.tableNextColumn()
            ImGui.text(field.desc)
        }

        methods.forEach { method ->
            ImGui.tableNextRow()
            ImGui.tableNextColumn()
            ImGui.selectable(
                "##r_m_${method.name}:${method.desc}",
                ImBoolean(),
                ImGuiSelectableFlags.SpanAllColumns or ImGuiSelectableFlags.AllowOverlap
            )
            if (ImGui.beginPopupContextItem()) {
                if (ImGui.menuItem("View Instruction")) {
                    instruction.set(true)
                    currentPath = zipEntry.path
                    currentNd = "${method.name}:${method.desc}"
                }
                ImGui.endPopup()
            }
            ImGui.sameLine()
            ImGui.text("M")
            access(method.access)
            ImGui.tableNextColumn()
            ImGui.text(method.name)
            ImGui.tableNextColumn()
            ImGui.text(method.desc)
        }
        ImGui.endTable()
    }

    if (instruction.get()) {
        if (currentPath != null && currentNd != null) {
            Instruction(instruction, currentPath!!, currentNd!!)
        }
    }
}