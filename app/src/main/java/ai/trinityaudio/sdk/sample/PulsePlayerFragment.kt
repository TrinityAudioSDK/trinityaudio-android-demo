package ai.trinityaudio.sdk.sample

import ai.trinityaudio.sdk.TrinityAudioPulse
import ai.trinityaudio.sdk.TrinityPulsePlayerListener
import ai.trinityaudio.sdk.sample.databinding.FragmentPulsePlayerBinding
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class PulsePlayerFragment :
    Fragment(),
    TrinityPulsePlayerListener {
    private lateinit var binding: FragmentPulsePlayerBinding
    private lateinit var trinityAudio: TrinityAudioPulse

    override fun onDestroy() {
        super.onDestroy()
        trinityAudio.invalidate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPulsePlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            trinityAudio = TrinityAudioPulse.create(requireContext(), this@PulsePlayerFragment)

            val autoplay = arguments?.getBoolean(Constant.KEY_AUTOPLAY,true)
            trinityAudio.autoPlay = autoplay ?: true

            playButton.setOnClickListener {
                trinityAudio.play()
            }

            pauseButton.setOnClickListener {
                trinityAudio.pause(trinityAudio.playerId)
            }

            trinityAudio.render(
                rootView = binding.scrollRootView,
                playerView = binding.trinityPlayerView,
                unitId = "2900019020",
                playlistURL = "https://delivery.trinityaudio.ai/v1/playlist/6t9itl7j/rss",
            )
        }
    }

    override fun trinityDidReceivePostMessage(
        service: TrinityAudioPulse,
        message: Map<String, *>,
    ) {
    }

    @SuppressLint("SetTextI18n")
    override fun trinityOnPlayerReady(
        service: TrinityAudioPulse,
        playerId: String,
    ) {
        binding.playerIdText.text = "PlayerId: $playerId"
    }

    override fun trinityOnBrowseMode(
        service: TrinityAudioPulse,
        toggled: Boolean,
        expectedHeight: Int,
    ) {
        // Not implemented yet
    }
}
