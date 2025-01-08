
class Menu(
    val title: String,
    var menuItems: MutableMap<Int, MenuItem>)
{
    fun show(){

        println()
        println()
        println("••• ${title} ••••••••••••••••••••••••••••••••••••••••••••••••")

        for (curPair in menuItems){
                println("${curPair.key.toString()}. ${curPair.value.name}")
        }

        println("••• ----------------------- •••")
    }

}