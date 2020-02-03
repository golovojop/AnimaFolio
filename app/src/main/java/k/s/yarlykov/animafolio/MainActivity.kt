package k.s.yarlykov.animafolio

import android.os.Bundle
import android.transition.Scene
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
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
import k.s.yarlykov.animafolio.domain.MenuItemData
import k.s.yarlykov.animafolio.ui.MenuListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ITEMS = 6
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

        val navController = findNavController(R.id.nav_controller)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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
        val navController = findNavController(R.id.nav_controller)
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

        // Оказалось ненужным
//        val navIds = with(resources.obtainTypedArray(R.array.nav_id)) {
//            val li = mutableListOf<Int>()
//            (0 until length()).forEach { i ->
//                li.add(i, getResourceId(i, 0))
//            }
//            recycle()
//            li.toTypedArray()
//        }

        with(recyclerMenu) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MenuListAdapter(this@MainActivity, extractMenuTitles())
        }
    }

    // Данные для RecyclerView берутся из файла меню. Считываем оттуда
    // title и иконку и формируем список из MenuItemData. Элементы меню
    // без иконок игнорируем. В результате полечается адаптер между
    // ресурсом меню и данными для формирования кастомного меню из RecyclerView.
    private fun extractMenuTitles(): List<MenuItemData> {
        val li = mutableListOf<MenuItemData>()

        with(MenuBuilder(this)) {
            MenuInflater(this@MainActivity).inflate(R.menu.activity_main_drawer, this)

            (0 until this.size()).forEach { i ->
                this.getItem(i).icon?.let {drawable ->
                    li.add(i, MenuItemData(this.getItem(i).title.toString(), drawable))
                }
            }
        }

        return li
    }
}
