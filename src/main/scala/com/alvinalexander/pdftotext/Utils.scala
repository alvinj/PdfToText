package com.alvinalexander.pdftotext

import java.io.StringWriter
import java.io.PrintWriter

object Utils {

    def getStackTraceAsString(t: Throwable) = {
        val sw = new StringWriter
        t.printStackTrace(new PrintWriter(sw))
        sw.toString
    } 

}