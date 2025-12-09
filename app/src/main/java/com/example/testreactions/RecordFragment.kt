package com.example.testreactions

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
private lateinit var viewModel: LoginViewModel


class RecordFragment : Fragment(R.layout.fragment_record) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_record_to_home)
            //findNavController().popBackStack()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        RecordsRepository.addResult(viewModel.name.value, viewModel.time.value)
        //RecordsRepository.addResult("Liza", 190)
        //RecordsRepository.addResult("Artem", 250)

        val records = RecordsRepository.getTopResults()

        val adapter = RecordAdapter(records)
        recyclerView.adapter = adapter

    }
}
