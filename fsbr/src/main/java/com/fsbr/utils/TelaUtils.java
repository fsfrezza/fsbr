package com.fsbr.utils;

import javax.swing.JFrame;

import com.fsbr.TelaPrincipal;

public class TelaUtils {

	public static void voltaPaginaPrincipal(JFrame frame) {
		frame.setVisible(false);
		TelaPrincipal principal = new TelaPrincipal();
		principal.setVisible(true);		
	}
}
