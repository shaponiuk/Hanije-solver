import ConfigToArray.Companion.bcToAr

class EnhancedConfigBuilder {

    private fun gFilter(config: List<Array<Int>>, hC: List<List<Int>>, k: Int, n: Int): Boolean {
        val ar = bcToAr(config, n)

        for ((col, constraint) in hC.withIndex()) {
            var new = true
            val ali = ArrayList<Int>()

            for (i in 0 until k) {
                if (ar[i][col]) {
                    if (new) {
                        ali.add(1)
                        new = false
                    } else {
                        ali[ali.size - 1] = ali[ali.size - 1] + 1
                    }
                } else {
                    new = true
                }
            }

            val last = ali.lastOrNull()

            if (!new) {
                ali.removeAt(ali.size - 1)
            }

            when {
                ali.size > constraint.size -> return false
                ali.size == constraint.size -> {
                    if (ali != constraint) {
                        return false
                    }
                }
                else -> {
                    for ((j, value) in ali.withIndex()) {
                        if (value != constraint[j]) {
                            return false
                        }
                    }
                }
            }

            if (ali.size + 1 <= constraint.size && !new && last != null) {
                if (last > constraint[ali.size]) {
                    return false
                }
            }
        }

        return true
    }

    fun buildConfigs(vC: List<List<Int>>, hC: List<List<Int>>, n: Int): Collection<Collection<Array<Int>>> {
        var gens = ArrayList<ArrayList<Array<Int>>>()

        val sum = vC[0].sum()
        val gen = CombinationsGenerator().generate(n, sum)

        for (g in gen) {
            val hs = ArrayList<Array<Int>>().apply {
                add(g)
            }

            gens.add(hs)
        }

        if (gens.isEmpty()) {
            val al = ArrayList<Array<Int>>().apply {
                add(Array(0) { 0 })
            }

            gens.add(al)
        }

        println("phase 1 of $n")

        for (i in 1 until n) {
//            val gen2a = CombinationsGenerator().generate(n, vC[i].sum())
            println(vC[i])
            val gen2a = CombinationsGenerator().generateForHanije(0, n, vC[i].reversed(), vC[i].size)
//            for (eee in gen2a) {
//                for (aaa in eee) {
//                    print("$aaa ")
//                }
//                println()
//            }
            val gens2 = ArrayList<ArrayList<Array<Int>>>()

//            var gen2 = gen2a.filter {
//                var new = true
//                val alb = BooleanArray(n) { false }
//
//                val ali = ArrayList<Int>()
//
//                for (j in it) {
//                    alb[j - 1] = true
//                }
//
//                for (a in alb) {
//                    if (a) {
//                        if (new) {
//                            ali.add(1)
//                            new = false
//                        } else {
//                            ali[ali.size - 1] = ali[ali.size - 1] + 1
//                        }
//                    } else {
//                        new = true
//                    }
//                }
//
//                ali == vC[i]
//            }
            var gen2 = gen2a.map {
                val nMap = Array(vC[i].sum()) { 0 }
                var id = 0
                var arId = 0
                for (constraint in vC[i]) {
                    for (j in 0 until constraint) {
                        nMap[arId] =
                            j + it[id]
                        arId++
                    }
                    id++
                }
                nMap
            }

            if (gen2.isEmpty()) {
                val al = ArrayList<Array<Int>>().apply {
                    add(Array(0) { 0 })
                }

                gen2 = al
            }

            println("generated")

            for (g in gens) {
                for (g2 in gen2) {
                    val gc = ArrayList<Array<Int>>(g)
                    gc.add(g2)
                    gens2.add(gc)
                }
            }

            println("pre")
            val gens2f = gens2.filter {
                gFilter(it, hC, i + 1, n)
            }
            println("post")
            gens = ArrayList()

            for (g in gens2f) {
                gens.add(g)
            }

            println("phase ${i + 1} of $n")
        }

        return gens
    }
}