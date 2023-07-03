package com.example.mebelkayu.ui
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mebelkayu.Aplication.FurnitureApp
import com.example.mebelkayu.databinding.FragmentSecondBinding
import com.example.mebelkayu.model.Furniture
import com.example.mebelkayu.ui.FurnitureViewModel
import com.example.mebelkayu.ui.FurnitureViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var applicationContext: Context
    private val furnitureViewModel: FurnitureViewModel by viewModels {
        FurnitureViewModelFactory((applicationContext as FurnitureApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var furniture: Furniture? =null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        furniture = args.furniture
        //kita cek jika furniture null maka tampil default nambah mebel baru
        //jika furniture tidak null tampilan seidkit berubah ada tombol hapus dan bah
        if (furniture != null){
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "ubah"
            binding.nameEditText.setText(furniture?.name)
            binding.nameEditText.setText(furniture?.address)
            binding.nameEditText.setText(furniture?.information)
        }
        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        val information = binding.informationEditText.text
        binding.saveButton.setOnClickListener {
            if (name.isEmpty()){
                Toast.makeText(context,"nama tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }else if (address.isEmpty()){
                Toast.makeText(context,"alamat tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }else  if (information.isEmpty()){
                Toast.makeText(context,"type tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }else {
                if (furniture == null) {
                    val furniture =
                        Furniture(0, name.toString(), address.toString(), information.toString())
                    furnitureViewModel.insert(furniture)
                } else {
                    val furniture = Furniture(
                        furniture?.id!!,
                        name.toString(),
                        address.toString(),
                        information.toString()
                    )
                    furnitureViewModel.update(furniture)
                }
                findNavController().popBackStack() //untuk dismiss halaman ini
            }
        }
        binding.deleteButton.setOnClickListener {
            furniture?.let { furnitureViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}