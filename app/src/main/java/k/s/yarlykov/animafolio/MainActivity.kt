package k.s.yarlykov.animafolio

import android.os.Bundle
import android.transition.Scene
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import k.s.yarlykov.animafolio.ui.MenuListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

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

        override fun onDrawerStateChanged(newState: Int) {
        }

        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        prepareTransition()
        initFab()
        initDrawerLayout()
        initRecyclerView()


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )

        val navHostFragment = navController
        if(navHostFragment is NavController) {
            setupActionBarWithNavController(navHostFragment, appBarConfiguration)
            navView.setupWithNavController(navHostFragment)

        }
    }

    private fun initDrawerLayout() {
        // Установить обработчик движения шторки
        drawerLayout.addDrawerListener(drawerListener)
    }

    private fun initFab() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navController)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Загрузить Transition и Сцены.
     * Первую Сцену вывести на экран.
     */
    private fun prepareTransition() {
        transition = TransitionInflater.from(this)
            .inflateTransition(R.transition.navigation_cap_transition)

        sceneExit = Scene.getSceneForLayout(navViewCap, R.layout.scene_drawer_header_1, this)
        scene1Enter = Scene.getSceneForLayout(navViewCap, R.layout.scene_drawer_header_2, this)
        sceneExit.enter()
    }

    private fun initRecyclerView() {
        with(recyclerMenu) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MenuListAdapter(this@MainActivity, ITEMS)
        }
    }
}
