import kotlin.math.pow

/**
 * Recursive function that converts decimal numbers (base 10) to binary numbers (base 2)
 * Handles negative decimal numbers by prefixing them with '-'
 *
 * @param n Integer to be converted
 * @return Binary representation of inputted number as a String
 */
fun decimalToBinary(n: Int): String {
    return when {
        n < 0 -> "-" + decimalToBinary(-n)
        n / 2 == 0 -> n.toString()
        else -> decimalToBinary(n / 2) + (n % 2).toString()
    }
}

/**
 * Recursive function that converts binary numbers (base 2) to decimal numbers (base 10)
 * Handles negative binary numbers by prefixing them with '-'
 *
 * @param b Binary String to be converted
 * @param index Current position being processed
 * @return Decimal representation of inputted binary number
 * @throws IllegalArgumentException if the input is not a valid binary number
 */
fun binaryToDecimal(b: String, index: Int = 0): Int {
    if (!b.matches(Regex("-?[01]+"))) throw IllegalArgumentException("Invalid binary number: $b")
    if (b.startsWith('-')) return -binaryToDecimal(b.substring(1))

    return when {
        index == b.length -> 0
        else -> {
            val digit = b[b.length - 1 - index].digitToInt()
            digit * 2.0.pow(index.toDouble()).toInt() + binaryToDecimal(b, index + 1)
        }
    }
}

fun main() {
    try {
        println(decimalToBinary(2))
        println(binaryToDecimal("10"))
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}