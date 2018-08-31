package com.example.framgianguyenvanthanhd.music_professional.screens.home.slide

import com.example.framgianguyenvanthanhd.music_professional.data.SlideDataSource
import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide
import com.example.framgianguyenvanthanhd.music_professional.data.repository.SlideRepository

/**
 * Created by admin on 8/30/2018.
 */
class SlidePresenter : SlideContract.Prensenter {
    private lateinit var slideView: SlideContract.View
    private lateinit var slideRepository: SlideRepository

    init {
        slideRepository = SlideRepository.getInstance()
    }

    override fun setView(view: SlideContract.View) {
        slideView = view
    }

    override fun onStart() {
        slideView.setPresenter(this)
    }

    override fun onStop() {
    }

    override fun requestGetSlides() {
        slideRepository.getSlide(object : SlideDataSource.OnResultGetSlide {
            override fun onSuccess(slides: List<Slide>?) {
                if (slides != null) slideView.showSlidesSuccess(slides)
            }

            override fun onFailure(t: Throwable?) {
                if (t != null) slideView.showSlidesFailure(t)
            }

        })
    }

}