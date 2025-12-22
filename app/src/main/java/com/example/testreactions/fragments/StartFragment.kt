package com.example.testreactions.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testreactions.R
import com.example.testreactions.ViewModel

class StartFragment : Fragment(R.layout.fragment_start) {

    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity())[ViewModel::class.java]


        val NameInput = view.findViewById<EditText>(R.id.NameInput)
        val loginButton = view.findViewById<Button>(R.id.button1)

        loginButton.setOnClickListener {
            viewModel.setName(NameInput.text.toString())
            //viewModel.name.value = NameInput.text.toString()
            //viewModel.time.value = 0L

            findNavController().navigate(R.id.action_start_to_home)


            /*if (viewModel.authorize()) {
                findNavController().navigate(R.id.action_start_to_play)
            }*/
        }
    }

}