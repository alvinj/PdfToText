package com.alvinalexander.pdftotext

import javax.swing.JTextField
import javax.swing.BorderFactory
import java.awt.Color

class BetterLookingTextField(numColumns: Int) extends JTextField(numColumns: Int) {
    this.setFont(getFont.deriveFont(16.0f))
    this.setBorder(BorderFactory.createCompoundBorder(getBorder, BorderFactory.createEmptyBorder(5, 12, 5, 5)))
    //this.setBounds(20, 20, getHeight, getWidth)
    this.setBackground(Color.WHITE)
}


