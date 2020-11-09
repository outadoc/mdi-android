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

package fr.outadoc.mdi.sample.grid

import fr.outadoc.mdi.common.MdiFontIcon
import fr.outadoc.mdi.toIconOrNull
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState
import io.uniflow.core.threading.onIO
import java.io.BufferedReader

class IconGridViewModel : AndroidDataFlow(defaultState = State.Loading) {

    sealed class State : UIState() {
        object Loading : State()
        class Ready(val icons: List<MdiFontIcon>) : State()
    }

    fun loadIcons(iconMap: BufferedReader) = action {
        onIO {
            val icons = iconMap.useLines { lines ->
                lines.mapNotNull { name -> name.takeWhile { it != ' ' }.toIconOrNull() }
                    .toList()
            }
            setState { State.Ready(icons) }
        }
    }
}