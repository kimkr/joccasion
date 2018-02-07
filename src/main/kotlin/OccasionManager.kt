import rule.OccasionRule
import rule.RecurrenceRule.*
import rule.OccasionRuleParser
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OccasionManager {

    // COUNTRY - OCCASION RULE
    private val occasionRuleMap: MutableMap<String, List<OccasionRule>> = mutableMapOf()
    private val parser = OccasionRuleParser()

    fun addOccasionRules(country: String, jsonArray: String) {
        val occasionRules = parser.parseArray(jsonArray)!!
        occasionRuleMap.put(country, occasionRules)
    }

    fun getOccasions(country: String, year: Int): Set<Occasion> {
        var occasions = mutableSetOf<Occasion>()
        occasionRuleMap[country]?.map { rule -> occasions.addAll(getOccasions(year, rule)) }
        return occasions
    }

    fun getOccasions(year: Int, rule: OccasionRule): Set<Occasion> {
        var occasions = mutableSetOf<Occasion>()
        var dates = mutableListOf<LocalDate>()
        when {
            rule.recurrence.equals(EVERYYEAR_ONEDAY) -> {
                val time = rule.time.get(0)
                dates.add(getDate(year, time))
            }
            rule.recurrence.equals(EVERYYEAR_SPAN) -> {
                val start = rule.time.get(0)
                val end = rule.time.get(1)
                dates.addAll(getDates(year, start, end))
            }
            rule.recurrence.equals(ONEYEAR_ONEDAY) -> {
                val time = rule.time.get(0)
                val (year2, month, day) = parseYearMonthDay(time)
                if (year == year2) {
                    dates.add(LocalDate.of(year, month, day))
                }
            }
            rule.recurrence.equals(ONEYEAR_SPAN) -> {
                val year2 = rule.time.get(0)
                val start = rule.time.get(1)
                val end = rule.time.get(2)
                if (year2.toInt() == year) {
                    dates.addAll(getDates(year, start, end))
                }
            }
        }
        dates.map { date -> occasions.add(Occasion(date, rule.name, rule.desc)) }
        return occasions
    }

    fun parseYearMonthDay(yearMonthDay: String): Array<Int> {
        val localDate = LocalDate.parse(yearMonthDay, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        return arrayOf(localDate.year, localDate.monthValue, localDate.dayOfMonth)
    }

    fun parseMonthDay(monthDay: String): List<Int> {
        return monthDay.split(".").map { s -> s.toInt() }
    }

    fun getDate(year: Int, monthDay: String): LocalDate {
        val (month, day) = parseMonthDay(monthDay)
        return LocalDate.of(year, month, day)
    }

    fun getDates(year: Int, start: String, end: String): List<LocalDate> {
        val dates = arrayListOf<LocalDate>()
        val (startMonth, startDay) = parseMonthDay(start)
        val (endMonth, endDay) = parseMonthDay(end)
        for (month in startMonth..endMonth) {
            val sDay: Int
            val eDay: Int
            when (month) {
                startMonth -> {
                    val firstDate = LocalDate.of(year, startMonth, startDay)
                    sDay = startDay
                    eDay = firstDate.lengthOfMonth()
                }
                endMonth -> {
                    sDay = 1
                    eDay = endDay
                }
                else -> {
                    val startDate = LocalDate.of(year, month, 1)
                    sDay = 1
                    eDay = startDate.lengthOfMonth()
                }
            }
            for (day in sDay..eDay) {
                dates.add(LocalDate.of(year, month, day))
            }
        }
        return dates
    }
}