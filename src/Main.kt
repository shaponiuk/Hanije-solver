import ConfigToArray.Companion.bcToAr

fun main(args: Array<String>) {

    val parsed = Parser().parse()
    val n = parsed.first
    val pC = parsed.second
    val arV = pC.first
    val arH = pC.second

    val bc = EnhancedConfigBuilder().buildConfigs(arH, arV, n)

    for (c in bc) {
        val ar = bcToAr(c, n)

        if (ConfigChecker().checkConfig(arH, arV, ar)) {
            for (a1 in ar) {
                var str = ""
                for (a2 in a1) {
                    str += if (a2) {
                        "*"
                    } else {
                        "."
                    }
                }
                println(str)
            }
            println()

            return
        }
    }
}