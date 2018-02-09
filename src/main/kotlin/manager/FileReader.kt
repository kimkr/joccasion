package manager

interface FileReader {

    fun readFile(path: String): String
}