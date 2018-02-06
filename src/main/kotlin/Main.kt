import parser.Occasion
import parser.OccasionParser
import rule.RecurrenceRule

/**
 * Created by kkr on 2018. 2. 6..
 */

fun main(args: Array<String>) {
    val parser = OccasionParser()
    var result: Occasion? = parser.parse("""
    {
      "name": "John Smith",
      "recurrence" : "EVERYYEAR_ONEDAY",
      "date" : "12-25",
      "desc": "23"
    }
""")
    println(result!!.recurrence == RecurrenceRule.EVERYYEAR_ONEDAY)
}