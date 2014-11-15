#!/bin/sh
exec scala -savecompiled -classpath "lib/pdfbox-app-1.8.7.jar:lib/commons-io-2.4.jar" "$0" "$@"
!#

import java.io._
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper

object PdfToText {

    System.setProperty("java.awt.headless", "true")

    def main(args: Array[String]) {
        if (args.length != 3) printUsageAndExit

        val startPage = args(0).toInt
        val endPage = args(1).toInt
        val filename = args(2)

        // sanity check
        if (endPage < startPage) printUsageAndExit

        println(getTextFromPdf(startPage, endPage, filename))
    }

    def printUsageAndExit {
        println("")
        println("Usage: pdftotext startPage endPage filename")
        println("       (startPage is assumed to be >= than endPage)")
        System.exit(1)
    }

    def getTextFromPdf(startPage: Int, endPage: Int, filename: String): Option[String] = {
        try {
            val pdf = PDDocument.load(new File(filename))
            val stripper = new PDFTextStripper
            stripper.setStartPage(startPage)
            stripper.setEndPage(endPage)
            Some(stripper.getText(pdf))
        } catch {
            case t: Throwable =>
                t.printStackTrace
                None
        }
    }

}

PdfToText.main(args)


