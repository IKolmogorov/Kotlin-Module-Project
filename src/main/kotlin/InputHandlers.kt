import java.util.Scanner

fun getStringFromUserInput(parameterName: String): String {

    var inputIsNotCorrect: Boolean = true
    var inputString: String = ""

    while (inputIsNotCorrect){
        println("Введите \"$parameterName\":")
        inputString = Scanner(System.`in`).nextLine()

        if (inputString != "") {
            inputIsNotCorrect = false
        }
    }

    return inputString
}

fun getIntFromUserInput(parameterName: String, minParameterValue: Int?, maxParameterValue: Int?): Int {

    var minValue = 0
    var maxValue = Int.MAX_VALUE

    if (minParameterValue != null) minValue = minParameterValue
    if (maxParameterValue != null) maxValue = maxParameterValue

    var inputIsNotCorrect: Boolean = true
    var inputInt: Int = -1

    while (inputIsNotCorrect){
        println("Введите \"$parameterName\":")

        try {
            inputInt = Scanner(System.`in`).nextLine().toInt()

            if (inputInt >= minValue && inputInt <= maxValue) {
                inputIsNotCorrect = false
            }
        }
        catch (e: Exception) {
            inputIsNotCorrect = true
        }
    }

    return inputInt

}

