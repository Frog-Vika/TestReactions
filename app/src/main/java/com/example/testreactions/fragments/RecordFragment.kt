package com.example.testreactions.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testreactions.R
import com.example.testreactions.RecordAdapter
import com.example.testreactions.ViewModel
import com.example.testreactions.data.RecordsRepository
import kotlinx.coroutines.launch

class RecordFragment : Fragment(R.layout.fragment_record) {

    private lateinit var viewModel: ViewModel
    private lateinit var repo: RecordsRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repo = RecordsRepository(requireContext())
        viewModel = ViewModelProvider(requireActivity())[ViewModel::class.java]

        val btnBack = view.findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_record_to_home)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            val name = viewModel.name.value.orEmpty()
            val time = viewModel.time.value ?: 0L

            if (name.isNotBlank() && time > 0L) {
                repo.addResult(name, time)
            }

            val records = repo.getTop10()
            recyclerView.adapter = RecordAdapter(records)
        }
    }
}