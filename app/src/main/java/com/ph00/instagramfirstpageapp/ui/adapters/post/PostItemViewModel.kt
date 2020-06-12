package com.ph00.instagramfirstpageapp.ui.adapters.post

import com.example.delegateadapter.delegate.diff.IComparableItem
import com.ph00.instagramfirstpageapp.data.models.Post


class PostItemViewModel(val post: Post) :
    IComparableItem {

    override fun id(): Any = post.id

    override fun content() = post
}

