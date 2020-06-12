package com.ph00.instagramfirstpageapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delegateadapter.delegate.diff.IComparableItem
import com.ph00.instagramfirstpageapp.data.models.Story
import com.ph00.instagramfirstpageapp.data.repositories.PostsRepository
import com.ph00.instagramfirstpageapp.ui.adapters.ad.AdItemViewModel
import com.ph00.instagramfirstpageapp.ui.adapters.post.PostItemViewModel
import com.ph00.instagramfirstpageapp.ui.adapters.stories.StoriesItemViewModel
import com.ph00.instagramfirstpageapp.ui.adapters.stories.story.StoryItemViewModel
import com.ph00.instagramfirstpageapp.ui.adapters.stories.your_story.MyStoryItemViewModel
import com.ph00.instagramfirstpageapp.utils.Event
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostListViewModel(private val repository: PostsRepository) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    val state: LiveData<ViewState> get() = _state

    private val _event = MutableLiveData<Event<ViewEvent>>()
    val event: LiveData<Event<ViewEvent>> get() = _event

    private val _contentList = MutableLiveData<List<IComparableItem>>()
    val contentList: LiveData<List<IComparableItem>> get() = _contentList

    init {
        loadContent()
    }

    private fun loadContent() {
        //TODO:: refactor pls :(
        _state.value = ViewState.LOADING
        viewModelScope.launch(Default) {
            try {
                _contentList.postValue(mutableListOf<IComparableItem>().apply {
                    addAll(repository.getAllPosts().map {
                        PostItemViewModel(
                            it
                        )
                    }.shuffled())
                    addAll(repository.getAllAds().map {
                        AdItemViewModel(
                            it
                        )
                    }.shuffled())
                    shuffle()
                    add(
                        0,
                        StoriesItemViewModel(
                            mutableListOf<IComparableItem>().apply {
                                add(
                                    0, MyStoryItemViewModel(
                                        Story(
                                            id = 421,
                                            userName = "Add story",
                                            storyImgUrl = "https://i.ytimg.com/vi/CKhwQdpCVI4/maxresdefault.jpg"
                                        )
                                    )
                                )
                                addAll(repository.getAllStories().map { StoryItemViewModel(it) })
                            }
                        )
                    )
                })
                _state.postValue(ViewState.DEFAULT)
            } catch (e: Exception) {
                _event.postValue(Event(ViewEvent.ERROR))
                delay(4000)
                withContext(Main) { loadContent() }
            }
        }
    }

    enum class ViewState {
        DEFAULT,
        LOADING
    }

    enum class ViewEvent {
        ERROR
    }
}