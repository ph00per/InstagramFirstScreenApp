package com.ph00.instagramfirstpageapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delegateadapter.delegate.diff.IComparableItem
import com.ph00.instagramfirstpageapp.data.models.Ad
import com.ph00.instagramfirstpageapp.data.models.Post
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
        _state.value = ViewState.LOADING
        viewModelScope.launch(Default) {
            try {
                _contentList.postValue(
                    prepareListOfData(
                        repository.getAllPosts(),
                        repository.getAllAds(),
                        repository.getAllStories()
                    )
                )
                _state.postValue(ViewState.DEFAULT)
            } catch (e: Exception) {
                _event.postValue(Event(ViewEvent.ERROR))
                delay(4000)
                withContext(Main) { loadContent() }
            }
        }
    }

    private fun prepareListOfData(
        listPosts: List<Post>,
        listAds: List<Ad>,
        listStories: List<Story>
    ) =
        mutableListOf<IComparableItem>().apply {
            addAll(listPosts.map {
                PostItemViewModel(it)
            }.shuffled())
            //Every 4th item is ad
            listAds.forEachIndexed { index, ad ->
                add((1 + index) * 4, AdItemViewModel(ad))
            }
            //Insert list of stories as first item of content list
            add(0, StoriesItemViewModel(
                mutableListOf<IComparableItem>().apply {
                    //TODO refactor your story
                    //Insert your story as first item of list of stories
                    add(
                        0, MyStoryItemViewModel(
                            Story(
                                id = 421,
                                userName = "Your Story",
                                storyImgUrl = "https://pic.rutube.ru/user/2f/bd/2fbd4f63dcbb6262b677517ad6c566f8.png"
                            )
                        )
                    )
                    addAll(listStories.map { StoryItemViewModel(it) }
                        .shuffled())
                }
            )
            )
        }

    enum class ViewState {
        DEFAULT,
        LOADING
    }

    enum class ViewEvent {
        ERROR
    }
}