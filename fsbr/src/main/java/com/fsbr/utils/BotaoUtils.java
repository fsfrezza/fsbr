package com.fsbr.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class BotaoUtils {
	
	public static JButton geraBotao (String textoBotao, Color corFundo, Color corFonte, String nomeFonte, int tamanhoFonte) {
		JButton btn = new JButton(textoBotao);
		return formataBotao(btn, corFundo, corFonte, nomeFonte, tamanhoFonte);		
	}
	
	public static JButton formataBotao(JButton btn, Color corFundo, Color corFonte, String nomeFonte, int tamanhoFonte) {
		btn.setBackground(corFundo);
		btn.setForeground(corFonte);
		btn.setFont(new Font(nomeFonte, Font.BOLD, tamanhoFonte));
		btn.setContentAreaFilled(false);
		btn.setOpaque(true);
		return btn;
	}
	
	public static void formataBotoes(Color corFundo, Color corFonte, String nomeFonte, int tamanhoFonte, JButton ... botoes) {
		for (JButton btn : botoes) {
			btn = formataBotao(btn, corFundo, corFonte, nomeFonte, tamanhoFonte);
		}
	}
}
