package com.example.testreactions

import android.os.Bundle
import android.os.SystemClock
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testreactions.databinding.FragmentPlayBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlin.random.Random


class PlayFragment : Fragment(R.layout.fragment_play) {
    private lateinit var viewModel: ViewModel

    private var _binding: FragmentPlayBinding? = null
    private val binding get() = _binding!!

    private var startTime = 0L

    private var numberOfClick = 0
    private var numberOfClickRef = 0
    private var signalStarted = false
    private var waitingForSignal = false
    private var gameMode = false

    private val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    //private val handler = android.os.Handler()
    private val updateRunnable = object : Runnable {
        override fun run() {
            if (signalStarted) {
                val current = SystemClock.elapsedRealtime() - startTime
                binding.timeText.text = "Прошло: ${current} мс"
                handler.postDelayed(this, 16) // обновление 60 раз в секунду
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val name = viewModel.name.value
        //binding.nameText.text = "Привет ${name}"

        viewModel = ViewModelProvider(requireActivity())[ViewModel::class.java]

        viewModel.name.observe(viewLifecycleOwner) { playerName ->
            binding.nameText.text = "Играем $playerName"
        }

        binding.goHomeButton.setOnClickListener {
            //findNavController().popBackStack()
            findNavController().navigate(R.id.action_play_to_home)
        }

        binding.oneClickButton.setOnClickListener {
            gameMode = false
            updateButtonStyles()
            resetGame()
        }

        binding.nClickButton.setOnClickListener {
            gameMode = true
            updateButtonStyles()
            resetGame()
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
                    numberOfClick++
                    binding.Text.apply {
                        setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 50f)
                        setTextColor(android.graphics.Color.RED)
                        text = "$numberOfClick из $numberOfClickRef"
                    }
                    if (numberOfClick == numberOfClickRef) {stopTimer()}
                    //stopTimer()
                }
            }
        }
    }


    private fun updateButtonStyles() {
        if (gameMode) {
            binding.nClickButton.alpha = 1.0f
            binding.oneClickButton.alpha = 0.5f
        } else {
            binding.nClickButton.alpha = 0.5f
            binding.oneClickButton.alpha = 1.0f
        }
    }

    private fun startWaitingForSignal() {
        waitingForSignal = true
        signalStarted = false

        binding.modeButtonsContainer.visibility = View.GONE
        binding.goHomeButton.visibility = View.GONE
        binding.Text2.visibility = View.GONE


        binding.root.setBackgroundColor(
            requireContext().getColor(R.color.white)
        )

        binding.Text.apply {
            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 30f)
            setTextColor(android.graphics.Color.BLACK)
            text = "Ждте сигнала ..."
        }

        binding.timeText.text = ""

        val delay = Random.nextLong(1000, 5000)

        handler.postDelayed({showSignal()}, delay)
    }

    private fun showSignal() {
        waitingForSignal = false
        signalStarted = true

        numberOfClickRef = if (gameMode) Random.nextInt(1, 6) else 1
        numberOfClick = 0

        binding.root.setBackgroundColor(
            requireContext().getColor(R.color.signal_color)
        )

        binding.Text.apply {
            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 50f)
            setTextColor(android.graphics.Color.RED)
            if (gameMode) {
                text = "ЖМИ $numberOfClickRef раз!!!"
            }
            else text = "ЖМИ"
        }


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
        binding.Text.apply {
            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 30f)
            setTextColor(android.graphics.Color.BLACK)
            text = "Слишком рано"
        }
        //binding.Text.text = "Слишком рано"

        startTime = SystemClock.elapsedRealtime()

        handler.postDelayed({resetGame()}, 1000)
    }

    private fun resetGame() {
        waitingForSignal = false
        signalStarted = false

        binding.modeButtonsContainer.visibility = View.VISIBLE
        binding.goHomeButton.visibility = View.VISIBLE
        binding.Text2.visibility = View.VISIBLE

        binding.root.setBackgroundColor(
            requireContext().getColor(R.color.white)
        )

        binding.Text.apply {
            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 30f)
            setTextColor(android.graphics.Color.BLACK)
            text = "Нажми чтобы снова начать"
        }

        //binding.Text.text = "Нажми, чтобы начать"
    }
    private fun stopTimer() {
        signalStarted = false

        binding.modeButtonsContainer.visibility = View.VISIBLE
        binding.goHomeButton.visibility = View.VISIBLE
        binding.Text2.visibility = View.VISIBLE

        handler.removeCallbacks(updateRunnable)

        val finalTime = SystemClock.elapsedRealtime() - startTime
        //if ((viewModel.time.value > finalTime) or (viewModel.time.value == 0L)) {viewModel.time.value = finalTime}
        viewModel.saveBestTime(finalTime)
        binding.root.setBackgroundColor(
            requireContext().getColor(R.color.white)
        )
        binding.timeText.text = "Результат: ${finalTime} мс"
        binding.Text.apply {
            setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 30f)
            setTextColor(android.graphics.Color.BLACK)
            text = "Нажми чтобы снова начать"
        }
    }

    override fun onDestroyView() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
        _binding = null
    }
}
