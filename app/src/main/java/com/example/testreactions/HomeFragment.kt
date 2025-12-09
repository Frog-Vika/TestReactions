package com.example.testreactions

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testreactions.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider

private lateinit var viewModel: LoginViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        viewModel.name.observe(viewLifecycleOwner) { playerName ->
            binding.nameText.text = "Привет, $playerName"


            binding.homeButton1.setOnClickListener {
                findNavController().navigate(R.id.action_home_to_play)
            }

            binding.homeButton2.setOnClickListener {
                //findNavController().popBackStack()
                findNavController().navigate(R.id.action_home_to_record)
            }

            binding.homeButton3.setOnClickListener {
                //findNavController().popBackStack()
                findNavController().navigate(R.id.action_home_to_start)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}