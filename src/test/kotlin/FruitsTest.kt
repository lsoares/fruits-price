import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FruitsTest {

    @Test
    fun `price of empty cart`() {
        assertEquals(0, priceOf(emptyList()))
    }

    @Test
    fun `an apple`() {
        assertEquals(25, priceOf(listOf("🍏")))
    }

    @Test
    fun `an orange`() {
        assertEquals(70, priceOf(listOf("🍊")))
    }
}

fun priceOf(fruits: List<String>): Int {
    if (fruits.contains("🍏")) return 25
    if (fruits.contains("🍊")) return 70
    return 0
}