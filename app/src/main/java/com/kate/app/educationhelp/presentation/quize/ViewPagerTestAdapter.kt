package com.kate.app.educationhelp.presentation.quize

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.domain.models.Test


class ViewPagerTestAdapter(
    private var context: Context,
    private var data: List<Test>,
    private val confirm: (Triple<Test, Boolean, Int>) -> Unit,
    private val next: () -> Unit
) :
    PagerAdapter() {

    private var currentAnswer: Triple<Test, Boolean, Int>? = null

    @SuppressLint("CutPasteId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        val listOfViews = mutableListOf<View>()

        data.forEach {


            val view = LayoutInflater.from(context)
                .inflate(R.layout.quize_test_item, null)


            val radius = 32f
            view.findViewById<ShapeableImageView>(R.id.image).shapeAppearanceModel =
                view.findViewById<ShapeableImageView>(R.id.image).shapeAppearanceModel
                    .toBuilder()
                    .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                    .setBottomRightCorner(CornerFamily.ROUNDED, radius)
                    .build()

            view.findViewById<TextView>(R.id.descr).text = it.title

            view.findViewById<TextView>(R.id.name).text = context.resources.getString(
                R.string.test_number_from_all,
                (position + 1).toString(),
                data.size.toString()
            )

            view.findViewById<TextView>(R.id.a_1).text = it.answer?.first()
            view.findViewById<MaterialCardView>(R.id.a_1_container).setOnClickListener { button ->
                applyAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_1),
                    view.findViewById<ImageView>(R.id.image_1)
                )

            }
            view.findViewById<TextView>(R.id.a_2).text = it.answer?.get(1)
            view.findViewById<MaterialCardView>(R.id.a_2_container).setOnClickListener { button ->
                applyAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_2),
                    view.findViewById<ImageView>(R.id.image_2)
                )
            }
            view.findViewById<TextView>(R.id.a_3).text = it.answer?.get(2)
            view.findViewById<MaterialCardView>(R.id.a_3_container).setOnClickListener { button ->
                applyAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_3),
                    view.findViewById<ImageView>(R.id.image_3)
                )
            }
            view.findViewById<TextView>(R.id.a_4).text = it.answer?.get(3)
            view.findViewById<MaterialCardView>(R.id.a_4_container).setOnClickListener { button ->
                applyAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_4),
                    view.findViewById<ImageView>(R.id.image_4)
                )
            }

            listOfViews.add(
                view
            )
        }

        container.addView(listOfViews[position])
        return listOfViews[position]
    }

    private fun confirmAnswerAndGoToNext() {
        currentAnswer?.let { it1 -> confirm.invoke(it1) }
        next.invoke()
    }

    private fun applyAnswer(
        it: Test,
        button: MaterialCardView,
        answer: TextView,
        icon: ImageView
    ) {
        currentAnswer =
            Triple(it, answer.text == it.correct_answer, it.bonus ?: 0)
        confirmAnswerAndGoToNext()

        if (answer.text == it.correct_answer) {
            button.strokeColor = ContextCompat.getColor(context, R.color.green)
            icon.setImageResource(R.drawable.ic_outline_correct_circle_24)
        } else {
            button.strokeColor = ContextCompat.getColor(context, R.color.red)
            icon.setImageResource(R.drawable.ic_outline_incorrect_24)
        }

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