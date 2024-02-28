package com.example.mojivet.nav_drawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView.ScaleType
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.mojivet.R


class Photos : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photos, container, false)

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://i.natgeofe.com/n/abb126f7-eed1-437b-9c40-760fda76032f/05-dog-portraits-FP-Jpegs-5_2x3.jpg","Asongot"))
//        imageList.add(SlideModel("",""))
//        imageList.add(SlideModel("",""))

        imageSlider.setImageList(imageList, com.denzcoskun.imageslider.constants.ScaleTypes.FIT)
        // Inflate the layout for this fragment
        return view
    }
}