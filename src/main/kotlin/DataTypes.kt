
enum class DataTypes : Localizable {
    DIRECTORY {
        override fun getLocalizedName(): String = "архив"
        override fun getLocalizedAccusativeName(): String = "архив"
        override fun getLocalizedPluralName(): String = "архивов"
    },
    DOCUMENT {
        override fun getLocalizedName(): String = "заметка"
        override fun getLocalizedAccusativeName(): String = "заметку"
        override fun getLocalizedPluralName(): String = "заметок"
    }
}

interface Localizable {
    fun getLocalizedName(): String
    fun getLocalizedAccusativeName(): String
    fun getLocalizedPluralName(): String
}