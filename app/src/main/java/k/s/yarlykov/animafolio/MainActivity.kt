package k.s.yarlykov.animafolio

import android.os.Bundle
import android.transition.Scene
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ITEMS = 5
    }

    private lateinit var transition: Transition
    private lateinit var sceneExit: Scene
    private lateinit var scene1Enter: Scene

    private lateinit var appBarConfiguration: AppBarConfiguration

    // Обработчик движения шторки навигации
    private val drawerListener = object : DrawerLayout.DrawerListener {
        override fun onDrawerClosed(drawerView: View) {
            recyclerMenu.adapter?.notifyDataSetChanged()
            TransitionManager.go(sceneExit, transition)
        }

        override fun onDrawerOpened(drawerView: View) {
            TransitionManager.go(scene1Enter, transition)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Подготовоить ресурсы для анимации
        prepareTransition()

        // Активировать тубар
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        // FAB
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


        with(drawer_layout) {
            addDrawerListener(drawerListener)

        }
        val drawerLayout = drawer_layout

        // Установить обработчик движения шторки
        drawerLayout.addDrawerListener(drawerListener)


        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Загрузить Transition и Сцены.
     * Первую Сцену вывести на экран.
     */
    private fun prepareTransition() {
        val rootContainer = nav_container

        transition = TransitionInflater.from(this)
            .inflateTransition(R.transition.header_transition)

        sceneExit = Scene.getSceneForLayout(rootContainer, R.layout.scene_drawer_header_1, this)
        scene1Enter = Scene.getSceneForLayout(rootContainer, R.layout.scene_drawer_header_2, this)
        sceneExit.enter()

    }
}
