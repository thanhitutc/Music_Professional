package com.example.framgianguyenvanthanhd.music_professional.screens.home.slide

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.framgianguyenvanthanhd.music_professional.R
import com.example.framgianguyenvanthanhd.music_professional.data.model.Slide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_slide_container.view.*

/**
 * Created by FRAMGIA\nguyen.van.thanhd on 31/08/2018.
 */
class SlidePagerAdapter(slides: List<Slide>?) : PagerAdapter() {
    private var slides: List<Slide>? = null

    init {
        this.slides = slides
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return slides?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val view = LayoutInflater.from(container?.context).inflate(R.layout.layout_slide_container, container, false)
        Picasso.with(container?.context).load(slides?.get(position)?.imageSlide).into(view.img_slide_background)
        Picasso.with(container?.context).load(slides?.get(position)?.imageSong).into(view.img_slide_image)
        view.txt_slide_name_song.text = slides?.get(position)?.nameSong.toString()
        view.txt_slide_content.text = slides?.get(position)?.content.toString()
        container?.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View?)
    }
}