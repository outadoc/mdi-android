/*
 * Copyright 2020 Baptiste Candellier
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package fr.outadoc.mdi

import android.content.Context
import fr.outadoc.mdi.common.MdiFontIcon
import fr.outadoc.mdi.common.MdiStringRef
import fr.outadoc.mdi.common.MdiMapper
import java.util.TreeMap

/**
 * Maps an icon name to a unicode codepoint that can be displayed using the MDI font.
 */
public class AndroidMdiMapper(context: Context) : MdiMapper {

    companion object {
        private const val FILENAME = "mdi_map.txt"
        private const val SEPARATOR = ' '
    }

    private val iconMap: Map<String, MdiFontIcon>

    init {
        iconMap = context.assets.open(FILENAME)
            .reader().buffered()
            .useLines { lines ->
                loadMap(lines)
            }
    }

    override fun getIcon(@MdiStringRef iconName: String): MdiFontIcon? {
        return iconMap[iconName]
    }

    override fun getAllIcons(): List<MdiFontIcon> {
        return iconMap.values.toList()
    }

    private fun loadMap(lines: Sequence<String>): Map<String, MdiFontIcon> {
        return lines.map { line ->
            val split = line.split(SEPARATOR, limit = 2)
            split[0] to MdiFontIcon(name = split[0], unicodePoint = split[1])
        }.toMap(TreeMap())
    }
}
