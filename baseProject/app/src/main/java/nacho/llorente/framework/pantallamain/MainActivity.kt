package nacho.llorente.framework.pantallamain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import nacho.llorente.R
import nacho.llorente.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import nacho.llorente.BuildConfig

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var primeraVez: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val apiKey = BuildConfig.GEMINI_API_KEY
        primeraVez = true
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}