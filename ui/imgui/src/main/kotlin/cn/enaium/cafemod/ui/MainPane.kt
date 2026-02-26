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

package cn.enaium.cafemod.ui

import cn.enaium.cafemod.ui.component.ContentTab
import cn.enaium.cafemod.ui.component.FileTree
import cn.enaium.cafemod.ui.component.Menu
import cn.enaium.cafemod.ui.component.Splitter
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags

/**
 * @author Enaium
 */
fun MainPane() {
    if (ImGui.begin(
            "Main",
            (ImGuiWindowFlags.NoDecoration
                    or ImGuiWindowFlags.NoMove
                    or ImGuiWindowFlags.NoBringToFrontOnFocus
                    or ImGuiWindowFlags.MenuBar),
        )
    ) {
        Menu()

        Splitter(
            left = {
                FileTree()
            },
            right = {
                ContentTab()
            },
            leftFlags = ImGuiWindowFlags.HorizontalScrollbar,
            rightFlags = ImGuiWindowFlags.NoScrollbar or ImGuiWindowFlags.NoScrollWithMouse
        )
        ImGui.end()
    }
}