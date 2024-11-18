package com.kewirausahaan.okgaspartner.ui.addkost

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kewirausahaan.okgaspartner.R
import com.kewirausahaan.okgaspartner.databinding.FragmentAddKostBinding
import java.io.IOException
import java.util.UUID
import kotlin.text.get
import kotlin.text.toIntOrNull

class AddKostFragment : Fragment() {

    private var _binding: FragmentAddKostBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null
    private lateinit var storageRef: StorageReference
    private lateinit var viewModel: AddKostViewModel

    private var pickMediaLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        // Callback is invoked after the user selects media
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            selectedImageUri = uri
            // Tampilkan gambar di ImageView menggunakan Glide
            Glide.with(this)
                .load(uri)
                .into(binding.bookingKostImage)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddKostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddKostViewModel::class.java)

        storageRef = FirebaseStorage.getInstance().reference

        // Setup Spinner
        val genderAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        )
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.inputAddGender.adapter = genderAdapter

        binding.imageButton.setOnClickListener {
            // Launch the photo picker
            pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.addButton.setOnClickListener {
            val kostId = UUID.randomUUID().toString() // Generate random ID
            val kostImage = kostId // Nama gambar kost (tanpa ekstensi)
            val kostName = binding.inputAddName.text.toString()
            val kostLocation = binding.inputAddLocation.text.toString()
            val kostGender = binding.inputAddGender.selectedItem.toString()
            val kostPrice = binding.inputAddPrice.text.toString().toIntOrNull() ?: 0
            val kostDescription = binding.inputAddDescription.text.toString()

            if (kostImage.isNotEmpty() && kostName.isNotEmpty() && kostLocation.isNotEmpty() && kostGender.isNotEmpty() && kostPrice != null && kostDescription.isNotEmpty()) {
                val kost = Kost(
                    kostImage, kostName, kostLocation, kostGender, kostPrice, kostDescription
                )

                // Upload gambar ke Firebase Storage jika selectedImageUri tidak null
                selectedImageUri?.let { imageUri ->
                    val imageRef = storageRef.child("kost/$kostImage.png") // Path di Firebase Storage dengan ekstensi
                    imageRef.putFile(imageUri)
                        .addOnSuccessListener {
                            // Gambar berhasil diupload
                            Toast.makeText(requireContext(), "Gambar berhasil diupload", Toast.LENGTH_SHORT).show()

                            // Tambahkan data ke Firebase Realtime Database setelah gambar berhasil diupload
                            val database = FirebaseDatabase.getInstance()
                            val kostRef = database.getReference("kost").child(kostId)

                            kostRef.setValue(kost)
                                .addOnSuccessListener {
                                    Toast.makeText(requireContext(), "Kost berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_navigation_add_kost_to_navigation_home) // Navigasi kembali
                                }
                                .addOnFailureListener {
                                    Toast.makeText(requireContext(), "Gagal menambahkan kost", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .addOnFailureListener {
                            // Gagal mengupload gambar
                            Toast.makeText(requireContext(), "Gagal mengupload gambar", Toast.LENGTH_SHORT).show()
                        }
                } ?: run {
                    // Jika selectedImageUri null, tampilkan pesan error
                    Toast.makeText(requireContext(), "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showImage() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}