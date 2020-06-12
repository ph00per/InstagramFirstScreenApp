package com.ph00.instagramfirstpageapp.ui.adapters.ad

import com.example.delegateadapter.delegate.diff.IComparableItem
import com.ph00.instagramfirstpageapp.data.models.Ad

class AdItemViewModel(val ad: Ad) :
    IComparableItem {

    override fun id(): Any = ad.id

    override fun content() = ad


}