package com.example.mebelkayu.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mebelkayu.Aplication.FurnitureApp
import com.example.mebelkayu.R
import com.example.mebelkayu.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val furnitureViewModel: FurnitureViewModel by viewModels {
        FurnitureViewModelFactory((applicationContext as FurnitureApp).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FurnitureListAdapter {furniture ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(furniture)
            findNavController().navigate(action)
        }

        binding.dataRecyclerView.adapter = adapter
        binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
        furnitureViewModel.allFurnitures.observe(viewLifecycleOwner) { furnitures ->
            furnitures.let {
                if (furnitures.isEmpty()){
                    binding.emptyTextView.visibility =View.VISIBLE
                    binding.illustrationimageView.visibility =View.VISIBLE
                }else{
                    binding.emptyTextView.visibility =View.GONE
                    binding.illustrationimageView.visibility =View.GONE
                }
                adapter.submitList(furnitures)
            }
        }

        binding.addFAB.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
           findNavController().navigate(action)

    }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}