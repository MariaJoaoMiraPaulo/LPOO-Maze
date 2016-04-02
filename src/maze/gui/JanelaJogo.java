package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import maze.gui.GraficosJogo.EstadoJogo;
import maze.gui.VitoriaDerrota.Imagem;
import maze.logic.Jogo;
import maze.logic.Jogo.Movimento;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

public class JanelaJogo {

	private final int WIDTH=450, HEIGHT=350;

	private JFrame frmJogoDoLabirinto;
	private JTextField dimensaoLabirinto;
	private JTextField numeroDragoes;
	private JButton btnDireita;
	private JButton btnBaixo;
	private JButton btnEsquerda;
	private JButton btnCima;
	private Jogo jogo;
	private JTextArea mostradorLabirinto;
	private JTextArea textArea;
	private JPanel desenhoLabirinto;
	private JPanel vencedor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaJogo window = new JanelaJogo();
					window.frmJogoDoLabirinto.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JanelaJogo() {
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {


		frmJogoDoLabirinto = new JFrame();
		frmJogoDoLabirinto.setResizable(true);
		frmJogoDoLabirinto.setTitle("Jogo do Labirinto");
		frmJogoDoLabirinto.setBounds(100, 100, 623, 466);
		frmJogoDoLabirinto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJogoDoLabirinto.getContentPane().setLayout(null);

		JLabel lblDimensaoDoLabirinto = new JLabel("Dimensao do labirinto");
		lblDimensaoDoLabirinto.setBounds(30, 41, 174, 14);
		frmJogoDoLabirinto.getContentPane().add(lblDimensaoDoLabirinto);

		JLabel lblNumeroDeDragoes = new JLabel("Numero de dragoes\r\n");
		lblNumeroDeDragoes.setBounds(30, 66, 141, 14);
		frmJogoDoLabirinto.getContentPane().add(lblNumeroDeDragoes);

		JLabel lblTipoDeDragoes = new JLabel("Tipo de dragoes\r\n");
		lblTipoDeDragoes.setBounds(30, 102, 114, 14);
		frmJogoDoLabirinto.getContentPane().add(lblTipoDeDragoes);

		dimensaoLabirinto = new JTextField();
		dimensaoLabirinto.setBounds(158, 38, 108, 20);
		frmJogoDoLabirinto.getContentPane().add(dimensaoLabirinto);
		dimensaoLabirinto.setColumns(10);

		numeroDragoes = new JTextField();
		numeroDragoes.setColumns(10);
		numeroDragoes.setBounds(158, 67, 108, 20);
		frmJogoDoLabirinto.getContentPane().add(numeroDragoes);

		JComboBox modosJogo = new JComboBox();
		modosJogo.setModel(new DefaultComboBoxModel(new String[] {"Estaticos", "Moveis", "A dormir"}));
		modosJogo.setSelectedIndex(1);
		modosJogo.setBounds(158, 99, 111, 20);
		frmJogoDoLabirinto.getContentPane().add(modosJogo);

		btnCima = new JButton("Cima");
		btnCima.setEnabled(false);
		btnCima.setBounds(103, 236, 117, 29);
		frmJogoDoLabirinto.getContentPane().add(btnCima);

		btnEsquerda = new JButton("Esquerda");
		btnEsquerda.setEnabled(false);
		btnEsquerda.setBounds(30, 276, 117, 29);
		frmJogoDoLabirinto.getContentPane().add(btnEsquerda);

		btnDireita = new JButton("Direita");
		btnDireita.setEnabled(false);
		btnDireita.setBounds(162, 276, 117, 29);
		frmJogoDoLabirinto.getContentPane().add(btnDireita);

		btnBaixo = new JButton("Baixo");
		btnBaixo.setEnabled(false);
		btnBaixo.setBounds(103, 312, 117, 29);
		frmJogoDoLabirinto.getContentPane().add(btnBaixo);

		btnCima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processarDirecao(Movimento.CIMA);
			}
		});

		btnEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processarDirecao(Movimento.ESQUERDA);
			}
		});

		btnDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processarDirecao(Movimento.DIREITA);
			}

		});


		btnBaixo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processarDirecao(Movimento.BAIXO);
			}

		});
		

		desenhoLabirinto = new GraficosJogo();
		desenhoLabirinto.setFocusTraversalPolicyProvider(true);
		desenhoLabirinto.setBounds(303, 11, 294, 264);
		frmJogoDoLabirinto.getContentPane().add(desenhoLabirinto);

		mostradorLabirinto = new JTextArea();
		desenhoLabirinto.add(mostradorLabirinto);
		mostradorLabirinto.setFont(new Font("Courier New", Font.PLAIN, 13));
		mostradorLabirinto.setEditable(false);

		//		mostradorLabirinto = new JTextArea();
		//		mostradorLabirinto.setFont(new Font("Courier New", Font.PLAIN, 13));
		//		mostradorLabirinto.setEditable(false);

		vencedor = new VitoriaDerrota();
		vencedor.setBounds(303, 11, 400, 300);
		frmJogoDoLabirinto.getContentPane().add(vencedor);

		JButton btnGerarLabirinto = new JButton("Gerar Labirinto");
		btnGerarLabirinto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nDragoes=0;
				try{
					if(numeroDragoes.getText().equals("")){
						JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Preencha a caixa com o numero de dragoes");
						return;
					}
					else {
						nDragoes=Integer.parseInt(numeroDragoes.getText());
						if(nDragoes>5){
							JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Dragoes a mais!");
							return;
						}
					}
				}
				catch (NumberFormatException ex){
					JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Formato n�o v�lido");
					return;
				}

				int dimensao=0;
				try{
					if(dimensaoLabirinto.getText().equals("")){
						dimensao=11;
					}
					else {
						dimensao=Integer.parseInt(dimensaoLabirinto.getText());
						if(dimensao%2==0){
							JOptionPane.showMessageDialog(frmJogoDoLabirinto, "A dimensao do labirinto tem de ser impar!");
							return;
						}
					}
				}
				catch (NumberFormatException ex){
					JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Formato n�o v�lido");
					return;
				}

				jogo=new Jogo(nDragoes,dimensao);
				if(modosJogo.getSelectedItem().equals("Estaticos"))
					jogo.setModoJogo(1);
				else if(modosJogo.getSelectedItem().equals("Moveis"))
					jogo.setModoJogo(2);
				else jogo.setModoJogo(3);

				//mostradorLabirinto.setText(jogo.getTab().paraString());
				((GraficosJogo) desenhoLabirinto).mudarEstadoJogo(EstadoJogo.COM_LABIRINTO);
				((GraficosJogo) desenhoLabirinto).setLabirinto(jogo.getTab());
				frmJogoDoLabirinto.setBounds(0, 0,desenhoLabirinto.getX()+ dimensao * 40+50, desenhoLabirinto.getY()+ dimensao * 40 +50);
				desenhoLabirinto.setBounds(desenhoLabirinto.getX(), desenhoLabirinto.getY(), desenhoLabirinto.getX()+ dimensao * 40, desenhoLabirinto.getY()+ dimensao * 40);
				desenhoLabirinto.setVisible(true);
				vencedor.setVisible(false);
				desenhoLabirinto.repaint();
				setEnableEmVariosBotoes(true);

			}
		});
	
		desenhoLabirinto.requestFocus();
		frmJogoDoLabirinto.pack();
		
		desenhoLabirinto.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				MovimentoTecla(e);

			}

			@Override
			public void keyReleased(KeyEvent e) {
			}			
		});
		
		
		btnGerarLabirinto.setBounds(80, 142, 174, 23);
		frmJogoDoLabirinto.getContentPane().add(btnGerarLabirinto);

		JButton btnTerminarPrograma = new JButton("Terminar Programa");
		btnTerminarPrograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnTerminarPrograma.setBounds(80, 188, 174, 23);
		frmJogoDoLabirinto.getContentPane().add(btnTerminarPrograma);
		
	}

	public void setEnableEmVariosBotoes(boolean flag){
		btnBaixo.setEnabled(flag);
		btnCima.setEnabled(flag);
		btnEsquerda.setEnabled(flag);
		btnDireita.setEnabled(flag);
	}

	public void processarDirecao(Movimento direcao){
		if(jogo.jogada(direcao)){
			acabouJogo();
		}
		//mostradorLabirinto.setText(jogo.getTab().paraString());
		else {
			desenhoLabirinto.repaint();
		}
	}

	public void acabouJogo(){
		setEnableEmVariosBotoes(false);

		if(vencedor.getY()+ HEIGHT< frmJogoDoLabirinto.getHeight())		
			frmJogoDoLabirinto.setSize(vencedor.getX()+ WIDTH, frmJogoDoLabirinto.getHeight());
		else
			frmJogoDoLabirinto.setSize(vencedor.getX()+ WIDTH, vencedor.getY()+ HEIGHT);

		if(!jogo.dragoesVivos()){
			((VitoriaDerrota) vencedor).quemGanhou(true);
		}
		else {
			((VitoriaDerrota) vencedor).quemGanhou(false);
		}

		((VitoriaDerrota) vencedor).setImagem(Imagem.COM_IMAGEM);
		desenhoLabirinto.setVisible(false);
		vencedor.setVisible(true);
		vencedor.repaint();

		if(!jogo.dragoesVivos()){
			JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Ganhou o jogo!!");
		}
		else {
			JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Perdeu o jogo!!");
		}

	}
	
	public void MovimentoTecla(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT: 
			processarDirecao(Movimento.ESQUERDA);
			break;

		case KeyEvent.VK_RIGHT: 
			processarDirecao(Movimento.DIREITA);
			break;

		case KeyEvent.VK_UP: 
			processarDirecao(Movimento.CIMA);
			break;

		case KeyEvent.VK_DOWN: 
			processarDirecao(Movimento.BAIXO);
			break;
		}
	}
}
