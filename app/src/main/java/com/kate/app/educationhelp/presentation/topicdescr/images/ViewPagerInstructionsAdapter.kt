package com.kate.app.educationhelp.presentation.topicdescr.images

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.google.firebase.storage.FirebaseStorage
import com.kate.app.educationhelp.R
import com.squareup.picasso.Picasso


class ViewPagerInstructionsAdapter(
    private var context: Context,
    private var data: List<String>
) :
    PagerAdapter() {

    @SuppressLint("CutPasteId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val listOfViews = mutableListOf<View>()

        data.forEach {

            val view = LayoutInflater.from(context)
                .inflate(R.layout.image_pager_item, null)

            if (data.isNotEmpty() && data[position].isNotBlank())
                FirebaseStorage.getInstance().reference.child(data[position]).downloadUrl.addOnSuccessListener {
                    Picasso.get().load(it).into(view.findViewById<ImageView>(R.id.image))
                }

            listOfViews.add(
                view
            )
        }

        container.addView(listOfViews[position])
        return listOfViews[position]
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