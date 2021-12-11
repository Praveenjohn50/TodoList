package id.ac.unhas.todolistapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import id.ac.unhas.todolistapp.R

/**
 * Created by Praveen John on 11/12/2021
 * Main Activity Entry Point
 * */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {
        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.todoFragment,
                R.id.addTodoFragment,
                R.id.editTodoFragment
            )
            .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(this, navController, appBarConfiguration)
    }
}