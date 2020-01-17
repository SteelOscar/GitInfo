package ru.steeloscar.gitinfo.view.mainActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentPagerAdapter
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.interfaces.MainActivityInterface
import ru.steeloscar.gitinfo.databinding.ActivityMainBinding
import ru.steeloscar.gitinfo.view.StartActivity
import ru.steeloscar.gitinfo.view.mainActivity.adapters.MainViewPagerAdapter
import ru.steeloscar.gitinfo.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), MainActivityInterface.View {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private val APP_PREFERENCES = "sharedPreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_person_white_40dp)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        viewModel = MainViewModel(this)

        binding.viewpager.adapter =
            MainViewPagerAdapter(
                supportFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
        binding.viewModel = viewModel
        binding.icGithub.setOnLongClickListener {
            Toast.makeText(this, resources.getString(R.string.git_hub), Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun startIntent(intent: Intent?) {
        startActivity(intent)
    }

    override fun getSharedPreferences(): SharedPreferences =
        getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sign_out -> {
                viewModel.signOut()
                startActivity(Intent(this, StartActivity::class.java))
            }
        }
        return true
    }
}