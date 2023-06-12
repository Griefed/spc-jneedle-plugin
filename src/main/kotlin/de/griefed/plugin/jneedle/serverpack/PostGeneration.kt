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

import com.electronwill.nightconfig.core.CommentedConfig
import com.electronwill.nightconfig.toml.TomlWriter
import de.griefed.serverpackcreator.api.ApiProperties
import de.griefed.serverpackcreator.api.PackConfig
import de.griefed.serverpackcreator.api.plugins.serverpackhandler.PostGenExtension
import de.griefed.serverpackcreator.api.utilities.common.Utilities
import de.griefed.serverpackcreator.api.versionmeta.VersionMeta
import org.apache.logging.log4j.LogManager
import org.pf4j.Extension
import java.nio.file.Paths
import java.util.*

/**
 * Custom post-server pack generation tasks.
 * @author Griefed
 */
@Suppress("unused")
@Extension
class PostGeneration : PerformScan(),PostGenExtension {
    private val pluginsLog = LogManager.getLogger("AddonsLogger")
    private val tomlWriter: TomlWriter = TomlWriter()

    /**
     * @param versionMeta         Instance of [VersionMeta] so you can work with available
     * Minecraft, Forge, Fabric, LegacyFabric and Quilt versions.
     * @param utilities           Instance of [Utilities] commonly used across
     * ServerPackCreator.
     * @param apiProperties       Instance of [ApiProperties] as ServerPackCreator itself uses
     * it.
     * @param packConfig  Instance of [PackConfig] for a given server pack.
     * @param destination         String. The destination of the server pack.
     * @param pluginConfig         Configuration for this addon, conveniently provided by
     * ServerPackCreator.
     * @param packSpecificConfigs Modpack and server pack specific configurations for this addon,
     * conveniently provided by ServerPackCreator.
     * @throws Exception [Exception] when an uncaught error occurs in the addon.
     * @author Griefed
     */
    @Throws(Exception::class)
    override fun run(
        versionMeta: VersionMeta,
        utilities: Utilities,
        apiProperties: ApiProperties,
        packConfig: PackConfig,
        destination: String,
        pluginConfig: Optional<CommentedConfig>,
        packSpecificConfigs: ArrayList<CommentedConfig>
    ) {
        scan(Paths.get(destination), name)
    }

    /**
     * Get the if of this extension. Used by ServerPackCreator to determine which configuration, if
     * any, to provide to any given extension being run.
     *
     * @return The ID of this extension.
     * @author Griefed
     */
    override val extensionId: String
        get() = "postgen-scans"

    /**
     * Get the name of this addon.
     *
     * @return The name of this addon.
     * @author Griefed
     */
    override val name: String
        get() = "Post Server Pack Generation Scan"

    /**
     * Get the description of this addon.
     *
     * @return The description of this addon.
     * @author Griefed
     */
    override val description: String
        get() = "Scans the server pack for malicious content after it has finished generating."

    /**
     * Get the author of this addon.
     *
     * @return The author of this addon.
     * @author Griefed
     */
    override val author: String
        get() = "Griefed"

    /**
     * Get the version of this addon.
     *
     * @return The version of this addon.
     * @author Griefed
     */
    override val version: String
        get() = "0.0.1-SNAPSHOT"
}