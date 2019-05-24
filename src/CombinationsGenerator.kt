class CombinationsGenerator {
    data class Pair<P1, P2>(val p1: P1, val p2: P2)

    private val map = HashMap<Pair<Int, Int>, ArrayList<ArrayList<Int>>>()

    private fun generateRec(bound: Int, k: Int): ArrayList<ArrayList<Int>> {
        return if (map.containsKey(Pair(bound, k))) {
            map[Pair(bound, k)]!!
        } else {
            when {
                k < 1 -> {
                    val hs = ArrayList<ArrayList<Int>>()
                    map[Pair(bound, k)] = hs
                    hs
                }
                k == 1 -> {
                    val hs = ArrayList<ArrayList<Int>>().apply {
                        for (i in 1..bound) {
                            val al = ArrayList<Int>().apply {
                                add(i)
                            }

                            add(al)
                        }
                    }
                    map[Pair(bound, k)] = hs
                    hs
                }
                else -> {
                    val hs = ArrayList<ArrayList<Int>>().apply {
                        for (i in 1 until bound) {
                            val generated = generateRec(i, k - 1)

                            for (g in generated) {
                                for (j in (i + 1)..bound) {
                                    val gCp = ArrayList<Int>()

                                    for (ge in g) {
                                        gCp.add(ge)
                                    }

                                    gCp.add(j)
                                    add(gCp)
                                }
                            }
                        }
                    }
                    map[Pair(bound, k)] = hs
                    hs
                }
            }
        }
    }

    fun generate(bound: Int, k: Int): ArrayList<ArrayList<Int>> {
        return generateRec(bound, k)
    }
}