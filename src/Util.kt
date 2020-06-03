fun <E>smallArrayToString(array: Array<E>): String {
    var str = ""

    for (el in array) {
        str += el
        str += ", "
    }

    return str
}

fun <E>smallArrayToString(collection: Collection<Array<E>>): String {

    var str = ""

    for (array in collection) {
        for (el in array) {
            str += el
            str += ", "
        }

        str += "\n"
    }

    return str
}