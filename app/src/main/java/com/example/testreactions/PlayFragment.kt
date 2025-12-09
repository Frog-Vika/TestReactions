package com.example.testreactions

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testreactions.databinding.FragmentPlayBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlin.random.Random

private lateinit var viewModel: LoginViewModel

class PlayFragment : Fragment(R.layout.fragment_play) {
    //private lateinit var viewModel: LoginViewModel

    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!

    private var startTime = 0L
    private var isRunning = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val handler = android.os.Handler()
    private val updateRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                val current = SystemClock.elapsedRealtime() - startTime
                binding.Text.text = "Прошло: ${current} мс"
                handler.postDelayed(this, 16) // обновление 60 раз в секунду
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val name = viewModel.name.value
        //binding.nameText.text = "Привет ${name}"

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        viewModel.name.observe(viewLifecycleOwner) { playerName ->
            binding.nameText.text = "Играем $playerName"
        }

        binding.goHomeButton.setOnClickListener {
            //findNavController().popBackStack()
            findNavController().navigate(R.id.action_play_to_home)
        }

        binding.root.setOnClickListener {
            if (!isRunning) {
                // Первый клик → стартуем таймер
                startTime = SystemClock.elapsedRealtime()
                isRunning = true

                //binding.button2.text = "Stop"
                //binding.Text.text = "Время идёт"
                binding.Text.text = "Прошло: 0 мс"

                handler.post(updateRunnable)


                /*Toast.makeText(requireContext(), "Таймер запущен!", Toast.LENGTH_SHORT).show()*/

            } else {
                // Второй клик → стоп
                val endTime = SystemClock.elapsedRealtime()
                val finalTime = endTime - startTime // разница в миллисекундах

                binding.Text.text = "Итоговое время: ${finalTime} мс"
                if ((viewModel.time.value > finalTime) or (viewModel.time.value == 0L)) {viewModel.time.value = finalTime}
                //viewModel.time.value = finalTime
                //binding.button2.text = "Start"

                /*Toast.makeText(
                    requireContext(),
                    "Прошло: ${diff} мс",
                    Toast.LENGTH_LONG
                ).show()*/

                isRunning = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
