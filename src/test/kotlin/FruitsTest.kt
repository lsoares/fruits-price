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
    fun `2 apples pays 1`() {
        assertEquals(25, priceOf(List(2) { "🍏" }))
    }

    @Test
    fun `3 apples pays 2`() {
        assertEquals(50, priceOf(List(3) { "🍏" }))
    }

    @Test
    fun `3 oranges pays 2`() {
        assertEquals(70 * 2, priceOf(List(3) { "🍊" }))
    }

    @Test
    fun `4 oranges pays 3`() {
        assertEquals(70 * 3, priceOf(List(4) { "🍊" }))
    }

    @Test
    fun `apples and oranges`() {
        assertEquals(25 + 140, priceOf(List(2) { "🍏" } + List(2) { "🍊" }))
    }

    @Test
    fun `1 banana`() {
        assertEquals(35, priceOf(listOf("🍌")))
    }

    @Test
    fun `10 bananas`() {
        assertEquals(350, priceOf(List(10) { "🍌" }))
    }
}

fun priceOf(fruits: List<String>): Int {
    val counts = fruits.groupBy { it }.mapValues { (_, occurrences) -> occurrences.size }
    val appleCount = counts.getOrDefault("🍏", 0)
    val orangeCount = counts.getOrDefault("🍊", 0)
    val bananaCount = counts.getOrDefault("🍌", 0)
    return (appleCount / 2 + appleCount % 2) * 25 +
            ((orangeCount / 3) * 2 + orangeCount % 3) * 70 +
            bananaCount * 35
}