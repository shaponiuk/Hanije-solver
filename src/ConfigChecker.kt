import java.util.*

class ConfigChecker {

    private fun checkV(constraints: List<List<Int>>, board: Array<Array<Boolean>>): Boolean {
        val ll = LinkedList<List<Int>>()

        for ((i, _) in constraints.withIndex()) {
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
        }

        return constraints == ll
    }

    private fun checkH(constraints: List<List<Int>>, board: Array<Array<Boolean>>): Boolean {
        val ll = LinkedList<List<Int>>()

        for ((i, _) in constraints.withIndex()) {
            val l = LinkedList<Int>()
            var new = true

            for ((j, _) in board[i].withIndex()) {
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
            }

            ll.add(l)
        }

        return constraints == ll
    }

    fun checkConfig(
        verticalConstraints: List<List<Int>>, horizontalConstraints: List<List<Int>>,
        board: Array<Array<Boolean>>
    ): Boolean {
        return checkH(horizontalConstraints, board)
                && checkV(verticalConstraints, board)
    }
}