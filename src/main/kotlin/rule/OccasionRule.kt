package rule

/**
 * Created by kkr on 2018. 2. 6..
 */

data class OccasionRule(val name: String,
                        @Recurrence val recurrence: RecurrenceRule,
                        val time: List<String>,
                        val title: String? = null,
                        val desc: String)