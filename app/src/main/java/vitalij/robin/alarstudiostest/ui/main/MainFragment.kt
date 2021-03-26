package vitalij.robin.alarstudiostest.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import vitalij.robin.alarstudiostest.AlarStudiosApplication
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.common.extensions.*
import vitalij.robin.alarstudiostest.databinding.FragmentMainBinding
import vitalij.robin.alarstudiostest.ui.common.BaseFragment
import vitalij.robin.alarstudiostest.ui.main.adapter.MainAdapter
import vitalij.robin.alarstudiostest.ui.map.MapBoxActivity
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private lateinit var viewModel: MainViewModel

    private val mainAdapter =
        MainAdapter { model ->
            startActivity(MapBoxActivity.newInstance(context, model))
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        dataBinding.lifecycleOwner = this@MainFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(MainViewModel::class.java).apply {
                observeToProgressBar(this@MainFragment)
                observeToError(this@MainFragment)

                openDialog = {
                    context?.showDialog(it)
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AlarStudiosApplication.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        initializeList()

        arguments?.let {
            viewModel.loadData(it.getString(CODE) ?: "")
        }

        viewModel.liveData.observe(viewLifecycleOwner, {
            mainAdapter.submitList(it)
        })

        setToolbarTitle(R.string.main_screen)
    }

    private fun setListeners() {
        setErrorResolveButtonClick {
            viewModel.refresh()
        }
    }

    private fun initializeList() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mainAdapter
    }

    companion object {
        private const val CODE = "code"

        fun newInstance(code: String?) = MainFragment().apply {
            arguments = Bundle().apply {
                putString(CODE, code)
            }
        }
    }
}