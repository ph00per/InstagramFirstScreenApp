package com.ph00.instagramfirstpageapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.google.android.material.snackbar.Snackbar
import com.ph00.instagramfirstpageapp.R
import com.ph00.instagramfirstpageapp.ui.adapters.ad.AdItemDelegateAdapter
import com.ph00.instagramfirstpageapp.ui.adapters.post.PostItemDelegateAdapter
import com.ph00.instagramfirstpageapp.ui.adapters.stories.StoriesItemDelegateAdapter
import com.ph00.instagramfirstpageapp.ui.adapters.stories.story.StoryItemDelegateAdapter
import com.ph00.instagramfirstpageapp.ui.adapters.stories.your_story.MyStoryItemDelegateAdapter
import com.ph00.instagramfirstpageapp.viewmodels.PostListViewModel
import kotlinx.android.synthetic.main.fragment_posts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostListFragment : Fragment() {

    private val viewModel: PostListViewModel by viewModel()

    private val diffAdapter by lazy {
        DiffUtilCompositeAdapter.Builder()
            .add(PostItemDelegateAdapter())
            .add(AdItemDelegateAdapter { showText(it) })
            .add(
                StoriesItemDelegateAdapter(
                    diffAdapter = DiffUtilCompositeAdapter.Builder()
                        .add(StoryItemDelegateAdapter { showText(it) })
                        .add(MyStoryItemDelegateAdapter { showText(it) })
                        .build(),
                    rvLayoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                )
            )
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_posts, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()

        viewModel.event.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                if (event == PostListViewModel.ViewEvent.ERROR) {
                    showText("Ошибка!")
                }
            }
        })
    }

    private fun initViews() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                PostListViewModel.ViewState.DEFAULT -> progress_bar.visibility = View.GONE
                PostListViewModel.ViewState.LOADING -> progress_bar.visibility = View.VISIBLE
                else -> null
            }
        })
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = diffAdapter
        }
        viewModel.contentList.observe(viewLifecycleOwner, Observer {
            diffAdapter.swapData(it)
        })
    }

    private fun showText(msg: String) {
        Snackbar.make(main_layout, msg, Snackbar.LENGTH_SHORT).show()
    }
}