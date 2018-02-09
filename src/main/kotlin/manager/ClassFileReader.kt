package manager

class ClassFileReader : FileReader {
    override fun readFile(path: String): String {
        println(path)
        return FileReader::class.java.getResource(path).readText()
    }
}