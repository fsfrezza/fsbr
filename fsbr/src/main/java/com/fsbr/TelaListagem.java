package com.fsbr;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.fsbr.entities.Cadastramento;
import com.fsbr.models.DisplayableObjectTableModel;
import com.fsbr.models.ObjectTableModel;
import com.fsbr.utils.BotaoUtils;
import com.fsbr.utils.ConexaoUtils;
import com.fsbr.utils.TelaUtils;

public class TelaListagem extends JFrame {
		
	private static final long serialVersionUID = -7436678802955667838L;
	protected static final String PESSOA_BY_CPF = "http://localhost:8080/pessoa-cpf/";
	protected static final String PESSOA_BY_CPF_OR_NOME = "http://localhost:8080/pessoa-cpf-nome/";
	private static JTextField textCpfBusca;
	private JTextField textNomeBusca;
	private JTable tabela;
	private JScrollPane pane;
	private JPanel contentPane;
	private JLabel lblQtd;
	ObjectTableModel<Cadastramento> tableModel;
	JFrame frame;

	public TelaListagem() {
		setResizable(false);
		setBackground(Color.WHITE);
		setFont(new Font("Century Gothic", Font.BOLD, 12));
		setTitle("Listagem");
		frame = this;
        initUI();
    }

    private void initUI() {
    	URL iconURL = getClass().getResource("/fsbr.png");
    	ImageIcon icon = new ImageIcon(iconURL);
    	this.setIconImage(icon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        tableModel = new DisplayableObjectTableModel<>(Cadastramento.class);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
        getContentPane().add(contentPane);
        
		//setContentPane(pane);
		setLocationRelativeTo(null);
        setVisible(false);
                
                textCpfBusca = new JTextField();
                textCpfBusca.setFont(new Font("Century Gothic", Font.PLAIN, 14));
                textCpfBusca.setColumns(10);
                
                JButton btnBuscar = new JButton("Buscar");
                btnBuscar = BotaoUtils.formataBotao(btnBuscar, new Color(0, 0, 128), Color.WHITE, "Century Gothic", 16);
                btnBuscar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
                    	lblQtd.setText("Qtd: ");
						String nome = textNomeBusca.getText().isEmpty() ? null : textNomeBusca.getText().replaceAll(" ",  "_");
						String cpf = textCpfBusca.getText().isEmpty() ? null : textCpfBusca.getText();
						String endpoint = PESSOA_BY_CPF_OR_NOME + cpf + "/" + nome;
                		String jsonString = ConexaoUtils.getRequest(endpoint);
                		if(!jsonString.isEmpty()) {
	                		jsonString = jsonString.replaceAll("\"","").replace("{", "").replace("}", "");
	                        String[] campos = jsonString.split(",");
	                        List<Cadastramento> pessoas = new ArrayList<Cadastramento>();
	                        Cadastramento pessoa = new Cadastramento();
	                        for(String campo : campos) {
	                        	String chave = campo.split(":")[0]; 
	                        	String valor = campo.split(":")[1]; 
	                        	if (chave.contains("cidade")) {
	                        		pessoa.setCidade(valor);
	                        		pessoas.add(pessoa);
	                        	} else if (chave.contains("id")) {
	                        		pessoa = new Cadastramento();
	                        		pessoa.setId(Integer.parseInt(valor));
	                        	} else if (chave.contains("nome")) {
	                        		pessoa.setNome(valor);
	                        	} else if (chave.contains("cpf")) {
	                        		pessoa.setCpf(valor);   
	                        	}                     	
	                        }

	                        tableModel.setObjectRows(pessoas);
	                        tableModel.fireTableDataChanged();
	                        lblQtd.setText(lblQtd.getText() + pessoas.size());
                		} else {
                    		tableModel = new DisplayableObjectTableModel<>(Cadastramento.class);
                        	lblQtd.setText("Qtd: ");
                        	textNomeBusca.setText("");
                        	textCpfBusca.setText("");
                		}
                	}
                });

                
                JLabel lblCpfBusca = new JLabel("CPF: ");
                lblCpfBusca.setFont(new Font("Century Gothic", Font.BOLD, 14));
                
                textNomeBusca = new JTextField();
                textNomeBusca.setFont(new Font("Century Gothic", Font.PLAIN, 14));
                textNomeBusca.setColumns(10);
                
                JLabel txtrNome = new JLabel("Nome: ");
                txtrNome.setFont(new Font("Century Gothic", Font.BOLD, 14));

                tabela = new JTable(tableModel);
                tabela.setFont(new Font("Century Gothic", Font.PLAIN, 12));
                tabela.setPreferredScrollableViewportSize(new Dimension(400, 300));
                for(int i = 0; i < 4; i++) {
                	tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                	if(i == 0) {
                		tabela.getColumnModel().getColumn(i).setPreferredWidth(Math.round(.01f * tabela.getWidth()));
                	} else if(i == 2) {
                		tabela.getColumnModel().getColumn(i).setPreferredWidth(Math.round(.25f * tabela.getWidth()));                		
                	}
                	tabela.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
                }
                pane = new JScrollPane(tabela);
                pane.setFont(new Font("Century Gothic", Font.PLAIN, 14));
                pane.setBackground(Color.WHITE); 
                
                lblQtd = new JLabel("Qtd: ");
                lblQtd.setFont(new Font("Century Gothic", Font.BOLD, 13));
                
                JButton btnLimpar = new JButton("Limpar");
                btnBuscar = BotaoUtils.formataBotao(btnBuscar, Color.BLUE, Color.WHITE, "Century Gothic", 15);
                btnLimpar.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		textNomeBusca.setText("");
                		textCpfBusca.setText("");
                		tableModel = new DisplayableObjectTableModel<>(Cadastramento.class);
                		lblQtd.setText("Qtd: ");
                	}
                });
                
                JButton btnVoltar = new JButton("Voltar");
                btnVoltar = BotaoUtils.formataBotao(btnVoltar, Color.ORANGE, Color.WHITE, "Century Gothic", 15);
                btnVoltar.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		TelaUtils.voltaPaginaPrincipal(frame);
                	}
                });
                
                GroupLayout gl_contentPane = new GroupLayout(contentPane);
                gl_contentPane.setHorizontalGroup(
                	gl_contentPane.createParallelGroup(Alignment.TRAILING)
                		.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                			.addContainerGap()
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                				.addGroup(gl_contentPane.createSequentialGroup()
                					.addComponent(pane, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                					.addContainerGap())
                				.addGroup(gl_contentPane.createSequentialGroup()
                					.addComponent(txtrNome)
                					.addPreferredGap(ComponentPlacement.RELATED)
                					.addComponent(textNomeBusca, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
                					.addGap(18)
                					.addComponent(lblCpfBusca)
                					.addPreferredGap(ComponentPlacement.RELATED)
                					.addComponent(textCpfBusca, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                					.addGap(18)
                					.addComponent(btnBuscar)
                					.addContainerGap())
                				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                					.addComponent(btnLimpar)
                					.addGap(101)
                					.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                					.addGap(141))
                				.addGroup(gl_contentPane.createSequentialGroup()
                					.addComponent(lblQtd)
                					.addContainerGap(532, Short.MAX_VALUE))))
                );
                gl_contentPane.setVerticalGroup(
                	gl_contentPane.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addGap(12)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(textNomeBusca, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                				.addComponent(lblCpfBusca)
                				.addComponent(txtrNome)
                				.addComponent(textCpfBusca, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                				.addComponent(btnBuscar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
                			.addGap(18)
                			.addComponent(pane, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
                			.addPreferredGap(ComponentPlacement.RELATED)
                			.addComponent(lblQtd)
                			.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                				.addComponent(btnLimpar)
                				.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
                			.addContainerGap())
                );
                contentPane.setLayout(gl_contentPane);

    }
}
