package vitalij.robin.alarstudiostest.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_login.*
import vitalij.robin.alarstudiostest.AlarStudiosApplication
import vitalij.robin.alarstudiostest.ui.main.MainActivity
import vitalij.robin.alarstudiostest.R
import vitalij.robin.alarstudiostest.common.extensions.*
import vitalij.robin.alarstudiostest.databinding.FragmentLoginBinding
import vitalij.robin.alarstudiostest.ui.common.BaseFragment
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        dataBinding.lifecycleOwner = this@LoginFragment
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory)
            .get(LoginViewModel::class.java).apply {
                observeToProgressBar(this@LoginFragment, activity as AppCompatActivity)

                openDialog = {
                    context?.showDialog(it)
                }

                openMainActivity = {
                    activity?.finish()
                    context?.startActivity(
                        MainActivity.newInstance(context, it)
                    )
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
        setClickButton(false)
    }

    private fun setListeners() {
        logInButton.setOnClickListener {
            context?.closeKeyboard(view)
            viewModel.login()
        }

        loginInputEditText.afterTextChanged {
            setClickButton(
                it.length >= resources.getInteger(R.integer.login_min_length) && passwordInputEditText.text.toString().length >= resources.getInteger(
                    R.integer.login_min_length
                )
            )
        }
        passwordInputEditText.afterTextChanged {
            setClickButton(
                it.length >= resources.getInteger(R.integer.login_min_length) && loginInputEditText.text.toString().length >= resources.getInteger(
                    R.integer.login_min_length
                )
            )
        }

        passwordInputEditText.setOnActionDoneEditorActionListener(view) {
            context?.closeKeyboard(view)
            if (loginInputEditText.text.toString().length >= resources.getInteger(R.integer.login_min_length)
                && passwordInputEditText.text.toString().length >= resources.getInteger(
                    R.integer.login_min_length
                )
            ) {
                viewModel.login()
            }
        }
    }

    private fun setClickButton(click: Boolean) {
        logInButton.isEnabled = click
        logInButton.isClickable = click
    }

    companion object {

        fun newInstance() = LoginFragment()
    }
}