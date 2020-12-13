package vn.com.libertime.kafka

public enum class Acks(public val value: String) {
    Zero("0"),
    One("1"),
    All("all")
}

public enum class BatchSize(public val value: Int) {
    Sixteen(16 * 1024),
    ThirtyTwo(32 * 1024),
    SixtyFour(64 * 1024),
    OneHundredAndTwentyEight(1024 * 1024)
}

internal enum class CommandStatus(val value: String) {
    Open("Open"),
    Closed("Closed"),
    Processing("Processing"),
    Error("Error")
}

public enum class Compression(public val value: String) {
    None("none"),
    Gzip("gzip"),
    Snappy("snappy"),
    lz4("lz4"),
    zstd("zstd")
}

public enum class OffsetBehaviour(public val value: String) {
    Latest("latest"),
    Earliest("earliest"),
    None("none")
}
