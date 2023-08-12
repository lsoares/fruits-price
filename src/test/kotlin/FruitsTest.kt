import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FruitsTest {

    @Test
    fun `price of empty cart`() {
        assertEquals(0, priceOf())
    }

    @Test
    fun `1 apple`() {
        assertEquals(25, priceOf("🍏"))
    }

    @Test
    fun `1 orange`() {
        assertEquals(70, priceOf("🍊"))
    }


    @Test
    fun `1 orange and 1 apple`() {
        assertEquals(95, priceOf("🍊", "🍏"))
    }

    @Test
    fun `2 apples pays 1`() {
        assertEquals(25, priceOf("🍏", "🍏"))
    }

    @Test
    fun `3 apples pays 2`() {
        assertEquals(50, priceOf("🍏", "🍏", "🍏"))
    }

    @Test
    fun `3 oranges pays 2`() {
        assertEquals(70 * 2, priceOf("🍊", "🍊", "🍊"))
    }

    @Test
    fun `4 oranges pays 3`() {
        assertEquals(70 * 3, priceOf(*Array(4) { "🍊" }))
    }

    @Test
    fun `apples and oranges`() {
        assertEquals(25 + 140, priceOf(*Array(2) { "🍏" } + Array(2) { "🍊" }))
    }

    @Test
    fun `1 banana`() {
        assertEquals(35, priceOf("🍌"))
    }

    @Test
    fun `10 bananas pays 5`() {
        assertEquals(35 * 5, priceOf(*Array(10) { "🍌" }))
    }

    @Test
    fun `2 bananas pays 1`() {
        assertEquals(35, priceOf("🍌", "🍌"))
    }
}


fun priceOf(vararg fruits: String): Int {
    val counts = fruits.groupBy { it }.mapValues { (_, occurrences) -> occurrences.size }
    val appleCount = counts.getOrDefault("🍏", 0)
    val orangeCount = counts.getOrDefault("🍊", 0)
    val bananaCount = counts.getOrDefault("🍌", 0)
    return (appleCount / 2 + appleCount % 2) * 25 +
            (bananaCount / 2 + bananaCount % 2) * 35 +
            ((orangeCount / 3) * 2 + orangeCount % 3) * 70
}