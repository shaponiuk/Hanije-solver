class ConfigToArray {
    companion object {
        fun bcToAr(c: Collection<Collection<Int>>, n: Int): Array<Array<Boolean>> {
            val ar = Array(n) {
                Array(n) {
                    false
                }
            }

            for ((i, cc) in c.withIndex()) {
                for (cr in cc) {
                    ar[i][cr - 1] = true
                }
            }

            return ar
        }
    }
}