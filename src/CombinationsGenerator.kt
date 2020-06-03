import java.lang.Exception

class CombinationsGenerator {
    data class Pair<P1, P2>(val p1: P1, val p2: P2)

    private val map = HashMap<Pair<Int, Int>, ArrayList<Array<Int>>>()

    private fun generateRec(bound: Int, k: Int): ArrayList<Array<Int>> =
        if (map.containsKey(Pair(bound, k))) {
            map[Pair(bound, k)]!!
        } else {
            when {
                k < 1 -> {
                    val hs = ArrayList<Array<Int>>()
                    map[Pair(bound, k)] = hs
                    hs
                }
                k == 1 -> {
                    val hs = ArrayList<Array<Int>>().apply {
                        for (i in 1..bound) {
                            add(Array(1) { i })
                        }
                    }
                    map[Pair(bound, k)] = hs
                    hs
                }
                else -> {
                    val hs = ArrayList<Array<Int>>().apply {
                        for (i in 1 until bound) {
                            val generated = generateRec(i, k - 1)

                            for (g in generated) {
                                for (j in (i + 1)..bound) {
                                    add(Array(g.size + 1) { if (it < g.size) g[it] else j })
                                }
                            }
                        }
                    }
                    map[Pair(bound, k)] = hs
                    hs
                }
            }
        }

    fun generate(bound: Int, k: Int): ArrayList<Array<Int>> =
        generateRec(bound, k)

    fun <E> mergeArrayLists(ar1: ArrayList<E>, ar2: ArrayList<E>): ArrayList<E> =
        ar1.apply {
            for (e in ar2) {
                add(e)
            }
        }

    fun doesnttartsWithZero(array: Array<Int>) = array[0]

    fun generateForHanije(
        start: Int,
        bound: Int,
        reverseConstraints: List<Int>,
        containerSize: Int
    ): ArrayList<Array<Int>> {
//        println("generateForHanije($start, $bound, $reverseConstraints, $containerSize)")
        return if (reverseConstraints.size == 1 && reverseConstraints[0] <= bound - start) {
            var next2 = ArrayList<Array<Int>>()
            try {
                next2 = generateForHanije(start + 1, bound, reverseConstraints, containerSize)
            } catch (_: Exception) {
            }
            next2.add(Array(containerSize) { start + 1 })
            next2
        } else if (reverseConstraints.isEmpty()) {
//            println("empty")
            ArrayList()
        } else if (start >= bound) {
            throw Exception("e")
        } else {
            val first = reverseConstraints.last()
            if (first <= bound - start) {
                var next1 = ArrayList<Array<Int>>()
                var next2 = ArrayList<Array<Int>>()
                try {
                    val a = generateForHanije(start + 1, bound, reverseConstraints, containerSize)
                    next1 = a
                } catch (_: Exception) {
                }
                try {
                    val a =
                        generateForHanije(start + first + 1, bound, reverseConstraints.dropLast(1), containerSize)
                    next2 = a
                } catch (_: Exception) {
                }

                val id = containerSize - reverseConstraints.size
                for (ar in next2) {
                    ar[id] = start + 1
                }

//                println("next1: ${smallArrayToString(next1)}")
//                println("next2: ${smallArrayToString(next2)}")
                val retval = mergeArrayLists(next1, next2)
//                println("returning: $retval")
                retval
            } else {
//                println("returning: empty")
                ArrayList()
            }
        }
    }
}