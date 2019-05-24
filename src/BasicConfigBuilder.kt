class BasicConfigBuilder {
    fun buildBasicConfig(config: Collection<Int>, n: Int): Array<Array<Boolean>> {
        val ar = Array(n) {
            Array(n) {
                false
            }
        }

        for (i in config) {
            val ii = i - 1
            val r = ii / n
            val c = ii % n
            ar[r][c] = true
        }

        return ar
    }
}