package parser

import parser.Recurrence
import rule.RecurrenceRule

/**
 * Created by kkr on 2018. 2. 6..
 */

data class Occasion(var name: String? = null,
                    @Recurrence var recurrence: RecurrenceRule,
                    var date: String? = null,
                    var desc: String? = null)