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

import cn.enaium.cafemod.model.ZipEntry
import cn.enaium.cafemod.state.TabState
import cn.enaium.cafemod.utility.inject
import cn.enaium.cafemod.workspace.Workspace
import imgui.ImGui
import imgui.flag.ImGuiMouseButton
import imgui.flag.ImGuiTreeNodeFlags

/**
 * @author Enaium
 */
fun FileTree() {
    val workspace: Workspace = inject()
    val tabState: TabState = inject()

    fun Entries(entries: List<ZipEntry>) {
        val drawList = ImGui.getWindowDrawList()
        val sortedBy = entries.sortedBy { if (it.type == ZipEntry.Type.DIRECTORY) -1 else 1 }
        sortedBy.forEach { entry ->
            if (ImGui.treeNodeEx(
                    entry.name,
                    ImGuiTreeNodeFlags.SpanAvailWidth
                            or if (entry.type == ZipEntry.Type.DIRECTORY) 0 else ImGuiTreeNodeFlags.Leaf or ImGuiTreeNodeFlags.NoTreePushOnOpen
                )
            ) {
                Entries(entry.children)
                when (entry.type) {
                    ZipEntry.Type.DIRECTORY -> {
                        ImGui.treePop()
                    }

                    ZipEntry.Type.FILE -> {
                        if (ImGui.isItemHovered() && ImGui.isItemClicked(ImGuiMouseButton.Left) && ImGui.isMouseDoubleClicked(
                                ImGuiMouseButton.Left
                            )
                        ) {
                            tabState.opens.add(entry)
                        }
                    }
                }
            }
        }
    }

    workspace.cafemod?.root?.also {
        Entries(it.children)
    }
}