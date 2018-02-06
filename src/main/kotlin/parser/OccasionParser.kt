package parser

import com.beust.klaxon.*
import rule.RecurrenceRule
import java.io.StringReader

/**
 * Created by kkr on 2018. 2. 6..
 */

class OccasionParser {

    val recurrenceRuleConverter = object : Converter<RecurrenceRule> {

        override fun fromJson(jv: JsonValue): RecurrenceRule {
            val x: String = jv.string as String
            return RecurrenceRule.valueOf(x)
        }

        override fun toJson(o: RecurrenceRule) = """ { "recurrence" : $o } """
    }

    fun parse(json: String): Occasion? {
        return Klaxon()
                .fieldConverter(Recurrence::class, recurrenceRuleConverter)
                .parse<Occasion>(json)
    }
}