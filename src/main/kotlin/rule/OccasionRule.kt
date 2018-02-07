package rule

/**
 * Created by kkr on 2018. 2. 6..
 */

data class OccasionRule(var name: String,
                        @Recurrence var recurrence: RecurrenceRule,
                        var time: List<String>,
                        var desc: String)