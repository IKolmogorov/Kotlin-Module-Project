
fun main(){

    val app: NoteApp = NoteApp()

    app.start()

}

class NoteApp(){

    companion object {
        // Запрещено вызывать пункт меню с номером "0"
        const val MIN_USER_MENU_ITEM_KEY = 1
    }

    fun start() {

        var needToGoOn: Boolean = true
        val rootDirectory: Directory = Directory("Root", null)
        val warehouse: Warehouse = Warehouse(mutableListOf(), rootDirectory)
        val menuHandler: MenuHandler = MenuHandler(warehouse)

        var menu: Menu = menuHandler.getMenuByCurrentFile(warehouse, rootDirectory)

        while (needToGoOn) {
            menu.show()

            val menuItemKey = getIntFromUserInput(
                "Номер пункта меню",
                NoteApp.MIN_USER_MENU_ITEM_KEY,
                menu.menuItems.size)

            menu = menuHandler.handleUserChoice(menu.menuItems.get(menuItemKey)!!)

            if (menu.menuItems.size ==0)
                needToGoOn = false

        }
    }
}

