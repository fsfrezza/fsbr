package com.fsbr;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.fsbr.utils.BotaoUtils;
import com.fsbr.utils.ConexaoUtils;
import com.fsbr.utils.JsonUtils;

public class TelaConsulta extends JFrame {
		
	private static final long serialVersionUID = -7436678802955667838L;
	protected static final String PESSOA_BY_CPF = "http://localhost:8080/pessoa-cpf/";
	protected static final String PESSOA = "http://localhost:8080/pessoa/";
	private static JTextField textNome;	
	private static JTextField textEstado;
	private static JTextField textCpf;
	private static JTextField textCidade;
	private static JTextField textCpfBusca;

	public TelaConsulta() {
		setResizable(false);
		setFont(new Font("Century Gothic", Font.BOLD, 12));
		setTitle("Consulta");

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
                
                JLabel lblCidade = new JLabel("Cidade: ");
                lblCidade.setFont(new Font("Century Gothic", Font.BOLD, 20));
                
                textEstado = new JTextField();
                textEstado.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textEstado.setEditable(false);
                textEstado.setColumns(10);
                
                textNome = new JTextField();
                textNome.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textNome.setEditable(false);
                textNome.setColumns(10);
                
                textCpf = new JTextField();
                textCpf.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textCpf.setEditable(false);
                textCpf.setColumns(10);
                
                textCidade = new JTextField();
                textCidade.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textCidade.setEditable(false);
                textCidade.setColumns(10);
                
                textCpfBusca = new JTextField();
                textCpfBusca.setFont(new Font("Century Gothic", Font.PLAIN, 20));
                textCpfBusca.setColumns(10);
                
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
								textEstado.setText(map.get("estado"));
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
                
                JTextArea lblCpfBusca = new JTextArea("CPF: ");
                lblCpfBusca.setEditable(false);
                lblCpfBusca.setFont(new Font("Century Gothic", Font.BOLD, 20));
                
                JSeparator separator = new JSeparator();
                
                JButton btnVoltar = new JButton("Voltar");
                btnVoltar.setContentAreaFilled(false);
                btnVoltar.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		voltaPaginaPrincipal();
                	}
                });
                btnVoltar.setBackground(Color.RED);
                btnVoltar.setForeground(Color.WHITE);
                btnVoltar.setFont(new Font("Century Gothic", Font.BOLD, 15));
                btnVoltar.setContentAreaFilled(false);
                btnVoltar.setOpaque(true);
                
                GroupLayout gl_contentPane = new GroupLayout(contentPane);
                gl_contentPane.setHorizontalGroup(
                	gl_contentPane.createParallelGroup(Alignment.TRAILING)
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addGap(26)
                			.addComponent(lblCpfBusca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                			.addPreferredGap(ComponentPlacement.RELATED)
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
                				.addComponent(lblCidade)
                				.addComponent(lblCpf))
                			.addPreferredGap(ComponentPlacement.RELATED)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                				.addComponent(textCidade)
                				.addComponent(textCpf)
                				.addComponent(textNome)
                				.addComponent(textEstado, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                			.addContainerGap(21, Short.MAX_VALUE))
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addContainerGap(248, Short.MAX_VALUE)
                			.addComponent(btnVoltar)
                			.addGap(237))
                );
                gl_contentPane.setVerticalGroup(
                	gl_contentPane.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addGap(13)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(btnBuscar)
                				.addComponent(lblCpfBusca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                				.addComponent(textCpfBusca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                			.addGap(12)
                			.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                			.addGap(28)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(lblEstado)
                				.addComponent(textEstado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
                				.addComponent(lblCidade)
                				.addComponent(textCidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                			.addPreferredGap(ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                			.addComponent(btnVoltar)
                			.addContainerGap())
                );
                contentPane.setLayout(gl_contentPane);

    }


	public void voltaPaginaPrincipal() {
		setVisible(false);
		TelaPrincipal principal = new TelaPrincipal();
		principal.setVisible(true);
	}
}
