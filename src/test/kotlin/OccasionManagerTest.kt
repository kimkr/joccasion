import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.runners.JUnit4
import rule.OccasionRule
import rule.RecurrenceRule
import java.time.LocalDate

@RunWith(JUnit4::class)
class OccasionManagerTest {

    val occasionManager = OccasionManager()

    @Before
    fun setUp() {
        occasionManager.addOccasionRules("KR", """
    [{
      "name": "Christmas",
      "recurrence" : "EVERYYEAR_ONEDAY",
      "time" : ["12.25"],
      "desc": "Christmas"
    },
    {
      "name": "Christmas Eve",
      "recurrence" : "ONEYEAR_ONEDAY",
      "time" : ["2018.12.25"],
      "desc": "Christmas"
    },
    {
      "name": "Spring festival",
      "recurrence" : "EVERYYEAR_SPAN",
      "time" : ["03.30", "04.02"],
      "desc": "Spring festival"
    },
        {
      "name": "Fall festival",
      "recurrence" : "ONEYEAR_SPAN",
      "time" : ["2018", "10.27", "10.28"],
      "desc": "Fall festival"
    },
    ]
""")
    }

    @Test
    fun getOccasions() {
        println(occasionManager.getOccasions("KR", 2000))
        println(occasionManager.getOccasions("KR", 2018))
    }

    @Test
    fun getOccasions1() {
        var occasionRule: OccasionRule
        var occasions: Set<Occasion>

        occasionRule = OccasionRule("abc", RecurrenceRule.ONEYEAR_ONEDAY, listOf("2000.12.31"), "the last day of the year")
        occasions = occasionManager.getOccasions(2000, occasionRule)
        println(occasions)

        occasionRule = OccasionRule("abc", RecurrenceRule.ONEYEAR_SPAN, listOf("2000", "12.30", "12.31"), "the last day of the year")
        occasions = occasionManager.getOccasions(2000, occasionRule)
        println(occasions)

        occasionRule = OccasionRule("abc", RecurrenceRule.EVERYYEAR_ONEDAY, listOf("12.31"), "the last day of the year")
        occasions = occasionManager.getOccasions(2000, occasionRule)
        println(occasions)

        occasionRule = OccasionRule("abc", RecurrenceRule.EVERYYEAR_SPAN, listOf("12.30", "12.31"), "the last day of the year")
        occasions = occasionManager.getOccasions(2000, occasionRule)
        println(occasions)
    }

    @Test
    fun parseYearMonthDay() {
        val (year, month, day) = occasionManager.parseYearMonthDay("2000.12.31")
        assertEquals(2000, year)
        assertEquals(12, month)
        assertEquals(31, day)
    }

    @Test
    fun parseMonthDay() {
        val (month, day) = occasionManager.parseMonthDay("12.31")
        assertEquals(12, month)
        assertEquals(31, day)
    }

    @Test
    fun getDate() {
        val localDate = occasionManager.getDate(2000, "01.02")
        assertEquals(2000, localDate.year)
        assertEquals(1, localDate.monthValue)
        assertEquals(2, localDate.dayOfMonth)
    }

    @Test
    fun getDates() {
        val localDates = occasionManager.getDates(2000, "01.29", "03.01")
        assertTrue(containDate(localDates, occasionManager.getDate(2000, "01.31")))
        assertTrue(containDate(localDates, occasionManager.getDate(2000, "02.01")))
        assertTrue(containDate(localDates, occasionManager.getDate(2000, "02.28")))
        assertTrue(containDate(localDates, occasionManager.getDate(2000, "02.29")))
        assertTrue(containDate(localDates, occasionManager.getDate(2000, "03.01")))
    }

    fun containDate(dates: List<LocalDate>, specificDate: LocalDate): Boolean {
        for (date in dates) {
            if (date.equals(specificDate)) {
                return true
            }
        }
        return false
    }
}
