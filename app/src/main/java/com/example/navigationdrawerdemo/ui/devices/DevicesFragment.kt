package com.example.navigationdrawerdemo.ui.devices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationdrawerdemo.R
import com.example.navigationdrawerdemo.databinding.FragmentDevicesBinding
import com.example.navigationdrawerdemo.models.Device
import com.example.navigationdrawerdemo.ui.devices.adapter.DevicesAdapter
import com.example.navigationdrawerdemo.util.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DevicesFragment : Fragment() {

    private val devicesViewModel: DevicesViewModel by viewModels()
    private var _binding: FragmentDevicesBinding? = null
    private lateinit var adapter: DevicesAdapter
    private lateinit var deviceList: List<Device>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDevicesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)
        adapter = DevicesAdapter { device: Device ->
            run {
                val action = DevicesFragmentDirections.actionNavDevicesToDeviceDetails(device)
                findNavController().navigate(action)
            }
        }

        binding.rvDevices.layoutManager = LinearLayoutManager(activity)
        binding.rvDevices.adapter = adapter

        devicesViewModel.onLoad()

        devicesViewModel.res.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    binding.rvDevices.visibility = View.VISIBLE
                    it.data?.let { response ->
                        deviceList = response.devices
                        adapter.submitList(deviceList)
                    }
                }
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.rvDevices.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    binding.rvDevices.visibility = View.VISIBLE
                    Snackbar.make(root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                }
            }
        })
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filter(newText) }
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList: ArrayList<Device> = ArrayList()

        // running a for loop to compare elements.
        for (item in deviceList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.submitList(filteredList)
        }
    }
}