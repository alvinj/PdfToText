package com.alvinalexander.pdftotext

import akka.actor.ActorRef
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.JOptionPane
import javax.swing.SwingUtilities
import javax.swing.JDialog
import java.awt.BorderLayout
import javax.swing.JProgressBar
import javax.swing.JLabel
import java.awt.Dimension

/**
 * This class has the responsibility of being the controller for the MainFrame,
 * meaning that it builds the MainFrame, has references to its widgets,
 * responds to events triggered by its widgets, and controls its widgets, 
 * in particular writing to its output area.
 */
class MainFrameController {
  
    // TODO find a better way to do this
    var pdfReader: ActorRef = _
  
    // mainframe and fields
    val mainFrame = new MainFrame
    val pageStartField = mainFrame.pageStartField
    val pageEndField = mainFrame.pageEndField
    val filenameField = mainFrame.filenameField
    val outputArea = mainFrame.outputArea
    val goButton = mainFrame.goButton
    
    // wait dialog
    val waitDialog = new JDialog(mainFrame, "Progress Dialog", true)
    configureWaitDialog
    configureActionListeners
    
    def start(pdfReader: ActorRef) {
        this.pdfReader = pdfReader
        mainFrame.setLocationRelativeTo(null)
        mainFrame.setVisible(true)
    }
    
    private def configureActionListeners {
        val goListener = new GoButtonListener(this)
        goButton.addActionListener(goListener)
        filenameField.addActionListener(goListener)
        pageStartField.addActionListener(goListener)
        pageEndField.addActionListener(goListener)
    }

    private def configureWaitDialog {
        val progressBar = new JProgressBar
        progressBar.setIndeterminate(true)
        waitDialog.add(BorderLayout.NORTH, spacer)
        waitDialog.add(BorderLayout.SOUTH, spacer)
        waitDialog.add(BorderLayout.EAST, spacer)
        waitDialog.add(BorderLayout.WEST, spacer)
        waitDialog.add(BorderLayout.CENTER, progressBar)
        waitDialog.setSize(300, 75)
        waitDialog.setLocationRelativeTo(mainFrame)
    }
    
    private def spacer = {
        val spacerLabel = new JLabel
        spacerLabel.setPreferredSize(new Dimension(10, 10))
        spacerLabel
    }

    private def showWaitDialog {
        SwingUtils.invokeLater(waitDialog.setVisible(true))
    }
    
    private def hideWaitDialog {
        SwingUtils.invokeLater(waitDialog.setVisible(false))
    }
    
    def doButtonClickedAction {
        try {
            val startPage = pageStartField.getText.toInt
            val endPage = pageEndField.getText.toInt
            val filename = filenameField.getText
            // tell the reader to read the pdf; it calls us back when it's done.
            showWaitDialog
            outputArea.setText("")
            pdfReader ! ReadFile(filename: String, startPage: Int, endPage: Int)
        } catch {
            case t: Throwable => 
                JOptionPane.showMessageDialog(
                    mainFrame,
                    "Sorry, something is wrong with the input. But have a nice day.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE)  
                }
    }
    
    def handleHeresTheTextEvent(text: String) {
        SwingUtilities.invokeLater(new Runnable {
            def run {
                outputArea.setText(text)
                outputArea.setCaretPosition(0)
                hideWaitDialog
            }
        })
    }

}

class GoButtonListener(caller: MainFrameController) extends ActionListener {

    def actionPerformed(e: ActionEvent) {
        caller.doButtonClickedAction
    }  

}



