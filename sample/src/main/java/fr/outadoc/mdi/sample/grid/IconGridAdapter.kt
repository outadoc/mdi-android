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

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.outadoc.mdi.common.MdiFontIcon

class IconGridAdapter : ListAdapter<MdiFontIcon, IconViewHolder>(IconItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

class IconViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

}