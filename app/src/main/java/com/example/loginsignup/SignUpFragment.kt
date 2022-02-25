package com.example.loginsignup

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.loginsignup.databinding.FragmentSignUpBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels {
        UserViewModelFactory(
            (activity?.applicationContext as UserApplication).database
                .userDao()
        )
    }

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.name.text.toString(),
            binding.email.text.toString(),
            binding.password.text.toString()
        )
    }

    private fun newUser() {
        val name = binding.name.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        runBlocking {
            launch {
                val user = viewModel.checkUser(email, password)
                if (isEntryValid()) {
                    if (user == null) {
                        viewModel.newUser(
                            name, email, password
                        )
                        Toast.makeText(activity, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "User Already Exists", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.name.setText("")
        binding.email.setText("")
        binding.password.setText("")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            signUpButton.setOnClickListener {
                newUser()
            }
            logIn.setOnClickListener {
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
        }
    }
}