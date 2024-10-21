package com.homebrew.gifsygrid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.homebrew.gifsygrid.R
import com.homebrew.gifsygrid.databinding.GridFragmentBinding
import com.homebrew.gifsygrid.db.AppDatabase
import com.homebrew.gifsygrid.network.Service
import com.homebrew.gifsygrid.ui.adapter.GifGridAdapter
import com.homebrew.gifsygrid.ui.util.GridSpacing
import com.homebrew.gifsygrid.ui.viewmodel.GifViewModelFactory
import com.homebrew.gifsygrid.ui.viewmodel.GridFragmentViewModel
import com.homebrew.gifsygrid.ui.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.collectLatest

class GridFragment : Fragment() {
    private lateinit var gridAdapter: GifGridAdapter
    private lateinit var gridFragmentBinding: GridFragmentBinding
    private lateinit var  gridFragmentViewModel : GridFragmentViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gridFragmentBinding = GridFragmentBinding.inflate(inflater, container, false)
        return gridFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        val database = AppDatabase.getInstance(context)
        val apiService = Service.create()

        val factory = GifViewModelFactory(database, apiService, context.applicationContext)
        gridFragmentViewModel = ViewModelProvider(this, factory).get(GridFragmentViewModel::class.java)

       gridAdapter = GifGridAdapter(context,
            onItemClick = { positon,list ->

                sharedViewModel.updateGifList(gridAdapter.snapshot().items)

                val bundle = Bundle().apply {
                    putInt("position", positon)
                }

                findNavController().navigate(
                    R.id.action_gridFragment_to_fullScreenFragment,
                    bundle
                )
            },
            onDeleteClick = { gifEntity ->
               gridFragmentViewModel.markGifAsDeleted(gifEntity)
                gridAdapter.refresh()
            }
        )

        gridFragmentBinding.searchButton.setOnClickListener {
            val query = gridFragmentBinding.searchBar.text.toString()
            gridFragmentViewModel.searchGifs(query)
        }

        gridFragmentBinding.GifsRecycler.adapter = gridAdapter
      //  gridFragmentBinding.GifsRecycler.addItemDecoration(GridSpacing(2,150,true))
        gridFragmentBinding.GifsRecycler.layoutManager = GridLayoutManager(requireContext(),2)


        lifecycleScope.launchWhenStarted {
           gridFragmentViewModel.gifs.collectLatest { pagingData ->
                gridAdapter.submitData(pagingData)
               gridAdapter.refresh()
            }
        }
    }
}