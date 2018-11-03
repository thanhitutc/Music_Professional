package com.example.framgianguyenvanthanhd.music_professional.screens.home.slide

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.Utils.Constants
import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide
import kotlinx.android.synthetic.main.fragment_slide.*

/**
 * Created by admin on 8/25/2018.
 */
class SlideFragment : Fragment(), SlideContract.View {
    private lateinit var presenter: SlideContract.Prensenter
    private var currentPosition: Int = 0
    private lateinit var handler: Handler
    private lateinit var slideAdapter: SlidePagerAdapter

    override fun showSlidesSuccess(slides: List<Slide>) {
        slideAdapter = SlidePagerAdapter(slides)
        slide_pager.adapter = slideAdapter
        slide_indicator.setViewPager(slide_pager)
        update()
    }

    override fun showSlidesFailure(t: Throwable) {
        Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
    }

    override fun setPresenter(presenter: SlideContract.Prensenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_slide, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        handler = Handler()
        presenter = SlidePresenter()
        presenter.setView(this)
        presenter.onStart()
        presenter.requestGetSlides()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun update() {
        handler.postDelayed(infinitySlideRunable, Constants.DELAY_SLIDE)
    }

    private var infinitySlideRunable = object : Runnable {
        override fun run() {
            if (slide_pager == null) {
                return
            }
            currentPosition++
            if (slide_pager.currentItem++ >= slideAdapter.count - 1) {
                currentPosition = 0
            }
            slide_pager.setCurrentItem(currentPosition, true)
            update()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentPosition = 0
    }
}