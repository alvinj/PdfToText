package com.alvinalexander.pdftotext

import com.jgoodies.forms.layout.FormLayout
import com.jgoodies.forms.builder.PanelBuilder
import com.jgoodies.forms.layout.CellConstraints
import javax.swing._
import java.awt._
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

class MainFrame extends JFrame {

    val filenameLabel = new JLabel("PDF Filename:")
    val pageStartLabel = new JLabel("Start Page: ")
    val pageEndLabel = new JLabel("End Page: ")
    val outputLabel = new JLabel("Output")
    val pageStartField = new BetterLookingTextField(4)
    val pageEndField = new BetterLookingTextField(4)
    val filenameField = new BetterLookingTextField(40)
    val outputArea = new JEditorPane
    val scrollPane = new JScrollPane(outputArea)
    val goButton = new JButton("Go")

    filenameField.setText("/Users/al/Desktop/Scala-Cookbook.pdf")
    pageStartField.setText("22")
    pageEndField.setText("26")

    setTitle("PDF to Text")
    getContentPane.add(buildInputPanel)
    
    configureOutputArea
    initializePreferredScreenSize
    pack
    
    private def initializePreferredScreenSize {
        val screenSize = Toolkit.getDefaultToolkit.getScreenSize
        val desiredHeight = screenSize.height * 4/5
        val desiredWidth = screenSize.width * 4/5
        setPreferredSize(new Dimension(desiredWidth, desiredHeight))
    }

    private def buildInputPanel: JPanel = {
      
        val layout = new FormLayout(
                "9dlu, pref,  3dlu, pref, 3dlu, pref:grow, 9dlu",                              //columns
                "6dlu, 1px, p, 2dlu, p, 2dlu, p, 2dlu, p, 6dlu, p, 2dlu, fill:pref:grow, 9dlu" //rows
        )
        val builder = new PanelBuilder(layout)
        builder.setDefaultDialogBorder
    
        val cc = new CellConstraints
        builder.add(filenameLabel,  cc.xy (2, 3))
        builder.add(filenameField,  cc.xyw(4, 3, 3))
        builder.add(pageStartLabel, cc.xy (2, 5))
        builder.add(pageStartField, cc.xy (4, 5))
        builder.add(pageEndLabel,   cc.xy (2, 7))
        builder.add(pageEndField,   cc.xy (4, 7))
        builder.add(goButton,       cc.xy (4, 9))
        builder.add(outputLabel,    cc.xy (2, 11))
        builder.add(scrollPane,     cc.xyw(2, 13, 5))

        return builder.getPanel
    
    }
    
    private def configureOutputArea {
        outputArea.setPreferredSize(new Dimension(550, 360))
        outputArea.setEditable(false)
        outputArea.setMargin(new Insets(20, 20, 20, 20))
        outputArea.setFont(new Font("Monaco", Font.PLAIN, 13))
    }

}















