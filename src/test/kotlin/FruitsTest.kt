import kotlin.math.max
import kotlin.math.min
import kotlin.test.Test
import kotlin.test.assertEquals

class FruitsTest {

    @Test
    fun `empty cart`() {
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
    fun `1 orange, 1 apple`() {
        assertEquals(95, priceOf("🍊", "🍏"))
    }

    @Test
    fun `2 apples = 1`() {
        assertEquals(25, priceOf("🍏", "🍏"))
    }

    @Test
    fun `3 apples = 2`() {
        assertEquals(50, priceOf("🍏", "🍏", "🍏"))
    }

    @Test
    fun `3 oranges = 2`() {
        assertEquals(70 * 2, priceOf("🍊", "🍊", "🍊"))
    }

    @Test
    fun `4 oranges = 3`() {
        assertEquals(70 * 3, priceOf(*"🍊"(4)))
    }

    @Test
    fun `2 apples, 2 oranges`() {
        assertEquals(25 + 140, priceOf("🍏", "🍏", "🍊", "🍊"))
    }

    @Test
    fun `1 banana`() {
        assertEquals(35, priceOf("🍌"))
    }

    @Test
    fun `10 bananas = 5`() {
        assertEquals(35 * 5, priceOf(*"🍌"(10)))
    }

    @Test
    fun `2 bananas = 1`() {
        assertEquals(35, priceOf(*"🍌"(2)))
    }

    @Test
    fun `1 banana, 1 apple = 1 banana`() {
        assertEquals(35, priceOf("🍏", "🍌"))
    }

    @Test
    fun `2 bananas, 2 apples = 2 bananas`() {
        assertEquals(2 * 35, priceOf("🍌", "🍌", "🍏", "🍏"))
    }

    @Test
    fun `6 bananas, 3 apples = 3+2 bananas`() {
        assertEquals(5 * 35, priceOf(*"🍌"(6) + "🍏"(3)))
    }

    @Test
    fun `3 bananas, 6 apples = 3 bananas + 2 apples`() {
        assertEquals(
            3 * 35 + 2 * 25,
            priceOf(*"🍌"(3) + "🍏"(6))
        )
    }

    @Test
    fun `31 bananas, 61 apples, 101 oranges = 31 bananas + 15 apples + 66+2 oranges`() {
        assertEquals(
            31 * 35 + 15 * 25 + 68 * 70,
            priceOf(*"🍌"(31) + "🍏"(61) + "🍊"(101))
        )
    }

    operator fun String.invoke(value: Int) = Array(value) { this }
}

fun priceOf(vararg fruits: String): Int {
    val apples = fruits.count { it == "🍏" }
    val bananas = fruits.count { it == "🍌" }
    val oranges = fruits.count { it == "🍊" }
    return min(bananas, apples) * 35 + // 🍏🍌
            max(0, bananas - apples).withPromo(2, 1) * 35 + // 🍌
            max(0, apples - bananas).withPromo(2, 1) * 25 + // 🍏
            oranges.withPromo(3, 2) * 70 // 🍊
}

private fun Int.withPromo(every: Int, pays: Int) =
    (this / every) * pays + this % every
