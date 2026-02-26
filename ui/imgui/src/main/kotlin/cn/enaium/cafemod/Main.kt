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

import cn.enaium.cafemod.state.TabState
import cn.enaium.cafemod.ui.MainPane
import cn.enaium.cafemod.utility.ImRemember.beginState
import cn.enaium.cafemod.utility.ImRemember.endState
import cn.enaium.cafemod.workspace.Workspace
import imgui.ImFontConfig
import imgui.ImFontGlyphRangesBuilder
import imgui.ImGui
import imgui.ImGuiIO
import imgui.app.Application
import imgui.app.Application.launch
import imgui.app.Configuration
import imgui.flag.ImGuiConfigFlags
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

/**
 * @author Enaium
 */
class Main : Application() {
    override fun configure(config: Configuration) {
        config.title = "Cafemod"
    }

    override fun initImGui(config: Configuration?) {
        super.initImGui(config)
        val io = ImGui.getIO()
        io.iniFilename = null
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard)
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable)
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable)
        io.configViewportsNoTaskBarIcon = true
        initFonts(io)
    }

    private fun initFonts(io: ImGuiIO) {

        val rangesBuilder = ImFontGlyphRangesBuilder()
        rangesBuilder.addRanges(io.getFonts().glyphRangesDefault)
        rangesBuilder.addRanges(io.getFonts().glyphRangesGreek)
        rangesBuilder.addRanges(io.getFonts().glyphRangesKorean)
        rangesBuilder.addRanges(io.getFonts().glyphRangesJapanese)
        rangesBuilder.addRanges(io.getFonts().glyphRangesChineseFull)
        rangesBuilder.addRanges(io.getFonts().glyphRangesChineseSimplifiedCommon)
        rangesBuilder.addRanges(io.getFonts().glyphRangesCyrillic)
        rangesBuilder.addRanges(io.getFonts().glyphRangesThai)
        rangesBuilder.addRanges(io.getFonts().glyphRangesVietnamese)
        val fontConfig = ImFontConfig()
        fontConfig.mergeMode = true
        io.fonts.clear()
        val glyphRanges = rangesBuilder.buildRanges()
        io.fonts
            .addFontFromFileTTF(
                "C:/Windows/Fonts/CascadiaCode.ttf",
                16f * 2,
                glyphRanges
            )
        io.fonts
            .addFontFromFileTTF(
                "C:/Windows/Fonts/msyh.ttc",
                16f * 2,
                fontConfig,
                glyphRanges
            )
    }


    override fun process() {
        val viewport = ImGui.getMainViewport()

        ImGui.setNextWindowPos(viewport.posX, viewport.posY)
        ImGui.setNextWindowSize(viewport.sizeX, viewport.sizeY)
        ImGui.setNextWindowViewport(viewport.id)

        beginState()
        MainPane()
        endState()
    }
}

fun main() {
    startKoin {
        modules(module {
            single { Workspace() }
            single { TabState() }
        })
    }
    launch(Main())
}