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

import cn.enaium.cafemod.utility.ImRemember.remember
import imgui.ImGui

/**
 * @author Enaium
 */
fun Splitter(left: () -> Unit, right: () -> Unit, leftFlags: Int = 0, rightFlags: Int = 0) {

    var leftWidth by remember { 300f }

    val splitterWidth = 10f
    val minSize = 50f

    if (ImGui.beginChild("left", leftWidth, 0f, true, leftFlags)) {
        left()
        ImGui.endChild()
    }

    ImGui.sameLine()

    ImGui.button(" ", splitterWidth, -1f)

    if (ImGui.isItemActive()) {
        leftWidth += ImGui.getIO().mouseDelta.x
        if (leftWidth < minSize) leftWidth = minSize
    }

    ImGui.sameLine()

    if (ImGui.beginChild("right", 0f, 0f, true, rightFlags)) {
        right()
        ImGui.endChild()
    }
}