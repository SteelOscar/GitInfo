package ru.steeloscar.gitinfo.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.interfaces.StartActivityInterface
import ru.steeloscar.gitinfo.databinding.ActivityStartBinding
import ru.steeloscar.gitinfo.view.mainActivity.MainActivity
import ru.steeloscar.gitinfo.viewModel.StartViewModel

class StartActivity : AppCompatActivity(), StartActivityInterface.View {

    private lateinit var viewModel: StartViewModel

    private val APP_PREFERENCES = "sharedPreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityStartBinding>(this, R.layout.activity_start)

        viewModel = StartViewModel(this)
        binding.viewModel = viewModel
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun startIntent(intent: Intent?) {
        if (intent == null) startActivity(Intent(this, MainActivity::class.java))
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
