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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.outadoc.mdi.common.MdiFontIcon
import fr.outadoc.mdi.toIconOrNull
import java.io.BufferedReader

class IconGridViewModel : ViewModel() {

    private val _allIcons = MutableLiveData<Sequence<MdiFontIcon>>()
    val allIcons: LiveData<Sequence<MdiFontIcon>>
        get() = _allIcons

    fun loadIcons(iconMap: BufferedReader) {
        _allIcons.value = sequence {
            iconMap.useLines { lines ->
                lines.mapNotNull { name -> name.takeWhile { it != ' ' }.toIconOrNull() }
            }
        }
    }
}