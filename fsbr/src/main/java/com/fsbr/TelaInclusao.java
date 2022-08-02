package com.fsbr;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.fsbr.entities.Cadastramento;
import com.fsbr.utils.BotaoUtils;
import com.fsbr.utils.ConexaoUtils;
import com.fsbr.utils.TelaUtils;
import com.fsbr.utils.JTextFieldLimit;

public class TelaInclusao extends JFrame {
		
	private static final long serialVersionUID = -7436678802955667838L;
	protected static final String PESSOA = "http://localhost:8080/pessoa/";
	private static final String ESTADOS = "http://localhost:8080/estados/";
	private JTextField textNome;
	private JTextField textCpf;
	private JTextField textCidade;
	private JFrame frame;

	public TelaInclusao() {
		setResizable(false);
		setFont(new Font("Century Gothic", Font.BOLD, 12));
		setTitle("Inclusão");
		frame = this;
        initUI();
    }
	

	@SuppressWarnings("unchecked")
	public static Map<String, String> flatMap(Map<String, String> res, String prefix, Map<String, Object> map) {
	    for (Map.Entry<String, Object> entry : map.entrySet()) {
	        String key = prefix + entry.getKey();
	        Object value = entry.getValue();
	
	        if (value instanceof Map)
	            flatMap(res, key + '.', (Map<String, Object>)value);
	        else
	            res.put(key, String.valueOf(value));
	    }
	
	    return res;
	}

    private void initUI() {

    	URL iconURL = getClass().getResource("/fsbr.png");
    	ImageIcon icon = new ImageIcon(iconURL);
    	this.setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
        setVisible(false);
                
                JLabel lblEstado = new JLabel("Estado: ");
                lblEstado.setFont(new Font("Century Gothic", Font.BOLD, 20));
                
                JLabel lblNome = new JLabel("Nome: ");
                lblNome.setFont(new Font("Century Gothic", Font.BOLD, 20));
                
                JLabel lblCpf = new JLabel("CPF: ");
                lblCpf.setFont(new Font("Century Gothic", Font.BOLD, 20));
                
                JLabel lblCidade = new JLabel("Cidade: ");
                lblCidade.setFont(new Font("Century Gothic", Font.BOLD, 20));
                
                List<String> listaEstados = new ArrayList<String>();
        		String expressao = "\"nome\":\"";
        		String jsonString = ConexaoUtils.getRequest(ESTADOS);
				while(jsonString.contains(expressao)) {
					String valor = jsonString.substring(jsonString.indexOf(expressao) + expressao.length(), jsonString.indexOf("\"}"));
	                if(!listaEstados.contains(valor)) {
	                	listaEstados.add(valor);
	                }
	                jsonString = jsonString.substring(jsonString.indexOf("},") + 2);
				}
				Collections.sort(listaEstados);   

                
                JComboBox<String> comboBox = new JComboBox<String>();
                comboBox.setFont(new Font("Century Gothic", Font.PLAIN, 20));
				for(String estado : listaEstados) {
					comboBox.addItem(estado);
				}
				comboBox.setSelectedItem("");
                
                textNome = new JTextField(10);
                textNome.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textNome.setDocument(new JTextFieldLimit(100));
                
                textCpf = new JTextField(10);
                textCpf.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textCpf.setDocument(new JTextFieldLimit(11));
                
                textCidade = new JTextField(50);
                textCidade.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textCidade.setDocument(new JTextFieldLimit(50));
                
                JButton btnVoltar = new JButton("Voltar");
                btnVoltar = BotaoUtils.formataBotao(btnVoltar, Color.RED, Color.WHITE, "Century Gothic", 15);
                btnVoltar.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		TelaUtils.voltaPaginaPrincipal(frame);  
                	}
                });
                
                
                JButton btnSalvar = new JButton("Salvar");
                btnSalvar = BotaoUtils.formataBotao(btnSalvar, new Color(60, 179, 113), Color.WHITE, "Century Gothic", 15);
                btnSalvar.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		Cadastramento pessoa = new Cadastramento(textNome.getText(), textCpf.getText(), textCidade.getText(), (String) comboBox.getSelectedItem());
                		try {
							ConexaoUtils.postRequest(PESSOA, pessoa.toString());
			        	    JOptionPane.showMessageDialog(null, new StringBuilder().append("Cadastro Realizado com Sucesso."));
							TelaUtils.voltaPaginaPrincipal(frame);

						} catch (Exception ex) {
			        	    JOptionPane.showMessageDialog(null, new StringBuilder().append("Houve algum problema na inclusão tentar novamente."));
						}
                	}
                });
                
                GroupLayout gl_contentPane = new GroupLayout(contentPane);
                gl_contentPane.setHorizontalGroup(
                	gl_contentPane.createParallelGroup(Alignment.TRAILING)
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addGap(35)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                				.addComponent(lblCpf)
                				.addComponent(lblEstado)
                				.addComponent(lblCidade)
                				.addComponent(lblNome))
                			.addPreferredGap(ComponentPlacement.UNRELATED)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                				.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                				.addComponent(textNome)
                				.addComponent(textCpf)
                				.addComponent(textCidade, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE))
                			.addGap(25, 51, Short.MAX_VALUE))
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addContainerGap(173, Short.MAX_VALUE)
                			.addComponent(btnVoltar)
                			.addGap(85)
                			.addComponent(btnSalvar)
                			.addGap(162))
                );
                gl_contentPane.setVerticalGroup(
                	gl_contentPane.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addGap(20)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                				.addComponent(lblEstado))
                			.addGap(40)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(textNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                				.addComponent(lblNome))
                			.addGap(40)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(textCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                				.addComponent(lblCpf))
                			.addGap(40)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(textCidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                				.addComponent(lblCidade))
                			.addGap(50)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(btnSalvar)
                				.addComponent(btnVoltar))
                			.addGap(7))
                );
                contentPane.setLayout(gl_contentPane);

    }
}
