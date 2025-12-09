package com.example.testreactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.testreactions.databinding.FragmentRecordBinding
import androidx.lifecycle.ViewModelProvider
import com.example.testreactions.databinding.FragmentPlayBinding

private lateinit var viewModel: LoginViewModel
class RecordFragment : Fragment(R.layout.fragment_record) {

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        /*viewModel.name.observe(viewLifecycleOwner) { playerName ->
            //binding.recordText.text = "Играем $playerName"
        }
        viewModel.time.observe(viewLifecycleOwner) { playerTime ->
            //binding.recordText.text = "Играем $playerName"
        }*/

        val playerName = viewModel.name.value
        val playerTime = viewModel.time.value
        binding.recordText.text = "$playerName: $playerTime мс"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}