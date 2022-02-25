package com.example.loginsignup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.loginsignup.databinding.FragmentLoginBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels {
        UserViewModelFactory(
            (activity?.applicationContext as UserApplication).database.userDao()
        )
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun login() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()
        runBlocking {
            launch {
                val user = viewModel.checkUser(email, password)
                if (user != null) {
                    Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            signUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
            loginButton.setOnClickListener {
                login()
            }
        }
    }
}