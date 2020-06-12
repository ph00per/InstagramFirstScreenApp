package com.ph00.instagramfirstpageapp.ui.adapters.stories

import com.example.delegateadapter.delegate.diff.IComparableItem
import com.ph00.instagramfirstpageapp.ui.adapters.stories.story.StoryItemViewModel

class StoriesItemViewModel(val list: List<IComparableItem>) :
    IComparableItem {

    override fun id(): Any = list.hashCode()

    override fun content() = list


}