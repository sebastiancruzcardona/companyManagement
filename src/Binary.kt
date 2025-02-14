import kotlin.math.pow

// Función que recibe un número decimal (base 10) y lo convierte a binario (base 2)
fun decimalToBinary(n: Int): String {
    return when (n) {
        0 -> "0"
        1 -> "1"
        else -> decimalToBinary(n / 2) + (n % 2).toString()
    }
}

// Función que recibe un número binario (base 2) y lo convierte a decimal (base 10)
fun binaryToDecimal(b: String, index: Int = 0): Int {
    return when(index) {
        b.length -> 0
        else -> {
            val digit = b[b.length - 1 - index].toString().toInt()
            digit * 2.0.pow(index.toDouble()).toInt() + binaryToDecimal(b, index + 1)
        }
    }
}

fun main() {
    println(decimalToBinary(45000))
    println(binaryToDecimal("1010111111001000"))
}