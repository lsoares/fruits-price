import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min
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

    @Test
    fun `1 banana 1 apple pays 1 banana`() {
        assertEquals(35, priceOf("🍏", "🍌"))
    }

    @Test
    fun `2 bananas 2 apples pays 2 bananas`() {
        assertEquals(2 * 35, priceOf("🍌", "🍌", "🍏", "🍏"))
    }

    @Test
    fun `6 bananas 3 apples pays 3+2 bananas`() {
        assertEquals(5 * 35, priceOf(*Array(6) { "🍌" } + Array(3) { "🍏" }))
    }

    @Test
    fun `3 bananas 6 apples pays 3 bananas + 2 apples`() {
        assertEquals(3 * 35 + 2 * 25, priceOf(*Array(3) { "🍌" } + Array(6) { "🍏" }))
    }
}

fun priceOf(vararg fruits: String): Int {
    val apples = fruits.count { it == "🍏" }
    val oranges = fruits.count { it == "🍊" }
    val bananas = fruits.count { it == "🍌" }
    val unmatchedBananas = max(0, bananas - apples)
    val unmatchedApples = max(0, apples - bananas)
    val matchedApplesAndBananas = min(bananas, apples)
    return matchedApplesAndBananas * 35 + // 🍏🍌
            (unmatchedBananas / 2 + unmatchedBananas % 2) * 35 + // 🍌
            (unmatchedApples / 2 + unmatchedApples % 2) * 25 + // 🍏
            ((oranges / 3) * 2 + oranges % 3) * 70 // 🍊
}