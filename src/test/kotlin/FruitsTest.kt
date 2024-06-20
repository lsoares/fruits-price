import kotlin.math.max
import kotlin.math.min
import kotlin.test.Test
import kotlin.test.assertEquals

class FruitsTest {

    @Test
    fun `empty cart`() {
        assertEquals(0, calculate())
    }

    @Test
    fun `1 apple`() {
        assertEquals(25, calculate("🍏"))
    }

    @Test
    fun `1 orange`() {
        assertEquals(70, calculate("🍊"))
    }

    @Test
    fun `1 orange, 1 apple`() {
        assertEquals(95, calculate("🍊", "🍏"))
    }

    @Test
    fun `2 apples = 1`() {
        assertEquals(25, calculate("🍏", "🍏"))
    }

    @Test
    fun `3 apples = 2`() {
        assertEquals(50, calculate("🍏", "🍏", "🍏"))
    }

    @Test
    fun `3 oranges = 2`() {
        assertEquals(70 * 2, calculate("🍊", "🍊", "🍊"))
    }

    @Test
    fun `4 oranges = 3`() {
        assertEquals(70 * 3, priceOf("🍊" * 4))
    }

    @Test
    fun `2 apples, 2 oranges`() {
        assertEquals(25 + 140, calculate("🍏", "🍏", "🍊", "🍊"))
    }

    @Test
    fun `1 banana`() {
        assertEquals(35, calculate("🍌"))
    }

    @Test
    fun `10 bananas = 5`() {
        assertEquals(35 * 5, priceOf("🍌" * 10))
    }

    @Test
    fun `1 banana, 1 apple = 1 banana`() {
        assertEquals(35, calculate("🍏", "🍌"))
    }

    @Test
    fun `2 bananas, 3 apples = 2 bananas + 1 apple`() {
        assertEquals(2 * 35 + 25, calculate("🍌", "🍌", "🍏", "🍏", "🍏"))
    }

    @Test
    fun `ignores unknown`() {
        assertEquals(35, calculate("🍌", "🍏", "🍇", "🥑"))
    }

    @Test
    fun `2 bananas, 2 apples = 2 bananas`() {
        assertEquals(2 * 35, calculate("🍌", "🍌", "🍏", "🍏"))
    }

    @Test
    fun `6 bananas, 3 apples = 3+2 bananas`() {
        assertEquals(5 * 35, priceOf("🍌" * 6 + "🍏" * 3))
    }

    @Test
    fun `5 apples, 7 bananas = 5+1 bananas`() {
        assertEquals(6 * 35, priceOf("🍌" * 7 + "🍏" * 5))
    }

    @Test
    fun `3 bananas, 6 apples = 3 bananas + 2 apples`() {
        assertEquals(
            3 * 35 + 2 * 25,
            priceOf("🍌" * 3 + "🍏" * 6)
        )
    }

    @Test
    fun `31 bananas, 61 apples, 101 oranges = 31 bananas + 15 apples + 66+2 oranges`() {
        assertEquals(
            31 * 35 + 15 * 25 + 68 * 70,
            priceOf("🍌" * 31 + "🍏" * 61 + "🍊" * 101)
        )
    }

    private operator fun String.times(value: Int) = Array(value) { this }

    private fun priceOf(fruits: Array<String>) = calculate(*fruits)
}

// 2-pass solution: calculates total prices and then subtracts the free fruits discount
fun calculate(vararg fruits: String): Int {
    val apples = fruits.count { it == "🍏" }
    val bananas = fruits.count { it == "🍌" }
    val oranges = fruits.count { it == "🍊" }

    val freeBananasPlusApples = (bananas + apples) / 2
    val freeApples = min(apples, freeBananasPlusApples)
    val freeBananas = freeBananasPlusApples - freeApples
    val freeOranges = oranges / 3

    return (oranges - freeOranges) * 70 +
            (bananas - freeBananas) * 35 +
            (apples - freeApples) * 25
}

// 1-pass solution: calculates right price with promotion in one go
fun calculate_old(vararg fruits: String): Int {
    val apples = fruits.count { it == "🍏" }
    val bananas = fruits.count { it == "🍌" }
    val oranges = fruits.count { it == "🍊" }

    fun Int.withPromo(every: Int, pays: Int) = (this / every) * pays + this % every
    val extraBananas = max(0, bananas - apples)
    val extraApples = max(0, apples - bananas)
    return min(bananas, apples) * 35 + // 🍏🍌
            extraBananas.withPromo(2, 1) * 35 + // 🍌
            extraApples.withPromo(2, 1) * 25 + // 🍏
            oranges.withPromo(3, 2) * 70 // 🍊
}

