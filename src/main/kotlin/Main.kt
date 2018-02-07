/**
 * Created by kkr on 2018. 2. 6..
 */

fun main(args: Array<String>) {
    val manager = OccasionManager()
    manager.addOccasionRules("KR", """
    [{
      "name": "John Smith",
      "recurrence" : "EVERYYEAR_ONEDAY",
      "time" : ["12-25"],
      "desc": "23"
    }]
""")
}