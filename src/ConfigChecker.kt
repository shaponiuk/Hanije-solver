import java.util.*

class ConfigChecker {

    fun compareLists(c: List<List<Int>>, ll: List<List<Int>>): Boolean {
        var i = 0

        for (l in ll) {
            if (l != c[i]) {
                return false
            }

            i++
        }

        return true
    }

    private fun checkV(constraints: List<List<Int>>, board: Array<Array<Boolean>>): Boolean {
        var i = 0
        val ll = LinkedList<List<Int>>()
        for (row in constraints) {
            val l = LinkedList<Int>()
            var new = true
            for (b in board[i]) {
                if (b) {
                    if (new) {
                        l.add(1)
                        new = false
                    } else {
                        if (l.last != null) {
                            l[l.size - 1] = l.last + 1
                        }
                    }
                } else {
                    new = true
                }
            }


            ll.add(l)

            i++
        }

        return compareLists(constraints, ll)
    }

    private fun checkH(constraints: List<List<Int>>, board: Array<Array<Boolean>>): Boolean {
        var i = 0
        val ll = LinkedList<List<Int>>()
        for (row in constraints) {
            val l = LinkedList<Int>()
            var new = true
            var j = 0
            for (b in board[i]) {
                if (board[j][i]) {
                    if (new) {
                        l.add(1)
                        new = false
                    } else {
                        if (l.last != null) {
                            l[l.size - 1] = l.last + 1
                        }
                    }
                } else {
                    new = true
                }

                j++
            }

            ll.add(l)
            i++
        }

        return compareLists(constraints, ll)
    }

    fun checkConfig(
        verticalConstraints: List<List<Int>>, horizontalConstraints: List<List<Int>>,
        board: Array<Array<Boolean>>
    ): Boolean {
        return checkH(horizontalConstraints, board)
                && checkV(verticalConstraints, board)
    }
}