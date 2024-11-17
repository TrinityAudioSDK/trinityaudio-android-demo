package ai.trinityaudio.sdk.sample

import ai.trinityaudio.sdk.TrinityAudio
import ai.trinityaudio.sdk.sample.databinding.FragmentMainUsageBinding
import ai.trinityaudio.sdk.tts.TrinityPlayerListener
import ai.trinityaudio.sdk.tts.TrinityStates
import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.json.JSONObject

class MainUsageFragment :
    Fragment(),
    TrinityPlayerListener {
    private lateinit var binding: FragmentMainUsageBinding

    private lateinit var trinityAudio: TrinityAudio
    private var eventLogs = ""

    override fun onDestroy() {
        super.onDestroy()
        trinityAudio.invalidate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainUsageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnPlay.setOnClickListener {
                // Play() only works after the player has started once and is in pause mode
                trinityAudio.play()
            }
            btnPause.setOnClickListener {
                trinityAudio.pause()
                // or trinityAudio.pause(trinityAudio.playerId)
            }
        }

        // Initialize TrinityAudio
        trinityAudio = TrinityAudio.create(requireContext(), this)
        val autoplay = arguments?.getBoolean(Constant.KEY_AUTOPLAY, true)
        trinityAudio.autoPlay = autoplay ?: true

        // Load the Trinity player
        loadTrinityPlayer()
    }

    private fun loadTrinityPlayer() {
        val unitID = "2900004156"
        val contentURL = "https://demo.trinityaudio.ai/general-demo/demo.html"
        val fabViewTopLeftCoordinates = Point(50, resources.displayMetrics.heightPixels * 2 / 3)
        trinityAudio.render(
            binding.rootView,
            binding.trinityPlayerView,
            unitID,
            fabViewTopLeftCoordinates,
            contentURL,
        )
    }

    override fun trinityOnDetectUpdateForContentHeight(
        service: TrinityAudio,
        height: Float,
        state: TrinityStates,
    ) {
        // Handle updates to the content height if needed
    }

    override fun trinityDidReceivePostMessage(
        service: TrinityAudio,
        message: Map<String, *>,
    ) {
        requireActivity().runOnUiThread {
            try {
                eventLogs = eventLogs.plus("\n \n").plus(JSONObject(message).toString(4))
                binding.events.text = eventLogs
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun trinityOnPlayerReady(
        service: TrinityAudio,
        playerId: String,
    ) {
        requireActivity().runOnUiThread {
            // After this method invoked. We could access the playerId later with `trinityAudio.playerId`
            binding.playerIdLb.text = "PlayerId: $playerId"
        }
    }
}
