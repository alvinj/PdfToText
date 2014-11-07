package com.alvinalexander.pdftotext

import akka.actor._
import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper

case class ReadFile(filename: String, startPage: Int, endPage: Int)
case class HeresTheContent(content: String)

/**
 * It's wicked-evil (not cool) to pass the controller class reference
 * in here like this. (Don't do this in important programs.)
 */
class PdfReader(controller: MainFrameController) extends Actor {

    var helper: ActorRef = context.actorOf(Props(new PdfReaderHelper), name = "PdfReaderHelper")

    // TODO i need a way to send this back to our caller
    def receive = {

        case ReadFile(filename, startPage, endPage) => helper ! ReadFile(filename, startPage, endPage)
        case HeresTheContent(content) => controller.handleHeresTheTextEvent(content)
        case unknown => // ignore

    }

}


// TODO code to implement is on aa.com
class PdfReaderHelper extends Actor {
  
    def receive = {
        case ReadFile(filename, startPage, endPage) => sender ! HeresTheContent(getPdfContent(filename, startPage, endPage))
        case unknown => // ignore
    }

    def getPdfContent(filename: String, startPage: Int, endPage: Int): String = {
        val pdf = PDDocument.load(new File(filename))
        val stripper = new PDFTextStripper
        stripper.setStartPage(startPage)
        stripper.setEndPage(endPage)
        stripper.getText(pdf)    
  }

}







