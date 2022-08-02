package com.fsbr;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
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
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.fsbr.entities.Cadastramento;
import com.fsbr.utils.BotaoUtils;
import com.fsbr.utils.ConexaoUtils;
import com.fsbr.utils.JTextFieldLimit;
import com.fsbr.utils.JsonUtils;
import com.fsbr.utils.TelaUtils;

public class TelaAlteracao extends JFrame {
		
	private static final long serialVersionUID = -7436678802955667838L;
	protected static final String PESSOA_BY_CPF = "http://localhost:8080/pessoa-cpf/";
	protected static final String PESSOA = "http://localhost:8080/pessoa/";
	private static final String ESTADOS = "http://localhost:8080/estados/";
	private JTextField textNome;
	private JTextField textCpf;
	private JTextField textCidade;
	private JTextField textCpfBusca;
	private JLabel textId;
	private JComboBox<String> comboBox;
	private JFrame frame;

	public TelaAlteracao() {
		setFont(new Font("Century Gothic", Font.BOLD, 12));
		setTitle("Alteração");
		frame = this;
        initUI();
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
                
                JTextArea lblCidade = new JTextArea("Cidade: ");
                lblCidade.setEditable(false);
                lblCidade.setFont(new Font("Century Gothic", Font.BOLD, 20));
                
                textNome = new JTextField(10);
                textNome.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textNome.setDocument(new JTextFieldLimit(100));
                
                textCpf = new JTextField(10);
                textCpf.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textCpf.setDocument(new JTextFieldLimit(11));
                
                textCidade = new JTextField(50);
                textCidade.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textCidade.setDocument(new JTextFieldLimit(50));

                
                comboBox = new JComboBox<String>();
                comboBox.setFont(new Font("Century Gothic", Font.PLAIN, 20));
				
                JButton btnBuscar = new JButton("Buscar");
                btnBuscar = BotaoUtils.formataBotao(btnBuscar, new Color(0, 0, 128), Color.WHITE, "Century Gothic", 15);
                btnBuscar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
                		String jsonString = ConexaoUtils.getRequest(PESSOA_BY_CPF + textCpfBusca.getText());
                		if(!jsonString.isEmpty()) {
	                		Map<String, String> map;
							try {
								map = flatMap(new LinkedHashMap<>(), "", JsonUtils.readMap(jsonString));
								textNome.setText(map.get("nome"));
								textCpf.setText(map.get("cpf"));
								textCidade.setText(map.get("cidade"));
								textId.setText(map.get("id"));
								comboBox = atualizaComboBox();
								comboBox.setSelectedItem(map.get("estado"));
							} catch (Exception e1) {
								e1.printStackTrace();
							}
                		} else {
			        	    JOptionPane.showMessageDialog(null, new StringBuilder().append("Dados não localizados, tente novamente."));
							textCpfBusca.setText("");
                		}
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
            });
                
                JButton btnVoltar = new JButton("Voltar");
                btnVoltar = BotaoUtils.formataBotao(btnVoltar, Color.RED, Color.WHITE, "Century Gothic", 15);
                btnVoltar.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
						TelaUtils.voltaPaginaPrincipal(frame); 
                	}
                });
                
                JButton btnSalvar = new JButton("Salvar");
                btnSalvar = BotaoUtils.formataBotao(btnSalvar, new Color(50, 205, 50), Color.WHITE, "Century Gothic", 15);
                btnSalvar.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		Cadastramento pessoa = new Cadastramento(textNome.getText(), textCpf.getText(), textCidade.getText(), (String) comboBox.getSelectedItem());
                		try {
							ConexaoUtils.putRequest(PESSOA + textId.getText(), pessoa.toString());
			        	    JOptionPane.showMessageDialog(null, new StringBuilder().append("Cadastro Atualizado com Sucesso."));
							TelaUtils.voltaPaginaPrincipal(frame);

						} catch (Exception ex) {
			        	    JOptionPane.showMessageDialog(null, new StringBuilder().append("Houve algum problema na alteração, tentar novamente."));
						}
                	}
                });
                
                textCpfBusca = new JTextField();
                textCpfBusca.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textCpfBusca.setColumns(10);
                
                JLabel lblCpfBusca = new JLabel("CPF: ");
                lblCpfBusca.setFont(new Font("Century Gothic", Font.BOLD, 20));
                
                JSeparator separator = new JSeparator();
                
                textId = new JLabel();
                textId.setVisible(false);
                textId.setBackground(Color.WHITE);
                textId.setForeground(Color.WHITE);
                
                GroupLayout gl_contentPane = new GroupLayout(contentPane);
                gl_contentPane.setHorizontalGroup(
                	gl_contentPane.createParallelGroup(Alignment.TRAILING)
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addGap(27)
                			.addComponent(lblCpfBusca)
                			.addPreferredGap(ComponentPlacement.UNRELATED)
                			.addComponent(textCpfBusca, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
                			.addGap(18)
                			.addComponent(btnBuscar)
                			.addGap(22))
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addGap(3)
                			.addComponent(separator, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                			.addContainerGap())
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addGap(27)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                				.addComponent(lblEstado)
                				.addComponent(lblNome)
                				.addComponent(lblCidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                				.addComponent(lblCpf))
                			.addPreferredGap(ComponentPlacement.RELATED)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                				.addComponent(textCidade, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                				.addComponent(textCpf, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                				.addComponent(textNome, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                				.addComponent(comboBox, Alignment.TRAILING, 0, 427, Short.MAX_VALUE))
                			.addGap(12)
                			.addComponent(textId)
                			.addContainerGap())
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addContainerGap(180, Short.MAX_VALUE)
                			.addComponent(btnVoltar)
                			.addGap(70)
                			.addComponent(btnSalvar)
                			.addGap(170))
                );
                gl_contentPane.setVerticalGroup(
                	gl_contentPane.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addGap(13)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(btnBuscar)
                				.addComponent(lblCpfBusca)
                				.addComponent(textCpfBusca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                			.addGap(12)
                			.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                			.addGap(28)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(lblEstado)
                				.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                			.addGap(18)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(lblNome)
                				.addComponent(textNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                			.addGap(18)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(textCpf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                				.addComponent(lblCpf))
                			.addGap(18)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(lblCidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                				.addComponent(textCidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                			.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(btnVoltar)
                				.addComponent(btnSalvar))
                			.addPreferredGap(ComponentPlacement.RELATED)
                			.addComponent(textId)
                			.addContainerGap())
                );
                contentPane.setLayout(gl_contentPane);

    }

	private JComboBox<String> atualizaComboBox() {
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
		
		for(String estado : listaEstados) {
			comboBox.addItem(estado);
		}
		return comboBox;
	}
}