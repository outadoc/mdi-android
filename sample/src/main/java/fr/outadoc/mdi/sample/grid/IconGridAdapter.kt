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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.outadoc.mdi.MdiFontIconView
import fr.outadoc.mdi.common.MdiFontIcon
import fr.outadoc.mdi.sample.R
import fr.outadoc.mdi.setIcon

class IconGridAdapter : ListAdapter<MdiFontIcon, IconGridAdapter.IconViewHolder>(IconItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val li = LayoutInflater.from(parent.context)
        val view = li.inflate(R.layout.item_icon, parent, false)
        return IconViewHolder(view)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val icon = getItem(position)
        holder.icon.setIcon(icon)
        holder.name.setText(icon.name, TextView.BufferType.NORMAL)
    }

    class IconViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: MdiFontIconView = view.findViewById(R.id.fontIconView_icon)
        val name: TextView = view.findViewById(R.id.textView_iconName)
    }
}