package ru.steeloscar.gitinfo.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.interfaces.StartActivityViewInterface
import ru.steeloscar.gitinfo.databinding.ActivityStartBinding
import ru.steeloscar.gitinfo.view.mainActivity.MainActivity
import ru.steeloscar.gitinfo.viewModel.StartViewModel

class StartActivity : AppCompatActivity(), StartActivityViewInterface.View {

    private lateinit var viewModel: StartViewModel

    private val APP_PREFERENCES = "sharedPreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.StartTheme)
        super.onCreate(savedInstanceState)
        viewModel = StartViewModel.getInstance(this)
        viewModel.checkToken()
        val binding = DataBindingUtil.setContentView<ActivityStartBinding>(this, R.layout.activity_start)
        binding.viewModel = viewModel
    }

    override fun showToast(message: String) {
        val errorText = when(message){
            "token" -> resources.getString(R.string.no_internet)
            "login" -> resources.getString(R.string.error_login)
            else -> null
        }
        Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show()
    }

    override fun startIntent(intent: Intent?) {
        if (intent == null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else startActivity(intent)
    }

    override fun getSharedPreferences(): SharedPreferences =
        getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun onResume() {
        super.onResume()
        viewModel.onResume(intent.data)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}
