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


    @Test
    fun `an orange and an apple`() {
        assertEquals(95, priceOf(listOf("🍊", "🍏")))
    }

    @Test
    fun `multiple of each`() {
        assertEquals(190, priceOf(List(2) { "🍏" } + List(2) { "🍊" }))
    }
}

fun priceOf(fruits: List<String>): Int {
    val counts = fruits.groupBy { it }.mapValues { (_, occurrences) -> occurrences.size }
    return counts.getOrDefault("🍏", 0) * 25 +
            counts.getOrDefault("🍊", 0) * 70
}