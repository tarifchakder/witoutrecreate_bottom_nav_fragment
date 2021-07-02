package com.tarif.savefragment_state.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tarif.savefragment_state.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private lateinit var v : FragmentGalleryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = FragmentGalleryBinding.inflate(inflater,container,false)
        return v.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getText = v.galleryText.text.toString()
        var count = 0
        v.button.setOnClickListener {
            count += 1
            v.galleryText.text = "$getText = $count"
        }
    }
}