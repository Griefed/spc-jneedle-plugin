/*
 * MIT License
 *
 * Copyright (c) 2023 Griefed
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
 *
 */
package de.griefed.plugin.jneedle.serverpack

import dev.kosmx.needle.CheckWrapper
import dev.kosmx.needle.ScanResult
import org.apache.logging.log4j.LogManager
import java.awt.Desktop
import java.awt.Frame
import java.nio.file.Path
import javax.swing.JDialog
import javax.swing.JOptionPane

abstract class PerformScan {
    private val pluginsLog = LogManager.getLogger("AddonsLogger")
    fun scan(destination: Path, extension: String) {
        pluginsLog.info("$extension - Scanning $destination for infections....")
        try {
            val run = CheckWrapper.checkPathBlocking(destination)
            val entries = getScanResults(run)
            var message = ""
            if (entries.isNotEmpty()) {
                for (entry in entries) {
                    pluginsLog.info(entry)
                    message += "$entry\n"
                }
                if (Desktop.isDesktopSupported() && entries.isNotEmpty()) {
                    val dialog = createNonModalDialog(message,extension)
                    dialog.isVisible = true
                }
            }
        } catch (ex: Exception) {
            pluginsLog.error("Error during scan.", ex)
        }
    }

    private fun createNonModalDialog(message: String, title: String) : JDialog {
        val pane = JOptionPane()
        pane.optionType = JOptionPane.PLAIN_MESSAGE
        pane.message = message
        val dialog: JDialog = pane.createDialog(Frame.getFrames()[0],title)
        dialog.isModal = false
        dialog.defaultCloseOperation = JDialog.DISPOSE_ON_CLOSE
        return dialog
    }

    private fun getScanResults(scanResults: List<ScanResult>): List<String> {
        val output = mutableListOf<String>()
        var addition: String
        for (scanResult in scanResults) {
            output.add("Results for ${scanResult.first}:")
            for (jarCheckResult in scanResult.second) {
                addition = "${jarCheckResult.status}: ${jarCheckResult.getMessage()}\n".padStart(9,' ')
                output.add(addition)
            }
        }

        return output.toList()
    }
}