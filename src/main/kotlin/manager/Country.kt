package manager

enum class Country(val code: String, val lang: String, val group: String? = null) {

    // NO GROUP
    korea("KO", "kr", null),
    china("CH", "zh", null),
    japan("JP", "ja", null),
    unitedstates("US", "en", null),
    singapore("SG", "en", null),
    india("IN", "en", null),
    // WESTERN COUNTRIES
    canada("CA", "en", "western"),
    mexico("MX", "en", "western"),
    argentina("AR", "en", "western"),
    brazil("BR", "en", "western"),
    australia("AU", "en", "western"),
    newzealand("NZ", "en", "western"),
    philippines("PH", "en", "western"),
    france("FR", "fr", "western"),
    germany("DE", "de", "western"),
    ireland("IE", "en", "western"),
    italy("IT", "it", "western"),
    netherlands("NL", "en", "western"),
    belgium("BE", "en", "western"),
    norway("NO", "en", "western"),
    denmark("DK", "en", "western"),
    sweden("SE", "en", "western"),
    finland("FI", "en", "western"),
    portugal("PT", "en", "western"),
    spain("ES", "en", "western"),
    switzerland("CH", "en", "western"),
    unitedkingdom("GB", "en", "western"),
    // MUSLIM COUNTRIES
    indonesia("IN", "ru", "muslim"),
    pakistan("PK", "ru", "muslim"),
    bangladesh("BD", "ru", "muslim"),
    iran("IR", "ru", "muslim"),
    turkey("TR", "ru", "muslim"),
    egypt("EG", "ru", "muslim"),
    algeria("DZ", "ru", "muslim"),
    morocco("MA", "ru", "muslim"),
    saudiarabia("SA", "ru", "muslim"),
    unitedarabemirates("AE", "ru", "muslim"),

    western("western", "en", null),
    muslim("muslim", "ru", null),
    other("other", "null", null);

    fun fromLang(name: String): Country {
        var replaced: String = name
        replaced = replaced.replace("\\s+", "")
        replaced = replaced.replace("-", "")
        replaced = replaced.replace("_", "")
        return Country.valueOf(replaced)
    }
}