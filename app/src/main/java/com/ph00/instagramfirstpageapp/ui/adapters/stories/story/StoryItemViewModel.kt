package com.ph00.instagramfirstpageapp.ui.adapters.stories.story

import com.example.delegateadapter.delegate.diff.IComparableItem
import com.ph00.instagramfirstpageapp.data.models.Ad
import com.ph00.instagramfirstpageapp.data.models.Story

class StoryItemViewModel(val story: Story) :
    IComparableItem {

    override fun id(): Any = story.id

    override fun content() = story


}