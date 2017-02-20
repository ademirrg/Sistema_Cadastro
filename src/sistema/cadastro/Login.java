package sistema.cadastro;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

	public static final String COMMAND_INFO = "command_info";
	public static final String COMMAND_CADASTRO_CLIENTE = "command_cadastro_cliente";
	public static final String COMMAND_CONSULTAR_USUARIO = "command_consultar_usuario";
	public static final String COMMAND_ALTERAR_USUARIO = "command_alterar_usuario";
	public static final String COMMAND_CADASTRO_USUARIO = "command_cadastro_usuario";
	public static final String COMMAND_SAIR = "command_sair";
	public static final String COMMAND_TELA_INICIAL = "command_inicial" ;

	private static final long serialVersionUID = 1L; 
	
	private JTextField tUser = new JTextField();
	private JTextField tPass = new JPasswordField();
	private CadastroUserVO cadastroVO = new CadastroUserVO();
	private CadastroUserDAO dao = new CadastroUserDAO();
	private Botao botoes = new Botao();
	private String oldEvent;

	public Login(String e) {
		this.oldEvent = e;
	}

	public void criaTela() {

		tUser.setBounds(150, 50, 120, 25);
		tPass.setBounds(150, 80, 120, 25);
		JLabel l1 = new JLabel("USU�RIO:");
		l1.setBounds(90, 50, 60, 30);
		JLabel l2 = new JLabel("SENHA:");
		l2.setBounds(103, 80, 47, 30);
		JLabel l3 = new JLabel("INFORME OS DADOS SOLICITADOS E PRESSIONE ENTRAR.");
		l3.setBounds(31, 15, 330, 30);
		JLabel l4 = new JLabel("ESQUECI MEU USU�RIO.");
		l4.setBounds(540, 15, 180, 30);
		JLabel l5 = new JLabel("ESQUECI MINHA SENHA.");
		l5.setBounds(540, 75, 180, 30);

		// Tela
		setTitle("LOGIN DE USU�RIO");
		setSize(700, 500);
		setLocation(450, 100);
		setResizable(false);
		setLayout(null);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Add
		getContentPane().add(l1);
		getContentPane().add(l2);
		getContentPane().add(l3);
		getContentPane().add(l4);
		getContentPane().add(l5);
		getContentPane().add(tUser);
		getContentPane().add(tPass);

	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "command_voltar":
			Tela tela = new Tela();
			tela.criaBotoes();
			tela.setVisible(true);
			dispose();
			break;
		case "command_login_usuario":
			pegaValorTela();
			break;
		case "command_encontrar_usuario":
			EsqueciUser encontrar = new EsqueciUser();
			encontrar.criaTelaEsqueciUser();
			encontrar.criaBotoesEsqueciUser();
			encontrar.setVisible(true);
			dispose();
			break;
		case "command_nova_senha":
			EsqueciSenha nova = new EsqueciSenha();
			nova.criaTelaEsqueciSenha();
			nova.criaBotoesEsqueciSenha();
			nova.setVisible(true);
			dispose();
			break;
		default:
			break;
		}
	}

	public void pegaValorTela() {
		String user = (gettUser().getText());
		String pass = (gettPass().getText());
		String NomeUserVO = "";
		String SenhaUserVO = "";

		if (user.length() == 0 || user.length() < 4 || user.length() > 25) {
			JOptionPane.showMessageDialog(null,
					"CAMPO USU�RIO INV�LIDO!" + System.lineSeparator() + "O USU�RIO DEVE CONTER DE 4 A 25 CARACTERES",
					"ERRO", JOptionPane.ERROR_MESSAGE);

		}

		else if (pass.length() == 0 || pass.length() < 5 || pass.length() > 12) {
			JOptionPane.showMessageDialog(null,
					"CAMPO SENHA INV�LIDO!" + System.lineSeparator() + "A SENHA DEVE CONTER DE 5 A 12 CARACTERES",
					"ERRO", JOptionPane.ERROR_MESSAGE);
		}

		// Se todos os campos estiverem ok
		if (user.length() >= 4 && user.length() <= 25 && pass.length() >= 5 && pass.length() <= 12) {
			cadastroVO.setUser(user);
			cadastroVO.setPass(pass);

			// Realiza uma conulta no banco e verifica o usu�rio e senha
			// informado.
			try {
				dao.buscarDadosNaBaseUser(cadastroVO);
				NomeUserVO = cadastroVO.getNomeUser();
				SenhaUserVO = cadastroVO.getSenhaUser();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (user.equalsIgnoreCase(NomeUserVO) == false) {
				System.out.println(
						"Tentativa de login com o usu�rio " + user.toUpperCase() + ", usu�rio n�o cadastrado.");
				JOptionPane.showMessageDialog(null, "O USU�RIO " + user.toUpperCase() + " N�O EXISTE NA BASE.",
						"ERRO DE LOGIN", JOptionPane.ERROR_MESSAGE);

			}

			else if (pass.equals(SenhaUserVO) == false) {
				System.out.println("Tentativa de login com o usu�rio " + user.toUpperCase() + ", senha incorreta.");
				JOptionPane.showMessageDialog(null, "SENHA INCORRETA.", "ERRO DE LOGIN", JOptionPane.ERROR_MESSAGE);
			}

			else {
				CadastroUserVO.setOldUser(user);
				criarTelaNova();	
			}
		}
	}

	protected void criarTelaNova() {
		if(oldEvent == null) {
			
		}
		CadastroUser cadastroUser;
		switch (oldEvent) {
		case COMMAND_SAIR:
			System.exit(0);
			break;
		case COMMAND_CADASTRO_USUARIO:
			cadastroUser = new CadastroUser();
			cadastroUser.criaTelaCadastroUser();
			cadastroUser.criaBotoes();
			cadastroUser.setVisible(true);
			dispose();
			break;
		case COMMAND_ALTERAR_USUARIO:
			cadastroUser = new CadastroUser();
			cadastroUser.criaTelaAlteraUser();
			cadastroUser.criaBotoesAltera();
			cadastroUser.setVisible(true);
			dispose();
			break;
		case COMMAND_CONSULTAR_USUARIO:			
			cadastroUser = new CadastroUser();
			cadastroUser.criaTelaConsultaUser();
			cadastroUser.criaBotoesConsulta();
			cadastroUser.setVisible(true);
			dispose();
			break;
		case COMMAND_CADASTRO_CLIENTE:
			CadastroCli cadastroCli = new CadastroCli();
			cadastroCli.criaTelaCadastroCli();
			cadastroCli.criaBotoes();
			cadastroCli.setVisible(true);
			dispose();
			break;
		case COMMAND_INFO:
			JOptionPane.showMessageDialog(null, "Vers�o: 0.1" + System.lineSeparator() + "Data de cria��o: 09/02/2017" + System.lineSeparator() + "Criado por: Ademir Rocha", "SOBRE",JOptionPane.INFORMATION_MESSAGE);
			break;

		}
		
	}

	public void criaBotoesLogin() {

		botoes.definirBotoesTelaLoginUser(this, this);
	}

	public JTextField gettUser() {
		return tUser;
	}

	public void settUser(JTextField tUser) {
		this.tUser = tUser;
	}

	public JTextField gettPass() {
		return tPass;
	}

	public void settPass(JTextField tPass) {
		this.tPass = tPass;
	}

}