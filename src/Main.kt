import kotlin.collections.ArrayList
import kotlin.collections.HashSet

fun genCombinations(gens: ArrayList<HashSet<Collection<Int>>>): Collection<HashSet<Collection<Int>>> {
    val al = ArrayList<HashSet<Collection<Int>>>()

    when {
        gens.size == 0 -> return al
        gens.size == 1 -> return gens
        else -> {
            for (i in 1..(gens.size - 2)) {
                al.add(gens[i])
            }

            val al2 = genCombinations(al)

            for (c in al2) {
                for (c2 in gens[gens.size - 1]) {
                    c.add(c2)
                }
            }

            return al2
        }
    }
}

fun gFilter(config: List<List<Int>>, hC: List<List<Int>>, k: Int, n: Int): Boolean {
    val ar = Array(n) {
        Array(n) {
            false
        }
    }

    for ((i, l) in config.withIndex()) {
        for (v in l) {
            ar[i][v - 1] = true
        }
    }

    /*for (a1 in ar) {
        var str = ""
        for (a2 in a1) {
            if (a2) {
                str += "*"
            } else {
                str += "."
            }
        }
        println(str)
    }
    println()*/

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

        if (!new) {
            ali.removeAt(ali.size - 1)
        }

        //println(ali)
        //println(constraint)

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

            //println(ali)
            //println(vC[i])
            ali == vC[i]
        }

        if (gen2.isEmpty()) {
            val al = ArrayList<ArrayList<Int>>().apply {
                add(ArrayList<Int>())
            }

            gen2 = al
        }

        //println(gen2)
        //println(gens)

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

fun main(args: Array<String>) {
    val n = 25

    val arV = ArrayList<ArrayList<Int>>().apply {
        for (i in 1..n) {
            add(ArrayList())
        }
    }

    val arH = ArrayList<ArrayList<Int>>().apply {
        for (i in 1..n) {
            add(ArrayList())
        }
    }

    arH[0].add(1)
    arH[1].add(1)
    arH[2].add(3)
    arH[3].add(2)
    arH[3].add(2)
    arH[4].add(2)
    arH[4].add(2)
    arH[5].add(1)

    arV[0].add(3)
    arV[1].add(3)
    arV[2].add(3)
    arV[3].add(2)
    arV[4].add(2)
    arV[5].add(1)

    val bc = buildConfigs(arH, arV, n)

    for (c in bc) {
        val ar = bcToAr(c, n)

        if (ConfigChecker().checkConfig(arH, arV, ar)) {
            for (a1 in ar) {
                var str = ""
                for (a2 in a1) {
                    if (a2) {
                        str += "*"
                    } else {
                        str += "."
                    }
                }
                println(str)
            }
            println()

            return
        }
    }
}