package com.ph00.instagramfirstpageapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter
import com.ph00.instagramfirstpageapp.R
import com.ph00.instagramfirstpageapp.ui.adapters.AdItemDelegateAdapter
import com.ph00.instagramfirstpageapp.ui.adapters.PostItemDelegateAdapter
import com.ph00.instagramfirstpageapp.viewmodels.PostListViewModel
import kotlinx.android.synthetic.main.fragment_posts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostListFragment : Fragment() {

    private val viewModel: PostListViewModel by viewModel()

    private val diffAdapter by lazy {
        DiffUtilCompositeAdapter.Builder()
            .add(PostItemDelegateAdapter())
            .add(AdItemDelegateAdapter { Toast.makeText(activity, it, Toast.LENGTH_SHORT).show() })
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
                    Toast.makeText(activity, "Ошибка!", Toast.LENGTH_SHORT).show()
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
}