package com.homebrew.gifsygrid.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.homebrew.gifsygrid.databinding.GridFragmentBinding
import com.homebrew.gifsygrid.databinding.GridItemBinding
import com.homebrew.gifsygrid.ui.adapter.GifGridAdapter
import com.homebrew.gifsygrid.ui.util.GridSpacing
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GridFragment : Fragment() {
    private lateinit var gridAdapter: GifGridAdapter
    private lateinit var gridFragmentBinding: GridFragmentBinding
    private val gridFragmentViewModel : GridFragmentViewModel by viewModels()

    private fun initRV() {
        gridAdapter = GifGridAdapter(requireContext(), emptyList(), {})
        gridFragmentBinding.GifsRecycler.adapter = gridAdapter
        gridFragmentBinding.GifsRecycler.addItemDecoration(GridSpacing(2,150,true))
        gridFragmentBinding.GifsRecycler.layoutManager = GridLayoutManager(requireContext(),2)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        gridFragmentBinding = GridFragmentBinding.inflate(inflater, container, false)
        initRV()
        return gridFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            gridFragmentViewModel.gifFlow.collectLatest {
                gridAdapter.updateGrid(it)
                Log.e("GIPHY","at least it tried")
                Log.e("Giphy","${it.size}")

            }
        }
    }

}