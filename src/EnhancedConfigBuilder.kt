import ConfigToArray.Companion.bcToAr

class EnhancedConfigBuilder {

    private fun gFilter(config: List<List<Int>>, hC: List<List<Int>>, k: Int, n: Int): Boolean {
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

    fun buildConfigs(vC: List<List<Int>>, hC: List<List<Int>>, n: Int): Collection<Collection<Collection<Int>>> {
        var gens = ArrayList<ArrayList<ArrayList<Int>>>()

        val sum = vC[0].sum()
        val gen = CombinationsGenerator().generate(n, sum)

        for (g in gen) {
            val hs = ArrayList<ArrayList<Int>>().apply {
                add(g)
            }

            gens.add(hs)
        }

        if (gens.isEmpty()) {
            val al = ArrayList<ArrayList<Int>>().apply {
                add(ArrayList())
            }

            gens.add(al)
        }

        println("phase 1 of $n")

        for (i in 1 until n) {
            val gen2a = CombinationsGenerator().generate(n, vC[i].sum())
            val gens2 = ArrayList<ArrayList<ArrayList<Int>>>()

            var gen2 = gen2a.filter {
                var new = true
                val alb = ArrayList<Boolean>().apply {
                    for (j in 0 until n) {
                        add(false)
                    }
                }

                val ali = ArrayList<Int>()

                for (j in it) {
                    alb[j - 1] = true
                }

                for (a in alb) {
                    if (a) {
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

                ali == vC[i]
            }

            if (gen2.isEmpty()) {
                val al = ArrayList<ArrayList<Int>>().apply {
                    add(ArrayList<Int>())
                }

                gen2 = al
            }

            for (g in gens) {
                for (g2 in gen2) {
                    val gc = ArrayList<ArrayList<Int>>().apply {
                        for (gp in g) {
                            add(gp)
                        }
                    }
                    gc.add(g2)
                    gens2.add(gc)
                }
            }

            val gens2f = gens2.filter {
                gFilter(it, hC, i + 1, n)
            }

            gens = ArrayList()

            for (g in gens2f) {
                gens.add(g)
            }

            println("phase ${i + 1} of $n")
        }

        return gens
    }
}