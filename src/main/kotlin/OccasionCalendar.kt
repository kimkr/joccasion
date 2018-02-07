import java.time.LocalDate

class OccasionCalendar(var country: Country,
                       var year: Int,
                       var occasions: Set<Occasion>) {

    fun checkOccasion(date: LocalDate): Occasion? {
        for (occasion in occasions) {
            if (occasion.date.equals(date)) return occasion
        }
        return null
    }
}