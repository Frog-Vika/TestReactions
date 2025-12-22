package com.example.testreactions

import android.os.Bundle
import android.os.SystemClock
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.testreactions.databinding.FragmentPlayBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlin.random.Random
//import java.util.logging.Handler

private lateinit var viewModel: LoginViewModel

class PlayFragment : Fragment(R.layout.fragment_play) {
    //private lateinit var viewModel: LoginViewModel

    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!

    private var startTime = 0L
    private var signalStarted = false
    private var waitingForSignal = false

    //private val handler = Handler(Looper.getMainLooper())


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
            if (signalStarted) {
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
            when {
                !waitingForSignal && ! signalStarted -> {
                   startWaitingForSignal()
                }
                waitingForSignal && ! signalStarted -> {
                    errorClick()
                }
                signalStarted -> {
                    stopTimer()
                }
            }
        }
    }

    private fun startWaitingForSignal() {
        waitingForSignal = true
        signalStarted = false

        binding.root.setBackgroundColor(
            requireContext().getColor(R.color.white)
        )

        binding.Text.text = "Ждте сигнала ..."

        val delay = Random.nextLong(1000, 5000)

        handler.postDelayed({showSignal()}, delay)
    }

    private fun showSignal() {
        waitingForSignal = false
        signalStarted = true

        binding.root.setBackgroundColor(
            requireContext().getColor(R.color.signal_color)
        )

        binding.Text.text = "ЖМИ!!!"

        /*handler.postDelayed({
            binding.root.setBackgroundColor(
                requireContext().getColor(R.color.white)
            )
        }, 100)*/


        startTime = SystemClock.elapsedRealtime()
        handler.post(updateRunnable)
    }

    private fun errorClick() {
        waitingForSignal = false
        //signalStarted = false

        handler.removeCallbacksAndMessages(null)

        binding.root.setBackgroundColor(
            requireContext().getColor(R.color.error_color)
        )
        binding.Text.text = "Слишком рано"

        startTime = SystemClock.elapsedRealtime()

        handler.postDelayed({resetGame()}, 1000)
    }

    private fun resetGame() {
        waitingForSignal = false
        signalStarted = false

        binding.root.setBackgroundColor(
            requireContext().getColor(R.color.white)
        )

        binding.Text.text = "Нажми, чтобы начать"
    }
    private fun stopTimer() {
        signalStarted = false
        //handler.removeCallbacks(updateTimerRunnable)

        val finalTime = SystemClock.elapsedRealtime() - startTime
        if ((viewModel.time.value > finalTime) or (viewModel.time.value == 0L)) {viewModel.time.value = finalTime}
        binding.root.setBackgroundColor(
            requireContext().getColor(R.color.white)
        )
        binding.Text.text = "Результат: ${finalTime} мс\nНажми снова чтобы начать"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
