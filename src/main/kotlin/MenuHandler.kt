
class MenuHandler(var warehouse: Warehouse)
{
    fun handleUserChoice(menuItem: MenuItem): Menu
    {
        //println(menuItem.command)

        val curCommand: String = menuItem.command

        when (curCommand)
        {
             "OnCreate(DIRECTORY)" -> {
                val fileName: String = getStringFromUserInput("Название архива")
                val newFile: File = createFileInWarehouse(warehouse,
                    DataTypes.DIRECTORY, fileName, menuItem.file)

                return getMenuByCurrentFile(warehouse, newFile)
            }
            "OnCreate(DOCUMENT)" -> {
                val fileName: String = getStringFromUserInput("Название заметки")
                val fileContent: String = getStringFromUserInput("Содержимое заметки")
                val newFile: File = createFileInWarehouse(warehouse,
                    DataTypes.DOCUMENT, fileName, menuItem.file)

                newFile.content = fileContent

                return getMenuByCurrentFile(warehouse, newFile)
            }
            "OnEnter" -> return getMenuByCurrentFile(warehouse, menuItem.file)
            "OnShow(DOCUMENT)" -> {

                openFile(true, menuItem.file!!)

                return getMenuByCurrentFile(warehouse, menuItem.file)
            }
            "OnEdit(DOCUMENT)" -> {

                openFile(false, menuItem.file!!)

                return getMenuByCurrentFile(warehouse, menuItem.file)
            }
            else -> return Menu("", mutableMapOf<Int, MenuItem>())
        }
    }

    fun openFile(readOnly: Boolean, currentFile: File)
    {

        if (currentFile.content == "")
            println("Содержимое заметки \"${currentFile.name}\" еще не заполнено.")
        else
            println("Содержимое заметки \"${currentFile.name}\": ${currentFile.content}")

        if (!readOnly)
            currentFile.content = getStringFromUserInput("Новое содержимое заметки")

    }

    fun createFileInWarehouse(
        warehouse: Warehouse,
        dataType: DataTypes,
        name: String,
        directory: File?) : File
    {
        when(dataType){
            DataTypes.DIRECTORY -> {
                val newFile: Directory = Directory(name, directory)
                warehouse.files.add(newFile)
                return newFile
            }
            DataTypes.DOCUMENT -> {
                val newFile: Document = Document(name, directory)
                warehouse.files.add(newFile)
                return newFile
            }
        }
    }


    fun getFilesFromWarehouseDirectory(
        warehouse: Warehouse,
        dataType: DataTypes,
        directory: File) : MutableList<File>
    {
        val files: MutableList<File> = mutableListOf<File>()

        for (curFile in warehouse.files)
        {
            if(curFile.type == dataType && curFile.parent == directory){
                files.add(curFile)
            }
        }

        return files
    }

    fun getMenuByCurrentFile(
        warehouse: Warehouse,
        currentFile: File?
    ): Menu
    {

        var menuTitle: String = ""
        val counter: Int = 0

        if (currentFile == null)
            return Menu("", mutableMapOf<Int, MenuItem>())


        if (currentFile == warehouse.rootDirectory )
        {

            // Главное меню программы
            menuTitle = "Список Ваших архивов"

            val menuItems: MutableMap<Int, MenuItem> = getMenuItemsByCurrentFile(
                warehouse, DataTypes.DIRECTORY, currentFile)

            return Menu(menuTitle, menuItems)

        }
        else
        {
            if (currentFile is Directory)
            {
                // Меню выбранного архива
                menuTitle = "Заметки из \"${currentFile.name}\""

                val menuItems: MutableMap<Int, MenuItem> = getMenuItemsByCurrentFile(
                    warehouse, DataTypes.DOCUMENT, currentFile)

                return Menu(menuTitle, menuItems)

            }
            else
            {
                val menuItems: MutableMap<Int, MenuItem> = mutableMapOf()

                if (currentFile is Document)
                {
                    // Меню выбранной заметки
                    menuTitle = "Заметка \"${currentFile.name}\""

                    menuItems.put(counter+1, MenuItem("Показать заметку",
                        currentFile, "OnShow(DOCUMENT)"))
                    menuItems.put(counter+2, MenuItem("Редактировать заметку",
                        currentFile, "OnEdit(DOCUMENT)"))
                    menuItems.put(counter+3, MenuItem("Назад",
                        currentFile.parent, "OnEnter"))

                }
                else
                {
                    menuTitle = "ОШИБКА! Неизвестный тип у currentFile"
                }
                return Menu(menuTitle, menuItems)

            }
        }
    }


    fun getMenuItemsByCurrentFile(
        warehouse: Warehouse,
        curDataType: DataTypes,
        currentFile: File?
    ): MutableMap<Int, MenuItem> {
        var counter: Int = 0
        val menuItems: MutableMap<Int, MenuItem> = mutableMapOf()

        val files: MutableList<File> = getFilesFromWarehouseDirectory(
            warehouse,
            curDataType, currentFile!!
        )

        for (curFile in files) {
            counter++
            menuItems.put(
                counter, MenuItem(
                    "Открыть ${curDataType.getLocalizedAccusativeName()} \"${curFile.name}\"",
                    curFile, "OnEnter"
                )
            )
        }

        menuItems.put(
            counter + 1, MenuItem(
                "Создать нов. ${curDataType.getLocalizedAccusativeName()}",
                currentFile, "OnCreate(${curDataType})"
            )
        )

        if (currentFile.parent == warehouse.rootDirectory) {
            menuItems.put(
                counter + 2, MenuItem(
                    "Назад",
                    currentFile.parent, "OnEnter"
                )
            )
            counter++
        }

        menuItems.put(
            counter + 2, MenuItem(
                "Выход",
                null, "OnEnter"
            )
        )

        return menuItems
    }
}
