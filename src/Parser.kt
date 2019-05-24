import java.util.*
import kotlin.collections.ArrayList

class Parser {
    fun parse(): Pair<Int, Pair<ArrayList<ArrayList<Int>>, ArrayList<ArrayList<Int>>>> {
        val input = Scanner(System.`in`)
        var n = 0
        if (input.hasNextLine()) {
            n = input.nextLine().toInt()
        }

        val vC = ArrayList<ArrayList<Int>>()
        val hC = ArrayList<ArrayList<Int>>()

        for (i in 0 until n) {
            vC.add(ArrayList())
            if (input.hasNextLine()) {
                val line = input.nextLine()!!

                val values = line.split(' ')

                for (value in values) {
                    vC[i].add(value.toInt())
                }
            }
        }

        for (i in 0 until n) {
            hC.add(ArrayList())
            if (input.hasNextLine()) {
                val line = input.nextLine()!!

                val values = line.split(' ')

                for (value in values) {
                    hC[i].add(value.toInt())
                }
            }
        }

        return Pair(n, Pair(vC, hC))
    }
}