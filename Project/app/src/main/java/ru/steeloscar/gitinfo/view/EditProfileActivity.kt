package ru.steeloscar.gitinfo.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import ru.steeloscar.gitinfo.R
import ru.steeloscar.gitinfo.databinding.ActivityEditProfileBinding
import ru.steeloscar.gitinfo.interfaces.EditProfileActivityViewInterface
import ru.steeloscar.gitinfo.viewModel.EditProfileViewModel

class EditProfileActivity : AppCompatActivity(), EditProfileActivityViewInterface.View {

    private var viewModel: EditProfileViewModel = EditProfileViewModel(this)
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.swipeRefreshEdit.isEnabled = false
        binding.editProfileToolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_30dp)
        setSupportActionBar(binding.editProfileToolbar)
        binding.editProfileToolbar.setNavigationOnClickListener {
            finish()
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.save_edit -> {
                viewModel.editProfile()
            }
        }
        return true
    }

    override fun toastMessage() {
        Toast.makeText(this, resources.getString(R.string.failed_update), Toast.LENGTH_SHORT).show()
    }

    override fun finishActivity() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroyDisposable()
    }
}
