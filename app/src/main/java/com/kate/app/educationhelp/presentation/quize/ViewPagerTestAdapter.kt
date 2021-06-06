package com.kate.app.educationhelp.presentation.quize

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.textfield.TextInputLayout
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

            val view: View

            if (it.type == 1) {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.quize_test_item, null)
                bindCommonTestView(view, it, position)
            } else if (it.type == 2) {
                view =
                    LayoutInflater.from(context).inflate(R.layout.quize_test_item_second_type, null)
                bindSecondTypeTestView(view, it, position)
            } else {
                view =
                    LayoutInflater.from(context).inflate(R.layout.quize_test_item_third_type, null)
                bindThirdTypeTestView(view, it, position)
            }

            listOfViews.add(
                view
            )
        }

        container.addView(listOfViews[position])
        return listOfViews[position]
    }

    private fun bindSecondTypeTestView(
        view: View,
        it: Test,
        position: Int
    ) {
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
            if (view.findViewById<ImageView>(R.id.image_1).tag ==
                "R.drawable.ic_outline_check_box_outline_blank_24"

            ) {
                applySecondTypeAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_1),
                    view.findViewById<ImageView>(R.id.image_1)
                )
            } else {
                removeSecondTypeAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_1),
                    view.findViewById<ImageView>(R.id.image_1)
                )
            }

        }
        view.findViewById<TextView>(R.id.a_2).text = it.answer?.get(1)
        view.findViewById<MaterialCardView>(R.id.a_2_container).setOnClickListener { button ->
            if (view.findViewById<ImageView>(R.id.image_2).tag ==
                "R.drawable.ic_outline_check_box_outline_blank_24"

            )
                applySecondTypeAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_2),
                    view.findViewById<ImageView>(R.id.image_2)
                )
            else
                removeSecondTypeAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_2),
                    view.findViewById<ImageView>(R.id.image_2)
                )
        }
        view.findViewById<TextView>(R.id.a_3).text = it.answer?.get(2)
        view.findViewById<MaterialCardView>(R.id.a_3_container).setOnClickListener { button ->
            if (view.findViewById<ImageView>(R.id.image_3).tag ==
                "R.drawable.ic_outline_check_box_outline_blank_24"

            )
                applySecondTypeAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_3),
                    view.findViewById<ImageView>(R.id.image_3)
                ) else
                removeSecondTypeAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_3),
                    view.findViewById<ImageView>(R.id.image_3)
                )
        }
        view.findViewById<TextView>(R.id.a_4).text = it.answer?.get(3)
        view.findViewById<MaterialCardView>(R.id.a_4_container).setOnClickListener { button ->
            if (view.findViewById<ImageView>(R.id.image_4).tag ==
                "R.drawable.ic_outline_check_box_outline_blank_24"

            )
                applySecondTypeAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_4),
                    view.findViewById<ImageView>(R.id.image_4)
                )
            else
                removeSecondTypeAnswer(
                    it,
                    button as MaterialCardView,
                    view.findViewById<TextView>(R.id.a_4),
                    view.findViewById<ImageView>(R.id.image_4)
                )
        }

        view.findViewById<MaterialButton>(R.id.confirmSecondType).setOnClickListener { button ->
            confirmSecondAnswer(
                it, view,
                view.findViewById<TextView>(R.id.a_1),
                view.findViewById<TextView>(R.id.a_2),
                view.findViewById<TextView>(R.id.a_3),
                view.findViewById<TextView>(R.id.a_4),
                view.findViewById<MaterialCardView>(R.id.a_1_container),
                view.findViewById<MaterialCardView>(R.id.a_2_container),
                view.findViewById<MaterialCardView>(R.id.a_3_container),
                view.findViewById<MaterialCardView>(R.id.a_4_container),
                view.findViewById<ImageView>(R.id.image_1),
                view.findViewById<ImageView>(R.id.image_2),
                view.findViewById<ImageView>(R.id.image_3),
                view.findViewById<ImageView>(R.id.image_4)
            )
        }
    }

    private var thirdAnswer = ""

    private fun bindThirdTypeTestView(
        view: View,
        it: Test,
        position: Int
    ) {
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

        view.findViewById<TextInputLayout>(R.id.enterField).editText?.doAfterTextChanged { text ->
            thirdAnswer = text?.toString() ?: ""
        }

        view.findViewById<MaterialButton>(R.id.confirmThirdTypAnswer).setOnClickListener { button ->
            confirmThirdTypeAnswer(it, view)
        }
    }

    private fun confirmThirdTypeAnswer(it: Test, view: View) {
        if(it.correct_answer == thirdAnswer){
            view.findViewById<TextInputLayout>(R.id.enterField).boxStrokeColor = context.resources.getColor(R.color.green)
        } else {
            view.findViewById<TextInputLayout>(R.id.enterField).boxStrokeColor = context.resources.getColor(R.color.red)
            view.findViewById<TextInputLayout>(R.id.enterField).error = "Не правильно"
        }
        currentAnswer =
            Triple(
                it,
                it.correct_answer == thirdAnswer,
                it.bonus ?: 0
            )

        confirmAnswerAndGoToNext()
    }

    private fun bindCommonTestView(
        view: View,
        it: Test,
        position: Int
    ) {
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
    }

    private fun confirmAnswerAndGoToNext() {
        currentAnswer?.let { it1 -> confirm.invoke(it1) }
        next.invoke()
    }

    private var secondAnswer: String = ""
    private fun applySecondTypeAnswer(
        it: Test,
        button: MaterialCardView,
        answer: TextView,
        icon: ImageView
    ) {
        secondAnswer =
            if (secondAnswer.isBlank()) secondAnswer.plus(answer.text) else secondAnswer.plus(", ")
                .plus(answer.text)

        icon.setImageResource(R.drawable.ic_outline_check_box_24)
        icon.tag = "R.drawable.ic_outline_check_box_24"
    }

    private fun removeSecondTypeAnswer(
        it: Test,
        button: MaterialCardView,
        answer: TextView,
        icon: ImageView
    ) {

        secondAnswer = if (secondAnswer.contains(", ".plus(answer.text))) {
            secondAnswer.replace(", ".plus(answer.text), "")
        } else if ((secondAnswer.contains(answer.text.toString().plus(", ")))) {
            secondAnswer.replace(answer.text.toString().plus(", "), "")
        } else {
            secondAnswer.replace(answer.text.toString(), "")
        }

        icon.setImageResource(R.drawable.ic_outline_check_box_outline_blank_24)
        icon.tag = "R.drawable.ic_outline_check_box_outline_blank_24"
    }

    private fun confirmSecondAnswer(
        it: Test,
        view: View,
        answer1: TextView,
        answer2: TextView,
        answer3: TextView,
        answer4: TextView,
        button1: MaterialCardView,
        button2: MaterialCardView,
        button3: MaterialCardView,
        button4: MaterialCardView,
        icon1: ImageView,
        icon2: ImageView,
        icon3: ImageView,
        icon4: ImageView,

        ) {
        currentAnswer =
            Triple(
                it,
                it.correct_answer?.split(", ")?.containsAll(secondAnswer.split(", ")) == true,
                it.bonus ?: 0
            )

        if (it.correct_answer?.contains(answer1.text) == true) {
            button1.strokeColor = ContextCompat.getColor(context, R.color.green)
            icon1.setImageResource(R.drawable.ic_outline_check_box_24_green)

        } else {
            button1.strokeColor = ContextCompat.getColor(context, R.color.red)
            icon1.setImageResource(R.drawable.ic_outline_check_box_24_red)
        }

        if (it.correct_answer?.contains(answer2.text) == true) {
            button2.strokeColor = ContextCompat.getColor(context, R.color.green)
            icon2.setImageResource(R.drawable.ic_outline_check_box_24_green)
        } else {
            button2.strokeColor = ContextCompat.getColor(context, R.color.red)
            icon2.setImageResource(R.drawable.ic_outline_check_box_24_red)
        }

        if (it.correct_answer?.contains(answer3.text) == true) {
            button3.strokeColor = ContextCompat.getColor(context, R.color.green)
            icon3.setImageResource(R.drawable.ic_outline_check_box_24_green)
        } else {
            button3.strokeColor = ContextCompat.getColor(context, R.color.red)
            icon3.setImageResource(R.drawable.ic_outline_check_box_24_red)
        }

        if (it.correct_answer?.contains(answer4.text) == true) {
            icon4.setImageResource(R.drawable.ic_outline_check_box_24_green)
            button4.strokeColor = ContextCompat.getColor(context, R.color.green)
        } else {
            button4.strokeColor = ContextCompat.getColor(context, R.color.red)
            icon4.setImageResource(R.drawable.ic_outline_check_box_24_red)
        }

        confirmAnswerAndGoToNext()
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