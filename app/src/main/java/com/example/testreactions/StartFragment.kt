package com.example.testreactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import android.widget.EditText
import android.widget.Button
import androidx.navigation.fragment.findNavController

class StartFragment : Fragment(R.layout.fragment_start) {

    private lateinit var viewModel: LoginViewModel

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
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]


        val NameInput = view.findViewById<EditText>(R.id.NameInput)
        val loginButton = view.findViewById<Button>(R.id.button1)

        loginButton.setOnClickListener {
            viewModel.name.value = NameInput.text.toString()
            viewModel.time.value = 0L

            findNavController().navigate(R.id.action_start_to_home)


            /*if (viewModel.authorize()) {
                findNavController().navigate(R.id.action_start_to_play)
            }*/
        }
    }

}