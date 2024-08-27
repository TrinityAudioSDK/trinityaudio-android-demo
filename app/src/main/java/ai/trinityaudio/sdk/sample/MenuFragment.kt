package ai.trinityaudio.sdk.sample

import ai.trinityaudio.sdk.sample.databinding.FragmentMenuBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            mainUsageBt.setOnClickListener {
                val navController = findNavController()
                navController.navigate(R.id.mainUsage)
            }
            autoPlayUsageBt.setOnClickListener {
                val navController = findNavController()
                val bundle =
                    Bundle().apply {
                        putBoolean("autoplay", true)
                    }
                navController.navigate(R.id.mainUsage, bundle)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }
}
