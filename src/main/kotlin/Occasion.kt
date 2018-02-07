import java.time.LocalDate

/**
 * Created by kkr on 2018. 2. 6..
 */

data class Occasion(val date: LocalDate,
                    val name: String,
                    val title: String? = null,
                    val desc: String)