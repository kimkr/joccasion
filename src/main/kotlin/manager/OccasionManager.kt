package manager

import rule.OccasionRule
import rule.RecurrenceRule.*
import rule.OccasionRuleParser
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OccasionManager(fileReader: FileReader) {

    private val fileReader = fileReader
    // COUNTRY - OCCASION RULE
    private val occasionRuleMap: MutableMap<String, List<OccasionRule>> = mutableMapOf()
    private val occasionMap: MutableMap<String, MutableMap<Int, Set<Occasion>>> = mutableMapOf()
    private val parser = OccasionRuleParser()

    fun loadOccasionRules(code: String, path: String) {
        val fileContent = fileReader.readFile(path)
        addOccasionRules(code, fileContent)
    }

    fun addOccasionRules(code: String, jsonArray: String) {
        val occasionRules = parser.parseArray(jsonArray)!!
        occasionRuleMap.put(code, occasionRules)
    }

    fun checkOccasion(country: Country, year: Int, date: String): String? {
        var occasions = getOccasions(country, year)
        for (occasion in occasions) {
            if (occasion.date.equals(date)) {
                return if (occasion.desc.isNullOrEmpty()) occasion.title else occasion.desc
            }
        }
        return null
    }

    fun getOccasions(country: Country, year: Int): Set<Occasion> {
        val code = country.code
        val group = country.group
        var occasions = mutableSetOf<Occasion>()
        if (occasionMap.containsKey(code)) {
            if (occasionMap[code]!!.containsKey(year)) {
                return occasionMap[code]!![year]!!
            }
            if (group != null) {
                occasionRuleMap[group]?.map { rule -> occasions.addAll(getOccasions(year, rule)) }
            } else {
                occasionRuleMap[code]?.map { rule -> occasions.addAll(getOccasions(year, rule)) }
            }
            occasionMap[code]!!.put(year, occasions)
            return occasions
        } else {
            if (group != null) {
                loadOccasionRules(group, "occasion_$group.json")
                occasionRuleMap[group]?.map { rule -> occasions.addAll(getOccasions(year, rule)) }
            } else {
                loadOccasionRules(code, "occasion_$code.json")
                occasionRuleMap[code]?.map { rule -> occasions.addAll(getOccasions(year, rule)) }
            }
            occasionMap.put(code, mutableMapOf(Pair(year, occasions)))
            return occasions
        }
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
        dates.map { date -> occasions.add(Occasion(date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")), rule.name, rule.title, rule.desc)) }
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