package k.s.yarlykov.animafolio.ui.menu

import android.content.Context
import android.view.MenuInflater
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import k.s.yarlykov.animafolio.R
import k.s.yarlykov.animafolio.domain.MenuItemData

class MenuController(
    private val context: Context,
    private val adapter: MenuListAdapter,
    private val navController: NavController,
    private val drawerLayout: DrawerLayout
) {

    var currentActivePosition : Int = 0

    private val model: List<MenuItemData>

    init {
        model = mapMenuToCustomModel()
        adapter.updateModel(model)
    }

    // Данные для RecyclerView берутся из файла меню. Считываем оттуда
    // title и иконку и формируем список из MenuItemData. Элементы меню
    // без иконок игнорируем. В результате полечается адаптер между
    // ресурсом меню и данными для формирования кастомного меню из RecyclerView.
    // !!! id элементов меню и id фрагментов в файле навигации одинаковые !!!

    private fun mapMenuToCustomModel(): List<MenuItemData> {
        val li = mutableListOf<MenuItemData>()

        with(MenuBuilder(context)) {
            MenuInflater(context).inflate(R.menu.activity_main_drawer, this)

            (0 until this.size()).forEach { i ->
                this.getItem(i).icon?.let { drawable ->
                    with(this.getItem(i)) {
                        li.add(
                            i, MenuItemData(
                                position = i,
                                navId = itemId,
                                isActive = false,
                                title = title.toString(),
                                icon = drawable,
                                clickHandler = this@MenuController::navigateToDestination
                            )
                        )
                    }
                }
            }
        }

        return li
    }

    private fun navigateToDestination(position: Int) {

        if(position == currentActivePosition) {
            return
        }

        // Пометить текущий активный элемент меню и сбросить метку с предыдущего активного
        model.indices.forEach { i ->
            model[i].isActive = (i == position)
        }
        currentActivePosition = position

        // Сменить отображаемый фрагмент
        navController.navigate(model[position].navId)

        // Задвинуть обратно NavigationView
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}