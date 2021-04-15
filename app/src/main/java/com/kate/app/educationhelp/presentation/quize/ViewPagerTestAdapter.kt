package com.kate.app.educationhelp.presentation.quize

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.domain.models.Test


class ViewPagerTestAdapter(
    private var context: Context,
    private var data: List<Test>,
    private val nextClick: (position: Int) -> Unit
) :
    PagerAdapter() {

    @SuppressLint("CutPasteId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val listOfViews = mutableListOf<View>()

        data.forEach {

            val view = LayoutInflater.from(context)
                .inflate(R.layout.quize_test_item, null)

            view.findViewById<TextView>(R.id.descr).text = it.title

            view.findViewById<TextView>(R.id.name).text = "Test ${position + 1}"

            view.findViewById<TextView>(R.id.a_1).text = it.answer?.first()
            view.findViewById<TextView>(R.id.a_2).text = it.answer?.get(1)
            view.findViewById<TextView>(R.id.a_3).text = it.answer?.get(2)
            view.findViewById<TextView>(R.id.a_4).text = it.answer?.get(3)

            view.findViewById<TextView>(R.id.bonus).text = "Bonuses ${it.bonus?.toString()}"

            listOfViews.add(
                view
            )
        }

        container.addView(listOfViews[position])
        return listOfViews[position]
    }

    private fun nextClick(position: Int) {
        nextClick.invoke(position)
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}