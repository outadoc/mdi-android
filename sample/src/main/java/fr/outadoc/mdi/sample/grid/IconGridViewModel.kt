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
import fr.outadoc.mdi.common.MdiMapperLocator
import io.uniflow.android.AndroidDataFlow
import io.uniflow.core.flow.actionOn
import io.uniflow.core.flow.data.UIEvent
import io.uniflow.core.flow.data.UIState

class IconGridViewModel : AndroidDataFlow(defaultState = State.Loading) {

    sealed class State : UIState() {
        object Loading : State()
        data class Ready(
            val allIcons: List<MdiFontIcon>,
            val filteredIcons: List<MdiFontIcon> = allIcons
        ) : State()
    }

    sealed class Event : UIEvent() {
        object OpenMdiHomePage : Event()
        object OpenRepoPage : Event()
    }

    fun loadIcons() = action {
        MdiMapperLocator.instance?.getAllIcons()?.let { icons ->
            setState { State.Ready(icons) }
        }
    }

    fun filterBy(query: String?) = actionOn<State.Ready> { state ->
        setState {
            if (query.isNullOrBlank()) {
                state.copy(filteredIcons = state.allIcons)
            } else {
                state.copy(filteredIcons = state.allIcons.filter { it.name.contains(query) })
            }
        }
    }

    fun onAboutMdiClicked() = action {
        sendEvent { Event.OpenMdiHomePage }
    }

    fun onAboutRepoClicked() = action {
        sendEvent { Event.OpenRepoPage }
    }
}