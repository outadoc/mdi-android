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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import fr.outadoc.mdi.sample.databinding.FragmentGridBinding
import io.uniflow.androidx.flow.onStates
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import java.io.BufferedReader
import java.io.InputStreamReader

@Suppress("unused")
class IconGridFragment : Fragment() {

    private var binding: FragmentGridBinding? = null
    private val viewModel: IconGridViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGridBinding.inflate(inflater, container, false).apply {
            recyclerViewIconGrid.layoutManager = GridLayoutManager(context, ITEM_SPAN)
            recyclerViewIconGrid.adapter = IconGridAdapter()

            FastScrollerBuilder(recyclerViewIconGrid)
                .useMd2Style()
                .build()
        }

        onStates(viewModel) { state ->
            when (state) {
                is IconGridViewModel.State.Ready -> binding?.adapter?.submitList(state.icons)
            }
        }

        return binding!!.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let { context ->
            viewModel.loadIcons(
                context.assets.open(MAP_FILENAME).reader().buffered()
            )
        }
    }

    private val FragmentGridBinding.adapter: IconGridAdapter
        get() = recyclerViewIconGrid.adapter as IconGridAdapter

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val ITEM_SPAN = 4
        const val MAP_FILENAME = "mdi_map.txt"
    }
}