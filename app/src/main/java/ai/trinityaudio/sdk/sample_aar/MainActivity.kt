package ai.trinityaudio.sdk.sample_aar

import ai.trinityaudio.sdk.TrinityAudio
import ai.trinityaudio.sdk.TrinityPlayerListener
import ai.trinityaudio.sdk.TrinityPlayerView
import ai.trinityaudio.sdk.TrinityStates
import android.graphics.Point
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), TrinityPlayerListener {
    private lateinit var playerView: TrinityPlayerView
    private lateinit var rootView: ViewGroup

    private var trinityAudio = TrinityAudio.create(this, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playerView = findViewById(R.id.trinityPlayerView)
        rootView = findViewById(R.id.rootView)
        loadTrinityPlayer()
    }

    private fun loadTrinityPlayer() {
        val unitID = "2900004156"
        val contentURL = "https://demo.trinityaudio.ai/general-demo/demo.html"
        val fabViewTopLeftCoordinates = Point(50, resources.displayMetrics.heightPixels * 2 / 3)
        trinityAudio.render(rootView, playerView, unitID, fabViewTopLeftCoordinates, contentURL)
    }

    override fun trinityOnDetectUpdateForContentHeight(
        service: TrinityAudio,
        height: Float,
        state: TrinityStates,
    ) {}

    override fun trinityDidReceivePostMessage(
        service: TrinityAudio,
        message: Map<String, *>,
    ) {}
}
