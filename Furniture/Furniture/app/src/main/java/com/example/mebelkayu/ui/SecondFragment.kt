package com.example.mebelkayu.ui
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mebelkayu.Aplication.FurnitureApp
import com.example.mebelkayu.R
import com.example.mebelkayu.databinding.FragmentSecondBinding
import com.example.mebelkayu.model.Furniture
import com.example.mebelkayu.ui.FurnitureViewModel
import com.example.mebelkayu.ui.FurnitureViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(),OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private lateinit var applicationContext: Context
    private val furnitureViewModel: FurnitureViewModel by viewModels {
        FurnitureViewModelFactory((applicationContext as FurnitureApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var furniture: Furniture? =null
    private lateinit var mMap : GoogleMap

    private var currentLatLang: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map)as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()
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
                        Furniture(0, name.toString(), address.toString(), information.toString(),currentLatLang?.latitude,currentLatLang?.longitude)
                    furnitureViewModel.insert(furniture)
                } else {
                    val furniture = Furniture(
                        furniture?.id!!,
                        name.toString(),
                        address.toString(),
                        information.toString(),
                        currentLatLang?.latitude,currentLatLang?.longitude
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val uiSettings= mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
                  mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude,newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {

    }
    private fun  checkPermission(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            getCurrentLocation()
        }else{
            Toast.makeText(applicationContext, "Akses lokasi ditolak",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null){
                    var latLang = LatLng(location.latitude,location.longitude)
                    currentLatLang = latLang
                    var title = "marker"
                    if (title != null){
                        title = furniture?.name.toString()
                        val newCurrentLocation = LatLng(furniture?.latitude!!,furniture?.longitude!!)
                        latLang= newCurrentLocation
                    }
                    val markerOptions = MarkerOptions()
                        .position(latLang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_living_room_32))
                    mMap.addMarker(markerOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang,15f))
                }
            }
    }

}