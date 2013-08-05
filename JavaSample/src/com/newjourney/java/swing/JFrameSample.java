package com.newjourney.java.swing;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JFrameSample extends JFrame implements ActionListener {

	JLabel label;
	JTextField edtF;
	JButton btn;
	JCheckBox chb;
	JTextArea edtA;

	public JFrameSample() {
		setTitle("Socket Connection");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		label = new JLabel("Address：");
		getContentPane().add(label);

		edtF = new JTextField("http://", 15);
		getContentPane().add(edtF);

		btn = new JButton("Connnect");
		btn.addActionListener(this);
		getContentPane().add(btn);

		chb = new JCheckBox("Remember");
		chb.addActionListener(this);
		getContentPane().add(chb);

		edtA = new JTextArea("Description", 6, 20);
		getContentPane().add(edtA);

		setBounds(300, 200, 350, 200);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Connnect".equals(e.getActionCommand())) {
			JDialog dialog = new JDialog(this, "Info", true);
			dialog.setLayout(new FlowLayout());
			JLabel label = new JLabel("Connect Successfully.");
			dialog.getContentPane().add(label);
			dialog.setBounds(350, 250, 250, 100);
			dialog.setVisible(true);
		}
	}

	public static void main(String[] args) {
		new JFrameSample();
	}

}
