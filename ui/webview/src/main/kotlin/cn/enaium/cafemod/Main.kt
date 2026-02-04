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

import app.saucer.SaucerApp
import app.saucer.util.SaucerUrl
import app.saucer.webview.SaucerWebview
import app.saucer.webview.SaucerWebviewListener
import app.saucer.webview.scheme.SaucerSchemeHandler
import app.saucer.webview.window.SaucerIcon
import app.saucer.webview.window.SaucerWindow


/**
 * @author Enaium
 */
fun main() {
    SaucerApp.initialize("cn.enaium.cafemod", true)
    SaucerWebview.registerCustomScheme("app")
    val debug = System.getProperty("debug") == "true"
    val window = SaucerWindow.create()
    val webview = window.createWebview { opts -> opts.hardwareAcceleration(true) }
    webview.bridge.defineObject("bridge", Bridge(webview))
    webview
        .listener(object : SaucerWebviewListener {
            override fun onTitle(newTitle: String) {
                window.title("Cafemod")
            }

            override fun onFavicon(newIcon: SaucerIcon) {
                if (!newIcon.isEmpty) {
                    window.icon(newIcon)
                }
            }
        })
        .contextMenuAllowed(false)
        .devToolsVisible(debug)
        .addSchemeHandler("app", SaucerSchemeHandler.fromResources(object {}::class.java))
        .url(if (debug) SaucerUrl.parse("http://localhost:5173") else SaucerUrl.parse("app://authority/index.html"))


    window
        .show()
        .focus()

    SaucerApp.run()
}