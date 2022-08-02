package com.fsbr;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fsbr.utils.BotaoUtils;


@SpringBootApplication
@EnableJpaRepositories
public class TelaPrincipal extends JFrame {
	
	private static final long serialVersionUID = -7436678802955667838L;

	public TelaPrincipal() {
		setResizable(false);
		setFont(new Font("Century Gothic", Font.BOLD, 12));
		setTitle("Cadastramento");

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
        setVisible(true);
                
                JTextArea texto = new JTextArea("Fernando Santos Frezza");
                texto.setEditable(false);
                texto.setFont(new Font("Century Gothic", Font.BOLD, 20));
                
                JButton btnIncluir = new JButton("Incluir");
                btnIncluir.setFont(new Font("Century Gothic", Font.PLAIN, 15));
                btnIncluir.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        TelaInclusao inclusao = new TelaInclusao();
                        inclusao.setVisible(true);
                	}
                });
                JButton btnAlterar = new JButton("Alterar");
                btnAlterar.setFont(new Font("Century Gothic", Font.PLAIN, 15));
                btnAlterar.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        TelaAlteracao alteracao = new TelaAlteracao();
                        alteracao.setVisible(true);
                	}
                });
                JButton btnExcluir = new JButton("Excluir");
                btnExcluir.setFont(new Font("Century Gothic", Font.PLAIN, 15));
                btnExcluir.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        TelaExclusao exclusao = new TelaExclusao();
                        exclusao.setVisible(true);
                	}
                });
                JButton btnConsultar = new JButton("Consultar");
                btnConsultar.setFont(new Font("Century Gothic", Font.PLAIN, 15));
                btnConsultar.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        TelaConsulta consulta = new TelaConsulta();
                        consulta.setVisible(true);
                	}
                });
                JButton btnListagem = new JButton("Listagem");
                btnListagem.setFont(new Font("Century Gothic", Font.PLAIN, 15));
                btnListagem.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        TelaListagem listagem = new TelaListagem();
                        listagem.setVisible(true);
                	}
                });
                JButton btnSair = new JButton("SAIR");

                BotaoUtils.formataBotoes(Color.BLUE, Color.WHITE, "Century Gothic", 15, btnIncluir, btnAlterar, btnExcluir, btnConsultar, btnListagem, btnSair);
                btnSair.setBackground(Color.RED);
                btnSair.setFont(new Font("Century Gothic", Font.BOLD, 15));
                
                btnSair.addActionListener(e -> System.exit(0));
                
                GroupLayout gl_contentPane = new GroupLayout(contentPane);
                gl_contentPane.setHorizontalGroup(
                	gl_contentPane.createParallelGroup(Alignment.TRAILING)
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addContainerGap(91, Short.MAX_VALUE)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                					.addGroup(gl_contentPane.createSequentialGroup()
                						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                							.addGroup(gl_contentPane.createSequentialGroup()
                								.addComponent(btnIncluir, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                								.addGap(21)
                								.addComponent(btnAlterar, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                								.addGap(21)
                								.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
                							.addGroup(gl_contentPane.createSequentialGroup()
                								.addComponent(btnConsultar, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                								.addGap(21)
                								.addComponent(btnListagem, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                								.addGap(70)))
                						.addGap(81))
                					.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                						.addComponent(texto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                						.addGap(166)))
                				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                					.addComponent(btnSair)
                					.addGap(252))))
                );
                gl_contentPane.setVerticalGroup(
                	gl_contentPane.createParallelGroup(Alignment.TRAILING)
                		.addGroup(gl_contentPane.createSequentialGroup()
                			.addContainerGap()
                			.addComponent(texto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                			.addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                				.addComponent(btnIncluir, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                				.addComponent(btnAlterar, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                				.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                			.addGap(18)
                			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                				.addComponent(btnConsultar, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                				.addComponent(btnListagem, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                			.addGap(66)
                			.addComponent(btnSair)
                			.addContainerGap())
                );
                contentPane.setLayout(gl_contentPane);

    }
	
	public static void main(String[] args) {

		var ctx = new SpringApplicationBuilder(TelaPrincipal.class).headless(false).run(args);

		EventQueue.invokeLater(() -> {

			var ex = ctx.getBean(TelaPrincipal.class);
			ex.setVisible(true);
		});
	}
}
