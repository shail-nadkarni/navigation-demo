package com.example.navigationdrawerdemo.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.navigationdrawerdemo.R
import com.example.navigationdrawerdemo.databinding.FragmentDeviceDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceDetailsFragment : Fragment() {

    private lateinit var viewModel: DeviceDetailsViewModel
    private var _binding: FragmentDeviceDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: DeviceDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(DeviceDetailsViewModel::class.java)

        _binding = FragmentDeviceDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val device = args.device

        val tvTitle = binding.tvTitle
        tvTitle.text = context?.getString(R.string.label_title, device.title)

        val tvPrice = binding.tvPrice
        tvPrice.text = context?.getString(R.string.label_price, device.currency, device.price)

        val tvType = binding.tvType
        tvType.text = context?.getString(R.string.label_type, device.type)

        Glide.with(this)
            .load(device.imageUrl)
            .into(binding.ivImage)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}