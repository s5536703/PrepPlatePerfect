package com.example.prepplateperfect.ui.organiser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.prepplateperfect.databinding.FragmentOrganiserBinding

class OrganiserFragment : Fragment() {

    private var _binding: FragmentOrganiserBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val organiserViewModel =
            ViewModelProvider(this).get(OrganiserViewModel::class.java)

        _binding = FragmentOrganiserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textOrganiser
        organiserViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}