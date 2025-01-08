
abstract class File {
    abstract val name: String
    abstract val type: DataTypes
    abstract val parent: File?
    abstract var content: String
}

data class Directory(
    override val name: String,
    override var type: DataTypes,
    override val parent: File?,
    override var content: String
) : File() {
    constructor(name: String, parent: File?) : this(name, DataTypes.DIRECTORY, parent, "")
}

data class Document(
    override val name: String,
    override var type: DataTypes,
    override val parent: File?,
    override var content: String
) : File() {
    constructor(name: String, parent: File?) : this(name, DataTypes.DOCUMENT, parent, "")
}

data class Warehouse(var files: MutableList<File>, var rootDirectory: Directory)
