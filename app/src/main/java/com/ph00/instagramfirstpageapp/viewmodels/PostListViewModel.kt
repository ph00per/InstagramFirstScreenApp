package com.ph00.instagramfirstpageapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delegateadapter.delegate.diff.IComparableItem
import com.ph00.instagramfirstpageapp.data.repositories.PostsRepository
import com.ph00.instagramfirstpageapp.ui.adapters.AdItemViewModel
import com.ph00.instagramfirstpageapp.ui.adapters.PostItemViewModel
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
        Log.d("Loading content", "Loading content")
        _state.value = ViewState.LOADING
        viewModelScope.launch(Default) {
            try {
                _contentList.postValue(mutableListOf<IComparableItem>().apply {
                    addAll(repository.getAllPosts().map { PostItemViewModel(it) }.shuffled())
                    addAll(repository.getAllAds().map { AdItemViewModel(it) }.shuffled())
                    shuffle()
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