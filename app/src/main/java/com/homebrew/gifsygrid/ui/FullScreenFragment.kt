package com.homebrew.gifsygrid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.homebrew.gifsygrid.databinding.FullScreenFragmentBinding
import com.homebrew.gifsygrid.ui.adapter.FullScreenPagerAdapter
import com.homebrew.gifsygrid.ui.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

class FullScreenFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FullScreenFragmentBinding
    private lateinit var adapter: FullScreenPagerAdapter
    private var initialPosition: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FullScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            initialPosition = it.getInt("position", 0)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.gifList.collect { gifList ->
                    if (gifList.isNotEmpty()) {
                        adapter = FullScreenPagerAdapter(gifList)
                        binding.viewPager.adapter = adapter
                        binding.viewPager.setCurrentItem(initialPosition, false)
                    } else {
                        Toast.makeText(requireContext(), "No GIFs to display", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}