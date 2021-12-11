package id.ac.unhas.todolistapp.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.myapplication.utils.CustomeProgressDialog
import id.ac.unhas.todolistapp.utils.Util
import id.ac.unhas.todolistapp.R
import id.ac.unhas.todolistapp.model.Repository
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by Praveen John on 11/12/2021
 * Fragment for login
 * */
class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private val TAG = "LoginFragment"

    var customeProgressDialog: CustomeProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customeProgressDialog = CustomeProgressDialog(requireContext())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_continue.setOnClickListener {
            btn_continue.setOnClickListener {
                if (Util.isEmailValid(email_text.text.toString())) {
                    customeProgressDialog?.show()
                    viewModel.login(email_text.text.toString(), passText.text.toString())
                } else {
                    Toast.makeText(requireContext(), "Email not valid", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Repository.isLogin.observe(viewLifecycleOwner) { isLogged ->
            if (isLogged) {
                customeProgressDialog?.dismiss()
                val action = LoginFragmentDirections.loginSuccess()
                findNavController().navigate(action)
            }

        }

    }


}