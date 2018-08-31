package com.example.framgianguyenvanthanhd.music_professional.screens.home.slide

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide
import kotlinx.android.synthetic.main.fragment_slide.*

/**
 * Created by admin on 8/25/2018.
 */
class SlideFragment : Fragment(), SlideContract.View {
    private lateinit var presenter: SlideContract.Prensenter

    override fun showSlidesSuccess(slides: List<Slide>) {
        var slideAdapter = SlidePagerAdapter(slides)
        slide_pager.adapter = slideAdapter
        slide_indicator.setViewPager(slide_pager)
        slide_pager.setCurrentItem(0, true)
    }

    override fun showSlidesFailure(t: Throwable) {
    }

    override fun setPresenter(presenter: SlideContract.Prensenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_slide, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter = SlidePresenter()
        presenter.setView(this)
        presenter.onStart()
        presenter.requestGetSlides()
        super.onViewCreated(view, savedInstanceState)
    }
}