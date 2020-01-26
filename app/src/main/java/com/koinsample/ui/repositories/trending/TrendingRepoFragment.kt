package com.koinsample.ui.repositories.trending


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import com.koinsample.BR
import com.koinsample.R
import com.koinsample.api.helper.AlertErrorHandler
import com.koinsample.api.helper.Result
import com.koinsample.api.helper.handleNoInternet
import com.koinsample.api.model.RepoModel
import com.koinsample.databinding.FragmentTrendingRepoBinding
import com.koinsample.databinding.ItemTrendingRepoBinding
import com.koinsample.utils.hide
import com.koinsample.utils.liveadapter.LiveAdapter
import com.koinsample.utils.setUpToolbar
import com.koinsample.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class TrendingRepoFragment : Fragment() {

    private val mViewModel: TrendingRepoViewModel by viewModel()
    private lateinit var mBinding: FragmentTrendingRepoBinding
    private var mCurrentExpandedPos = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_trending_repo, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setup toolbar
        setUpToolbar(mBinding.toolbar, getString(R.string.title_trending)) {
            showOverFlowMenu()
        }

        // set error handler
        setupErrorHandler()

        // setup repo list
        setupRepoList()

        // add swipe to refresh
        setupSwipeToRefresh()

    }

    private fun setupSwipeToRefresh() {
        mBinding.swipeRefreshList.setOnRefreshListener {
            mViewModel.fetchTrendingRepos()
        }
    }

    private fun setupRepoList() {
        context?.let { ctx ->
            // set divider
            val itemDecorator =
                DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(ctx, R.drawable.divider)?.let {
                itemDecorator.setDrawable(it)
            }

            mBinding.listTrendingRepo.addItemDecoration(itemDecorator)
        }

        // setup liveAdapter
        LiveAdapter(mViewModel.repoList, BR.repo)
            .map<RepoModel, ItemTrendingRepoBinding>(R.layout.item_trending_repo) {
                onBind { holder ->

                    // show/hide description as per mCurrentExpandedPos
                    if (holder.adapterPosition == mCurrentExpandedPos) {
                        holder.binding.repoDescContainer.show()
                    } else {
                        holder.binding.repoDescContainer.hide()
                    }
                }
                onClick { holder ->

                    if (mCurrentExpandedPos == holder.adapterPosition) {
                        mCurrentExpandedPos =
                            -1 //same pos is click which is already expanded then collapse that one
                        updateClickedItem(holder.adapterPosition)
                    } else {
                        val oldPosition = mCurrentExpandedPos // store old expanded pos
                        mCurrentExpandedPos = holder.adapterPosition // store current clicked pos
                        updateClickedItem(oldPosition)
                        updateClickedItem(mCurrentExpandedPos)
                    }

                }
            }
            .into(mBinding.listTrendingRepo)
    }

    /**
     * set error handling mechanism
     */
    private fun setupErrorHandler() {
        mBinding.layoutState.errorHandler = AlertErrorHandler(context) { isNetworkAvailable ->
            // manage no internet
            handleNoInternet(mBinding.layoutNoInternet, isNetworkAvailable) {
                // call fetch repository
                mViewModel.fetchTrendingRepos()
            }
        }

        @Suppress("UNCHECKED_CAST")
        mBinding.result = mViewModel.reposResult as LiveData<Result<Any>>
    }

    /**
     * update the Ui for current Item
     */
    private fun updateClickedItem(position: Int) {
        mBinding.listTrendingRepo.adapter?.notifyItemChanged(position)
    }

    /**
     * show overflow menu with the sort by options
     * where user get the sorted result by star and name
     */
    private fun showOverFlowMenu() {
        context?.let { ctx ->
            val popup = PopupMenu(ctx, mBinding.toolbar.imageOverflowMenu)
            popup.menuInflater.inflate(R.menu.menu_overflow, popup.menu)
            popup.show()

            // set click
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    R.id.btnSortByStar -> {
                        mViewModel.sortByStar() // repo sorted by the stars
                    }

                    R.id.btnSortByName -> {
                        mViewModel.sortByName() // repo sorted by the names
                    }

                }
                return@setOnMenuItemClickListener true
            }
        }
    }
}
