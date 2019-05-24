import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

fun buildBasicConfig(config: Collection<Int>, n: Int): Array<Array<Boolean>> {
    val ar = Array<Array<Boolean>>(n) {
        Array<Boolean>(n) {
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

fun genCombinations(gens: ArrayList<HashSet<Collection<Int>>>): Collection<HashSet<Collection<Int>>> {
    val al = ArrayList<HashSet<Collection<Int>>>()

    if (gens.size == 0) {
        return al
    } else if (gens.size == 1) {
        return gens
    } else {
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

fun buildConfigs(vC: List<List<Int>>, n: Int): Collection<Collection<Collection<Int>>> {
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
            add(ArrayList<Int>())
        }

        gens.add(al)
    }

    println("phase 1 of $n")

    for (i in 1 until n) {
        val gen2a = CombinationsGenerator().generate(n, vC[i].sum())
        var gens2 = ArrayList<ArrayList<ArrayList<Int>>>()

        var gen2 = gen2a.filter {
            var new = true
            val alb = ArrayList<Boolean>().apply {
                for (i in 0 until n) {
                    add(false)
                }
            }

            val ali = ArrayList<Int>()

            for (i in it) {
                alb[i - 1] = true
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

        gens = gens2

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
    val n = 11

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
    //arH[5].add(1)

    arV[0].add(3)
    arV[1].add(3)
    arV[2].add(3)
    arV[3].add(2)
    arV[4].add(2)
    //arV[5].add(1)

    val bc = buildConfigs(arH, n)

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