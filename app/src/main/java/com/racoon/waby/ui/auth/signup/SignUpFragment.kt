package com.racoon.waby.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.racoon.waby.R
import com.racoon.waby.data.repository.UserRepositoryImp
import com.racoon.waby.databinding.FragmentSignUpBinding
import com.racoon.waby.domain.usecases.authuser.AuthUserUseCaseImpl
import com.racoon.waby.ui.auth.login.LoginVMFactory
import com.racoon.waby.ui.auth.login.LoginViewModel

class SignUpFragment : Fragment() {

    private lateinit var EMAIL: String
    private lateinit var PASSWD: String
    private val GOOGLE_SIGN_IN = 10


    //private val viewModel by viewModels<SignUpViewModel>()
    private val  viewModel by viewModels<SignUpViewModel> {
        SignUpVMFactory(AuthUserUseCaseImpl(UserRepositoryImp()))
    }


    //ViewBiding
    private  var _binding: FragmentSignUpBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            setUp()
            setUpViewModel()
            binding.progressBar.visibility = View.GONE

        }
        /*binding.googleButton.setOnClickListener {
            setUpGoogle()
        }*/
    }

    /*private fun setUpGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_token))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(requireActivity(),googleConf)
        googleClient.signOut()

        startActivityForResult(googleClient.signInIntent,GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {

                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_signUpFragment_to_registerUserFragment)
                        }else {
                            Toast.makeText(context, R.string.login_error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }catch (e: ApiException) {
                println("Google exception")
            }

        }
    }*/

    private fun setUp() {
        val email = binding.emailEditText.text.toString()
        val passwd = binding.passwordEditText.text.toString()
        val passwdRepeat = binding.repeatPasswordEditText.text.toString()

        viewModel.create(email, passwd,passwdRepeat)

        EMAIL = email
        PASSWD = passwd

    }

    private fun setUpViewModel() {
        with(viewModel) {
            successLD.observe(viewLifecycleOwner) {

                openEmailVerify()

                activity?.also {
                    Toast.makeText(context,R.string.signup_success,Toast.LENGTH_SHORT).show()
                }

            }
            errorLD.observe(viewLifecycleOwner) {
                activity?.also {
                    Toast.makeText(context,R.string.signup_error,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openEmailVerify() {
        val bundle = bundleOf(
            "email" to EMAIL,
            "passwd" to PASSWD,

        )
        findNavController().navigate(R.id.action_signUpFragment_to_verifyEmailFragment,bundle)
    }
}