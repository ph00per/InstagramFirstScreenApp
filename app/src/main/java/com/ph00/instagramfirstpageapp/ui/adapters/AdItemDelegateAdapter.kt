package com.ph00.instagramfirstpageapp.ui.adapters

import com.example.delegateadapter.delegate.KDelegateAdapter
import com.ph00.instagramfirstpageapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_ad.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class AdItemDelegateAdapter(
    private val onItemClick: ((String) -> Unit)
) :
    KDelegateAdapter<AdItemViewModel>(), KoinComponent {

    private val picasso by inject<Picasso>()

    override fun getLayoutId() = R.layout.item_ad

    override fun isForViewType(items: MutableList<*>, position: Int) =
        items[position] is AdItemViewModel

    override fun onBind(item: AdItemViewModel, viewHolder: KViewHolder) {
        picasso.load(item.ad.imageUrl).into(viewHolder.ad_image)
        viewHolder.ad_image.setOnClickListener { onItemClick(item.ad.imageUrl) }
    }
}