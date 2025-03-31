package me.partypronl.recur.util.database

class MultiCauseException(
    val header: String = DefaultHeader,
    val causes: List<Throwable>,
) : Exception() {

    constructor(header: String = DefaultHeader, vararg causes: Throwable) : this(header, causes.toList())

    override val message: String
        get() = printMessage()

    private fun printMessage() = buildString {
        if (causes.isEmpty()) {
            appendLine(header)
        } else {
            appendLine("$header (${causes.size} failures)")
            for ((index, cause) in causes.withIndex()) {
                appendLine()
                appendLine("Failure ${index + 1} of ${causes.size}:")
                appendLine(cause.printStackTrace())
            }
        }
    }

    companion object {

        private const val DefaultHeader = "Multiple failures"
    }
}
