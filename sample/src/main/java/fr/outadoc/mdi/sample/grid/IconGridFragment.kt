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

import android.content.ClipData
import android.content.ClipboardManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import fr.outadoc.mdi.common.MdiFontIcon
import fr.outadoc.mdi.sample.BuildConfig
import fr.outadoc.mdi.sample.R
import fr.outadoc.mdi.sample.databinding.FragmentGridBinding
import io.uniflow.androidx.flow.onEvents
import io.uniflow.androidx.flow.onStates
import me.zhanghai.android.fastscroll.FastScrollerBuilder


@Suppress("unused")
class IconGridFragment : Fragment() {

    private var binding: FragmentGridBinding? = null
    private val viewModel: IconGridViewModel by viewModels()

    private var clipboardManager: ClipboardManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clipboardManager = context?.getSystemService()
        viewModel.loadIcons()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGridBinding.inflate(inflater, container, false).apply {
            with(recyclerViewIconGrid) {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, ITEM_SPAN)
                adapter = IconGridAdapter().apply {
                    clickListener = IconGridItemClickListener { icon ->
                        onItemClick(icon)
                    }
                }
            }

            FastScrollerBuilder(recyclerViewIconGrid)
                .useMd2Style()
                .build()

            val searchItem: MenuItem = toolbarGrid.menu.findItem(R.id.item_search)
            val searchView = searchItem.actionView as SearchView

            searchView.queryHint = getString(R.string.grid_search_hint)
            searchView.findViewById<View>(androidx.appcompat.R.id.search_plate).background = null
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.filterBy(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.filterBy(newText)
                    return true
                }
            })

            toolbarGrid.subtitle = getString(R.string.grid_subtitle, mdiVersion)
            toolbarGrid.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_about_mdi -> viewModel.onAboutMdiClicked()
                    R.id.item_about_repo -> viewModel.onAboutRepoClicked()
                    else -> return@setOnMenuItemClickListener false
                }
                true
            }

            toolbarGrid.setOnClickListener {
                recyclerViewIconGrid.scrollToPosition(0)
            }
        }

        onStates(viewModel) { state ->
            when (state) {
                is IconGridViewModel.State.Loading -> {
                    binding?.viewFlipperGrid?.displayedChild = CHILD_LOADING
                }
                is IconGridViewModel.State.Ready -> {
                    binding?.adapter?.submitList(state.filteredIcons)
                    binding?.viewFlipperGrid?.displayedChild = CHILD_GRID
                }
            }
        }

        onEvents(viewModel) { event ->
            when (event.take()) {
                is IconGridViewModel.Event.OpenMdiHomePage -> openUrl(getString(R.string.url_mdi_homepage).toUri())
                is IconGridViewModel.Event.OpenRepoPage -> openUrl(getString(R.string.url_repo).toUri())
            }
        }

        return binding!!.root
    }

    private fun openUrl(uri: Uri) {
        context?.let {
            CustomTabsIntent.Builder()
                .build()
                .launchUrl(it, uri)
        }
    }

    private fun onItemClick(icon: MdiFontIcon) {
        clipboardManager?.setPrimaryClip(ClipData.newPlainText(icon.name, icon.name))
        binding?.recyclerViewIconGrid?.let { view ->
            Snackbar.make(
                view,
                getString(R.string.grid_copiedToClipboard, icon.name),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private val FragmentGridBinding.adapter: IconGridAdapter
        get() = recyclerViewIconGrid.adapter as IconGridAdapter

    private val mdiVersion: String
        get() = BuildConfig.VERSION_NAME

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val ITEM_SPAN = 4

        const val CHILD_LOADING = 0
        const val CHILD_GRID = 1
    }
}