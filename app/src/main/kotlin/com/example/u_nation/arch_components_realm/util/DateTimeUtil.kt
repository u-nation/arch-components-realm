package com.example.u_nation.arch_components_realm.util


import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*

object DateTimeUtil {

    private val ZONE_ID: ZoneId = ZoneId.systemDefault()

    fun nowUnixTime(): Long = ZonedDateTime.now().toEpochSecond()

    fun plusDays(day: Int): Long = ZonedDateTime.now().plusDays(day.toLong()).toEpochSecond()

    fun stringToUnixTime(timeText: String, formatter: TimeFormat): Long {
        return ZonedDateTime.of(LocalDateTime.parse(timeText, DateTimeFormatter.ofPattern(formatter.format)), ZONE_ID).toEpochSecond()
    }

    fun unixTimeToString(unixtime: Long, formatter: TimeFormat): String {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(unixtime), ZONE_ID).format(DateTimeFormatter.ofPattern(formatter.format))
    }

    enum class TimeFormat(val format: String) {
        YYYYMMDDHHMMSS_JP("yyyy年MM月dd日 HH:mm:ss"),
        YYYYMMDDHHMM_JP("yyyy年MM月dd日 HH:mm"),
        YYYYMMDD_JP("yyyy年MM月dd日"),
        HHMMSS_JP("HH時mm分ss秒"),
        HHMM_JP("HH時mm分"),
        MMSS_jp("mm分ss秒"),
        YYYYMMDDHHMMSS_HYPHEN("yyyy-MM-dd HH:mm:ss"),
        YYYYMMDDHHMMSS_SLASH("yyyy/MM/dd HH:mm:ss"),
        YYYYMMDDHHMM_SLASH("yyyy/MM/dd HH:mm"),
        YYYYMMDD_HYPHEN("yyyy-MM-dd"),
        YYYYMMDD_SLASH("yyyy/MM/dd"),
        HHMMSS("HH:mm:ss"),
        HHMM("HH:mm"),
        MMSS("mm:ss"),
        YYYYMMDDHHMMSS_US("MM/dd/yyyy HH:mm:ss"),
        YYYYMMDDHHMM_US("MM/dd/yyyy HH:mm"),
        MDE("M/d(E)"),
        DE("d(E)")
    }
}
