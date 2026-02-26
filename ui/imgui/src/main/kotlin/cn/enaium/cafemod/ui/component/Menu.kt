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

import cn.enaium.cafemod.ui.window.About
import cn.enaium.cafemod.utility.ImRemember.remember
import cn.enaium.cafemod.utility.inject
import cn.enaium.cafemod.workspace.Workspace
import imgui.ImGui
import imgui.flag.ImGuiMouseButton
import imgui.flag.ImGuiMouseCursor
import imgui.flag.ImGuiWindowFlags
import imgui.type.ImBoolean
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.Desktop
import java.net.URI

/**
 * @author Enaium
 */
fun Menu(workspace: Workspace = inject()) {
    var about by remember { ImBoolean(false) }

    if (ImGui.beginMenuBar()) {
        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("Load File")) {
                CoroutineScope(Dispatchers.Default).launch {
                    val file = FileKit.openFilePicker(mode = FileKitMode.Single, type = FileKitType.File(listOf("jar")))
                    file?.file?.absolutePath?.also {
                        workspace.load(it)
                    }
                }
            }
            ImGui.endMenu()
        }
        if (ImGui.beginMenu("Help")) {
            if (ImGui.beginMenu("Theme")) {
                if (ImGui.menuItem("Dark")) {
                    ImGui.styleColorsDark()
                }
                if (ImGui.menuItem("Light")) {
                    ImGui.styleColorsLight()
                }
                if (ImGui.menuItem("Classic")) {
                    ImGui.styleColorsClassic()
                }
                ImGui.endMenu()
            }
            if (ImGui.menuItem("About")) {
                about.set(true)
            }
            ImGui.endMenu()
        }
        ImGui.endMenuBar()
    }

    if (about.get()) {
        About(about)
    }
}