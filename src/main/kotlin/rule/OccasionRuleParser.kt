package rule

import com.beust.klaxon.*
import rule.OccasionRule
import rule.Recurrence
import rule.RecurrenceRule

/**
 * Created by kkr on 2018. 2. 6..
 */

class OccasionRuleParser {

    val recurrenceRuleConverter = object : Converter<RecurrenceRule> {

        override fun fromJson(jv: JsonValue): RecurrenceRule {
            val x: String = jv.string as String
            return RecurrenceRule.valueOf(x)
        }

        override fun toJson(o: RecurrenceRule) = """ { "recurrence" : $o } """
    }

    fun parse(json: String): OccasionRule? {
        return Klaxon()
                .fieldConverter(Recurrence::class, recurrenceRuleConverter)
                .parse<OccasionRule>(json)
    }

    fun parseArray(array: String): List<OccasionRule>? {
        return Klaxon()
                .fieldConverter(Recurrence::class, recurrenceRuleConverter)
                .parseArray<OccasionRule>(array)
    }
}