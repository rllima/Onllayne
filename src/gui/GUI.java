package gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import basic.Chat;
import basic.User;
import control.Control;
import file.FileReciev;
import file.FileSender;
import file.ProgressBarDown;
import file.ProgressBarUp;
import repository.RepositoryChat;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.MatteBorder;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import java.awt.Choice;
import java.awt.Panel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

/**
 * 
 * @author Rodrigo de Lima Oliveira
 *
 */
public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9194116232055696952L;

	private JPanel basePanel;
	private JPanel logInPanel;
	private JButton logInButton;
	private JSeparator separator;
	private JSeparator separator1;
	private JSeparator separator2;
	private JLabel iconUser;
	private JLabel iconPassword;
	private JPasswordField passwordField;
	private JTextField userField;
	private JLabel singInLabel;
	private JPanel singInPanel;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JSeparator separator_6;
	private JSeparator separator_7;
	private JSeparator separator_1;
	private JSeparator separator_8;
	private JSeparator separator_9;
	private JSeparator separator_10;
	private JSeparator separator_11;
	private JSeparator separator_12;
	private JLabel nameLabel;
	private JLabel LastNameLabel;
	private JLabel emailLabel;
	private JLabel nickLabel;
	private JLabel passwordLabel;
	private JLabel againPasswordLabel;
	private JLabel birthdayLabel;
	private JSeparator separator_13;
	private JSeparator separator_14;
	private JSeparator separator_15;
	private JSeparator separator_16;
	private JLabel sexoLabel;
	private JComboBox<Object> dayComboBox;
	private JComboBox<Object> monthComboBox;
	private JComboBox<Object> yearComboBox;
	private JComboBox<Object> genderComboBox;
	private JButton singInButton;
	private JButton logInBackButton;
	private JTextField nameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JTextField nickField;
	private JTextField imageField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private JLabel label;
	private JLabel validMaillabel;
	private JLabel validSenhalabel;
	private final JList<String> contList = new JList<String>();
	private JPanel chatPanel;
	private JButton btn_ip;
	private JLabel lblIp;
	private JTextField textIP1;
	private JTextField textPorta;
	private JLabel lbl_portErro;
	private JLabel lbl_ipErro;
	private JButton btnNovoGrupo;
	private JTextField textField;
	private JButton button_arquivo;
	private JSeparator separator_20;
	private JSeparator separator_21;
	private JPanel panel_chat;
	private JLabel valid_nick;
	private JLabel valid_name;
	private JLabel valid_sobre;
	private JScrollPane scrollPane;
	private JLabel erroData_label;
	private JScrollPane scrollPane_1;
	private JLabel nickErro;
	private JLabel passwordErro;
	private User user;
	private JButton btnNovoChat;
	private RepositoryChat repChat = new RepositoryChat();
	private JButton btnBuscar;
	private boolean SA = true;
	private JPanel panel_nomeGrup;
	private JLabel lblNewLabel_1;
	private JTextField textGroupName;
	private JButton button;
	private JLabel erroGroupName;
	private JButton btnVoltar;
	private Control control;
	private Vector<String> ids;
	private JLabel label_ev;
	private JLabel label_etr;
	private JLabel label_ld;
	private JTextPane textPane;
	private JLabel ChatName;
	private JScrollBar var;
	private JLabel label_Verif;
	private JButton btnEnviar;
	private JLabel label_infos;
	private JLabel lbl_c;
	private Panel panel_dow;
	private JButton button_play;
	private JButton button_cancel;
	private boolean play = false;
	private JLabel label_rtt;
	private JLabel lbl_vel;
	private JLabel label_time;
	private String serverIP = "G1C22";
	private int serverPort = 12345;
	private JProgressBar progressBar;
	private Choice choice_arquivo;
	private String directoryUp;
	private String directoryDown;
	private JPanel panel_te;
	private FileReciev reciever;

	/**
	 * Classe myThread, é reponsavel pela atualização das mensagens de usuário e
	 * tambem pelas mensagens recebidas de outros usuarios. Tambem realiza o
	 * mecanismo de "Enviado", "Recebido", e "Visto". se baseando em no sistema
	 * de push com o servidor e variaveis booleanas.
	 */

	public class myThread extends Thread {

		public myThread() {
			this.start();
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DefaultListModel<String> dlm = new DefaultListModel<String>();
				Iterator<Chat> itChat = repChat.iterator();
				ids.clear();
				while (itChat.hasNext()) {
					Chat chat = itChat.next();
					if (chat != null) {
						ids.addElement(chat.getId());
						int i = contList.getSelectedIndex();
						dlm.addElement((chat.getHave() && i != dlm.size() ? "\u25CF " : "    ") + chat.getName());
					}
				}
				if (SA) {
					if (contList.getModel().getSize() <= dlm.getSize()) {
						int i = contList.getSelectedIndex();
						contList.setModel(dlm);
						contList.setSelectedIndex(i);
						i = contList.getSelectedIndex();

						label_ev.setVisible(true);
						boolean sent = false, seen = false;

						if (i >= 0 && i < ids.size() && ids.get(i) != null) {
							Chat chat = repChat.search(ids.get(i));
							if (chat != null) {
								sent = chat.getSent();
								seen = chat.getSeen();
								if (chat.getSb().substring(0).length() > textPane.getText().length()) {
									control.seenMsg(ids.get(i));
								}
								if (choice_arquivo.getItemCount() != chat.getSizeFiles()) {
									choice_arquivo.removeAll();
									Iterator<String> it = chat.getFiles().iterator();
									while (it.hasNext()) {
										choice_arquivo.add(it.next());
									}
								}
								label_etr.setVisible(sent);
								label_ld.setVisible(seen);
								ChatName.setText("<html><b>Falando com: </b>" + contList.getSelectedValue().toString()
										+ "</html>");
								// if(){
								String aux = ids.get(i);
								Chat chataux = repChat.search(aux);
								if (chataux != null) {
									StringBuffer sb = chataux.getSb();
									if (sb != null) {
										aux = sb.substring(0);
										textPane.setText(aux);
									}
								}

								// }

								var.setValue(var.getMaximum());
								repChat.update(chat);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {

		this.ids = new Vector<String>();
		control = new Control(serverIP, serverPort, this, this.repChat);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Icons\\2_Flat_logo_on_transparent_114x74.png"));

		/*-----------------------------------------------------------------------------------------------------------------------*/

		String[] genders = { "M", "F" };
		String[] days = new String[31];
		for (int i = 0; i < 31; i++)
			days[i] = (i + 1) + "";
		String[] months = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
				"Outubro", "Novembro", "Dezembro" };
		String[] years = new String[150];
		String data = new Date().toString();
		int ano = Integer.parseInt(data.toString().substring(data.length() - 4, data.length()));
		for (int i = 0; i < 150; i++)
			years[i] = (ano - i) + "";
		/*----------------------------------------------------------------------------------------------------------------*/
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		basePanel = new JPanel();
		basePanel.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		basePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(basePanel);
		basePanel.setLayout(null);

		chatPanel = new JPanel();
		chatPanel.setBounds(0, 0, 894, 672);
		chatPanel.setVisible(false);
		
				logInPanel = new JPanel();
				logInPanel.setBounds(0, 0, 894, 671);
				logInPanel.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
				basePanel.add(logInPanel);
				logInPanel.setLayout(null);
				
						logInButton = new JButton("Entrar");
						logInButton.setForeground(Color.WHITE);
						logInButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								String path = "repChat//" + userField.getText() + "//";
								new File(path).mkdir();
								repChat.setBasepath(path);
								control.logIn(userField.getText(), new String(passwordField.getPassword()));
							}
						});
						logInButton.setBorder(null);
						logInButton.setBackground(new Color(0, 153, 153));
						logInButton.setFont(new Font("Dialog", Font.BOLD, 14));
						logInButton.setBounds(327, 480, 240, 40);
						logInButton.setFocusPainted(false);
						logInPanel.add(logInButton);
						
								separator = new JSeparator();
								separator.setForeground(Color.LIGHT_GRAY);
								separator.setBackground(Color.LIGHT_GRAY);
								separator.setBounds(327, 440, 240, 1);
								logInPanel.add(separator);
								
										separator1 = new JSeparator();
										separator1.setForeground(Color.LIGHT_GRAY);
										separator1.setBackground(Color.LIGHT_GRAY);
										separator1.setBounds(327, 400, 240, 1);
										logInPanel.add(separator1);
										
												separator2 = new JSeparator();
												separator2.setForeground(Color.LIGHT_GRAY);
												separator2.setBackground(Color.LIGHT_GRAY);
												separator2.setBounds(327, 360, 240, 1);
												logInPanel.add(separator2);
												
														iconUser = new JLabel("");
														iconUser.setHorizontalAlignment(SwingConstants.CENTER);
														iconUser.setIcon(new ImageIcon(GUI.class.getResource("/user.png")));
														iconUser.setBounds(328, 361, 38, 38);
														logInPanel.add(iconUser);
														
																iconPassword = new JLabel("");
																iconPassword.setHorizontalAlignment(SwingConstants.CENTER);
																iconPassword.setIcon(new ImageIcon(GUI.class.getResource("/padlock.png")));
																iconPassword.setBounds(328, 401, 38, 38);
																logInPanel.add(iconPassword);
																
																		passwordField = new JPasswordField();
																		passwordField.setCaretColor(Color.WHITE);
																		passwordField.setForeground(Color.WHITE);
																		passwordField.setFont(new Font("Dialog", Font.PLAIN, 14));
																		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
																		passwordField.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
																		passwordField.setBorder(null);
																		passwordField.setBounds(367, 402, 200, 38);
																		passwordField.addKeyListener(new KeyAdapter() {
																			public void keyPressed(KeyEvent k) {
																				if (k.getKeyCode() == KeyEvent.VK_ENTER) {
																					logInButton.doClick();
																				}
																			}
																		});
																		logInPanel.add(passwordField);
																		
																				userField = new JTextField();
																				userField.setCaretColor(Color.WHITE);
																				userField.addKeyListener(new KeyAdapter() {
																					public void keyPressed(KeyEvent k) {
																						if (k.getKeyCode() == KeyEvent.VK_ENTER) {
																							logInButton.doClick();
																						}
																					}
																				});
																				userField.setForeground(Color.WHITE);
																				userField.setFont(new Font("Dialog", Font.PLAIN, 14));
																				userField.setBorder(null);
																				userField.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
																				userField.setBounds(367, 362, 200, 38);
																				logInPanel.add(userField);
																				userField.setColumns(10);
																				
																						singInLabel = new JLabel("Cadastre-se!");
																						singInLabel.addMouseListener(new MouseAdapter() {
																							@Override
																							public void mouseClicked(MouseEvent arg0) {
																								singInPanel.setVisible(true);
																								logInPanel.setVisible(false);
																								unlockSingIn();
																								dayComboBox.setEditable(false);
																								yearComboBox.setEditable(false);
																								monthComboBox.setEditable(false);
																								genderComboBox.setEditable(false);
																							}
																						});
																						singInLabel.setHorizontalAlignment(SwingConstants.CENTER);
																						singInLabel.setForeground(Color.WHITE);
																						singInLabel.setFont(new Font("Dialog", Font.BOLD, 12));
																						singInLabel.setBounds(388, 531, 116, 40);
																						logInPanel.add(singInLabel);
																						
																								label = new JLabel("");
																								label.setIcon(new ImageIcon(GUI.class.getResource("/Logo_Principal.png")));
																								label.setHorizontalAlignment(SwingConstants.CENTER);
																								label.setBorder(null);
																								label.setBounds(346, 209, 200, 117);
																								
																										logInPanel.add(label);
																										
																												JPanel IPpanel = new JPanel();
																												IPpanel.setBorder(new MatteBorder(1, 0, 1, 0, (Color) Color.WHITE));
																												IPpanel.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
																												IPpanel.setBounds(633, 476, 251, 135);
																												logInPanel.add(IPpanel);
																												IPpanel.setLayout(null);
																												IPpanel.setVisible(false);
																												
																														lblIp = new JLabel("IP:");
																														lblIp.setForeground(Color.WHITE);
																														lblIp.setFont(new Font("Dialog", Font.PLAIN, 14));
																														lblIp.setBounds(22, 34, 23, 30);
																														IPpanel.add(lblIp);
																														
																																textIP1 = new JTextField();
																																textIP1.setBorder(null);
																																
																																		textIP1.setBounds(55, 40, 182, 23);
																																		IPpanel.add(textIP1);
																																		textIP1.setColumns(10);
																																		
																																				JLabel lblPorta = new JLabel("Porta:");
																																				lblPorta.setForeground(Color.WHITE);
																																				lblPorta.setFont(new Font("Dialog", Font.PLAIN, 14));
																																				lblPorta.setBounds(22, 75, 46, 30);
																																				IPpanel.add(lblPorta);
																																				
																																						textPorta = new JTextField();
																																						textPorta.setBorder(null);
																																						textPorta.setColumns(10);
																																						textPorta.setBounds(78, 81, 90, 23);
																																						IPpanel.add(textPorta);
																																						
																																								JButton btnOk = new JButton("OK");
																																								btnOk.addActionListener(new ActionListener() {
																																									public void actionPerformed(ActionEvent e) {
																																										serverPort = Integer.parseInt(textPorta.getText());
																																										serverIP = textIP1.getText();
																																										if (validPort(serverPort)) {
																																											control.setPort(serverPort);
																																											lbl_portErro.setText(null);
																																										} else {
																																											lbl_portErro.setText("*Número de porta não válida");
																																										}
																																										control.setIp(serverIP);
																																										IPpanel.setVisible(false);

																																									}
																																								});
																																								btnOk.setForeground(Color.WHITE);
																																								btnOk.setFont(new Font("Dialog", Font.BOLD, 14));
																																								btnOk.setFocusPainted(false);
																																								btnOk.setBorder(null);
																																								btnOk.setBackground(new Color(0, 153, 153));
																																								btnOk.setBounds(191, 79, 46, 23);
																																								IPpanel.add(btnOk);
																																								
																																										lbl_portErro = new JLabel("");
																																										lbl_portErro.setFont(new Font("Tahoma", Font.PLAIN, 9));
																																										lbl_portErro.setForeground(Color.WHITE);
																																										lbl_portErro.setBounds(78, 104, 159, 14);
																																										IPpanel.add(lbl_portErro);
																																										
																																												lbl_ipErro = new JLabel("");
																																												lbl_ipErro.setForeground(Color.WHITE);
																																												lbl_ipErro.setFont(new Font("Tahoma", Font.PLAIN, 9));
																																												lbl_ipErro.setBounds(55, 62, 159, 14);
																																												IPpanel.add(lbl_ipErro);
																																												
																																														btn_ip = new JButton("");
																																														btn_ip.addActionListener(new ActionListener() {
																																															boolean show = false;

																																															public void actionPerformed(ActionEvent arg0) {
																																																{
																																																	if (!show) {
																																																		IPpanel.setVisible(true);
																																																		show = true;
																																																	} else {
																																																		IPpanel.setVisible(false);
																																																		show = false;
																																																	}
																																																}

																																															}
																																														});
																																														btn_ip.setIcon(new ImageIcon(GUI.class.getResource("/settings.png")));
																																														btn_ip.setBounds(828, 622, 56, 38);
																																														btn_ip.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
																																														btn_ip.setBorderPainted(false);
																																														btn_ip.setContentAreaFilled(false);
																																														btn_ip.setFocusPainted(false);
																																														logInPanel.add(btn_ip);
																																														
																																																nickErro = new JLabel("");
																																																nickErro.setForeground(Color.WHITE);
																																																nickErro.setFont(new Font("Dialog", Font.PLAIN, 12));
																																																nickErro.setBorder(null);
																																																nickErro.setBounds(577, 360, 182, 38);
																																																logInPanel.add(nickErro);
																																																
																																																		passwordErro = new JLabel("");
																																																		passwordErro.setForeground(Color.WHITE);
																																																		passwordErro.setFont(new Font("Dialog", Font.PLAIN, 12));
																																																		passwordErro.setBorder(null);
																																																		passwordErro.setBounds(577, 400, 182, 38);
																																																		logInPanel.add(passwordErro);

		singInPanel = new JPanel();
		singInPanel.setBounds(0, 0, 894, 671);
		basePanel.add(singInPanel);
		singInPanel.setVisible(false);
		singInPanel.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		singInPanel.setLayout(null);

		separator_2 = new JSeparator();
		separator_2.setForeground(Color.LIGHT_GRAY);
		separator_2.setBackground(Color.LIGHT_GRAY);
		separator_2.setBounds(379, 228, 474, 1);
		singInPanel.add(separator_2);

		separator_3 = new JSeparator();
		separator_3.setForeground(Color.LIGHT_GRAY);
		separator_3.setBackground(Color.LIGHT_GRAY);
		separator_3.setBounds(39, 228, 300, 1);
		singInPanel.add(separator_3);

		separator_4 = new JSeparator();
		separator_4.setForeground(Color.LIGHT_GRAY);
		separator_4.setBackground(Color.LIGHT_GRAY);
		separator_4.setBounds(39, 268, 814, 1);
		singInPanel.add(separator_4);

		separator_5 = new JSeparator();
		separator_5.setForeground(Color.LIGHT_GRAY);
		separator_5.setBackground(Color.LIGHT_GRAY);
		separator_5.setBounds(39, 308, 814, 1);
		singInPanel.add(separator_5);

		separator_6 = new JSeparator();
		separator_6.setForeground(Color.LIGHT_GRAY);
		separator_6.setBackground(Color.LIGHT_GRAY);
		separator_6.setBounds(39, 188, 300, 1);
		singInPanel.add(separator_6);

		separator_7 = new JSeparator();
		separator_7.setForeground(Color.LIGHT_GRAY);
		separator_7.setBackground(Color.LIGHT_GRAY);
		separator_7.setBounds(379, 188, 474, 1);
		singInPanel.add(separator_7);

		separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBackground(Color.LIGHT_GRAY);
		separator_1.setBounds(39, 348, 260, 1);
		singInPanel.add(separator_1);

		separator_8 = new JSeparator();
		separator_8.setForeground(Color.LIGHT_GRAY);
		separator_8.setBackground(Color.LIGHT_GRAY);
		separator_8.setBounds(39, 388, 260, 1);
		singInPanel.add(separator_8);

		separator_9 = new JSeparator();
		separator_9.setForeground(Color.LIGHT_GRAY);
		separator_9.setBackground(Color.LIGHT_GRAY);
		separator_9.setBounds(493, 348, 360, 1);
		singInPanel.add(separator_9);

		separator_10 = new JSeparator();
		separator_10.setForeground(Color.LIGHT_GRAY);
		separator_10.setBackground(Color.LIGHT_GRAY);
		separator_10.setBounds(493, 388, 360, 1);
		singInPanel.add(separator_10);

		separator_11 = new JSeparator();
		separator_11.setForeground(Color.LIGHT_GRAY);
		separator_11.setBackground(Color.LIGHT_GRAY);
		separator_11.setBounds(493, 428, 360, 1);
		singInPanel.add(separator_11);

		separator_12 = new JSeparator();
		separator_12.setForeground(Color.LIGHT_GRAY);
		separator_12.setBackground(Color.LIGHT_GRAY);
		separator_12.setBounds(493, 468, 360, 1);
		singInPanel.add(separator_12);

		separator_13 = new JSeparator();
		separator_13.setForeground(Color.LIGHT_GRAY);
		separator_13.setBackground(Color.LIGHT_GRAY);
		separator_13.setBounds(39, 428, 411, 1);
		singInPanel.add(separator_13);

		separator_15 = new JSeparator();
		separator_15.setForeground(Color.LIGHT_GRAY);
		separator_15.setBackground(Color.LIGHT_GRAY);
		separator_15.setBounds(342, 348, 108, 1);
		singInPanel.add(separator_15);

		separator_14 = new JSeparator();
		separator_14.setForeground(Color.LIGHT_GRAY);
		separator_14.setBackground(Color.LIGHT_GRAY);
		separator_14.setBounds(39, 468, 411, 1);
		singInPanel.add(separator_14);

		separator_16 = new JSeparator();
		separator_16.setForeground(Color.LIGHT_GRAY);
		separator_16.setBackground(Color.LIGHT_GRAY);
		separator_16.setBounds(342, 388, 108, 1);
		singInPanel.add(separator_16);

		JSeparator separator_19 = new JSeparator();
		separator_19.setForeground(Color.LIGHT_GRAY);
		separator_19.setBackground(Color.LIGHT_GRAY);
		separator_19.setBounds(39, 108, 814, 1);
		singInPanel.add(separator_19);

		nameLabel = new JLabel("Nome:");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		nameLabel.setBounds(39, 188, 49, 41);
		singInPanel.add(nameLabel);

		LastNameLabel = new JLabel("Sobrenome: ");
		LastNameLabel.setForeground(Color.WHITE);
		LastNameLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		LastNameLabel.setBounds(379, 188, 89, 41);
		singInPanel.add(LastNameLabel);

		emailLabel = new JLabel("Email:");
		emailLabel.setForeground(Color.WHITE);
		emailLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		emailLabel.setBounds(39, 268, 49, 41);
		singInPanel.add(emailLabel);

		nickLabel = new JLabel("Nick: ");
		nickLabel.setForeground(Color.WHITE);
		nickLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		nickLabel.setBounds(39, 348, 38, 41);
		singInPanel.add(nickLabel);

		passwordLabel = new JLabel("Senha: ");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		passwordLabel.setBounds(493, 348, 52, 41);
		singInPanel.add(passwordLabel);

		againPasswordLabel = new JLabel("Repetir senha: ");
		againPasswordLabel.setForeground(Color.WHITE);
		againPasswordLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		againPasswordLabel.setBounds(493, 428, 108, 41);
		singInPanel.add(againPasswordLabel);

		birthdayLabel = new JLabel("Data de Nasc.: ");
		birthdayLabel.setForeground(Color.WHITE);
		birthdayLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		birthdayLabel.setBounds(39, 428, 103, 41);
		singInPanel.add(birthdayLabel);

		sexoLabel = new JLabel("Sexo:");
		sexoLabel.setForeground(Color.WHITE);
		sexoLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		sexoLabel.setBounds(342, 348, 49, 41);
		singInPanel.add(sexoLabel);

		JLabel registerlabel = new JLabel("Cadastro");
		registerlabel.setVerticalAlignment(SwingConstants.BOTTOM);
		registerlabel.setForeground(Color.WHITE);
		registerlabel.setFont(new Font("Dialog", Font.BOLD, 50));
		registerlabel.setBounds(39, 30, 240, 80);
		singInPanel.add(registerlabel);

		dayComboBox = new JComboBox<Object>(days);
		dayComboBox.setBackground(Color.WHITE);
		dayComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		dayComboBox.setBounds(145, 439, 60, 20);
		dayComboBox.setEditable(false);
		singInPanel.add(dayComboBox);

		monthComboBox = new JComboBox<Object>(months);
		monthComboBox.setBackground(Color.WHITE);
		monthComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		monthComboBox.setBounds(215, 439, 145, 20);
		monthComboBox.setEditable(false);
		singInPanel.add(monthComboBox);

		yearComboBox = new JComboBox<Object>(years);
		yearComboBox.setBackground(Color.WHITE);
		yearComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		yearComboBox.setBounds(370, 439, 80, 20);
		yearComboBox.setEditable(false);
		singInPanel.add(yearComboBox);

		genderComboBox = new JComboBox<Object>(genders);
		genderComboBox.setBackground(Color.WHITE);
		genderComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		genderComboBox.setBounds(390, 360, 60, 20);
		singInPanel.add(genderComboBox);

		singInButton = new JButton("Cadastrar");
		
		singInButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				/**
				 * Essa verificação, assegura que todos os campos possuem as
				 * informações desejadas para o cadastro de um novo usuário.
				 * Informações necessárias para o contrutor da classe User.
				 */
				if (userOk()) {
					control.registerUser(nameField.getText(), lastNameField.getText(), emailField.getText(),
							nickField.getText(), new String(passwordField_1.getPassword()),
							Integer.parseInt(dayComboBox.getSelectedItem().toString()),
							monthComboBox.getSelectedIndex() + 1,
							Integer.parseInt(yearComboBox.getSelectedItem().toString()),
							genderComboBox.getSelectedItem().toString().charAt(0));
					label_Verif.setText(null);
					lockSingIn();
				} else {
					label_Verif.setText("*Verifique todas as informações e tente novamente");
				}
			}
		});
		singInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validDate(Integer.parseInt(dayComboBox.getSelectedItem().toString()),
						monthComboBox.getSelectedIndex() + 1,
						Integer.parseInt(yearComboBox.getSelectedItem().toString()))) {
					erroData_label.setText("*Não é um formato de data válido");
				} else
					erroData_label.setText(null);
			}
		});
		singInButton.setForeground(Color.WHITE);
		singInButton.setFont(new Font("Dialog", Font.BOLD, 14));
		singInButton.setBorder(null);
		singInButton.setBackground(new Color(0, 153, 153));
		singInButton.setBounds(613, 588, 240, 40);
		singInPanel.add(singInButton);

		logInBackButton = new JButton("");
		logInBackButton.setIcon(new ImageIcon("Icons\\back.png"));
		logInBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cleanSigIn();
				logInPanel.setVisible(true);
				singInPanel.setVisible(false);
			}
		});
		logInBackButton.setFocusPainted(false);
		logInBackButton.setContentAreaFilled(false);
		logInBackButton.setBorderPainted(false);
		logInBackButton.setBackground(new Color(44, 64, 84));
		logInBackButton.setBounds(816, 23, 49, 45);

		singInPanel.add(logInBackButton);

		nameField = new JTextField();
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!validName(nameField.getText()) && !nameField.getText().trim().isEmpty()) {
					valid_name.setText("*O nome não deve conter caracteres especiais ou numeros");
				} else
					valid_name.setText(null);
				if (nameField.getText().length() <= 1) {
					valid_name.setText("*O nome deve conter mais de 2 letras");
				} else
					valid_name.setText(null);
			}
		});
		nameField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				// Na variável "c" armazenamos o que o usuário digitou
				char c = evt.getKeyChar();

				// Aqui verificamos se o que foi digitado é um número, um
				// backspace ou um delete. Se for, consumimos o evento, ou seja,
				// o jTextField não receberá o valor digitado
				if ((c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE) {
					evt.consume();
				}
			}
		});

		nameField.setCaretColor(Color.WHITE);
		nameField.setForeground(Color.WHITE);
		nameField.setFont(new Font("Dialog", Font.PLAIN, 14));
		nameField.setBorder(null);
		nameField.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		nameField.setBounds(98, 200, 241, 20);

		singInPanel.add(nameField);
		nameField.setColumns(10);

		lastNameField = new JTextField();
		lastNameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!validName(lastNameField.getText()) && !lastNameField.getText().trim().isEmpty()) {
					valid_sobre.setText("*O nome não deve conter caracteres especiais ou numeros");
				} else
					valid_sobre.setText(null);

				if (lastNameField.getText().length() <= 1) {
					valid_sobre.setText("*O sobrenome deve conter mais de 2 letras");
				} else
					valid_sobre.setText(null);
			}
		});
		lastNameField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				// Na variável "c" armazenamos o que o usuário digitou
				char c = evt.getKeyChar();

				// Aqui verificamos se o que foi digitado é um número, um
				// backspace ou um delete. Se for, consumimos o evento, ou seja,
				// o jTextField não receberá o valor digitado
				if ((c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE) {
					evt.consume();
				}
			}
		});
		lastNameField.setCaretColor(Color.WHITE);
		lastNameField.setForeground(Color.WHITE);
		lastNameField.setFont(new Font("Dialog", Font.PLAIN, 14));
		lastNameField.setColumns(10);
		lastNameField.setBorder(null);
		lastNameField.setBackground(new Color(44, 64, 84));
		lastNameField.setBounds(478, 200, 375, 20);
		singInPanel.add(lastNameField);

		emailField = new JTextField();
		emailField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String email = emailField.getText();
				if (!valid(email) && !emailField.getText().trim().isEmpty()) {
					validMaillabel.setText("*Digite um email válido");
				} else
					validMaillabel.setText(null);
			}
		});

		emailField.setCaretColor(Color.WHITE);
		emailField.setForeground(Color.WHITE);
		emailField.setFont(new Font("Dialog", Font.PLAIN, 14));
		emailField.setColumns(10);
		emailField.setBorder(null);
		emailField.setBackground(new Color(44, 64, 84));
		emailField.setBounds(93, 280, 760, 20);
		singInPanel.add(emailField);

		nickField = new JTextField();
		nickField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!validNick(nickField.getText()) && !nickField.getText().trim().isEmpty()) {
					valid_nick.setText("*O nick não deve conter caracteres especiais");
				} else
					valid_nick.setText(null);

				if (nickField.getText().length() < 2) {
					valid_nick.setText("*O nick deve conter no minimo 3 caracteres");
				} else
					valid_nick.setText(null);
			}
		});
		nickField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				// Na variável "c" armazenamos o que o usuário digitou
				char c = evt.getKeyChar();

				// Aqui verificamos se o que foi digitado é um número, um
				// backspace ou um delete. Se for, consumimos o evento, ou seja,
				// o jTextField não receberá o valor digitado
				if ((c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE) {
					evt.consume();
				}
			}
		});

		erroData_label = new JLabel("");
		erroData_label.setForeground(Color.WHITE);
		erroData_label.setFont(new Font("Dialog", Font.PLAIN, 11));
		erroData_label.setBorder(null);
		erroData_label.setBounds(39, 468, 188, 14);
		singInPanel.add(erroData_label);

		valid_nick = new JLabel("");
		valid_nick.setBorder(null);
		valid_nick.setForeground(Color.WHITE);
		valid_nick.setFont(new Font("Dialog", Font.PLAIN, 12));
		valid_nick.setBounds(39, 388, 260, 14);
		singInPanel.add(valid_nick);
		nickField.setCaretColor(Color.WHITE);
		nickField.setForeground(Color.WHITE);
		nickField.setFont(new Font("Dialog", Font.PLAIN, 14));
		nickField.setColumns(10);
		nickField.setBorder(null);
		nickField.setBackground(new Color(44, 64, 84));
		nickField.setBounds(87, 360, 212, 20);
		singInPanel.add(nickField);

		imageField = new JTextField();
		imageField.setEditable(false);
		imageField.setForeground(Color.WHITE);
		imageField.setFont(new Font("Dialog", Font.PLAIN, 14));
		imageField.setColumns(10);
		imageField.setBorder(null);
		imageField.setBackground(new Color(44, 64, 84));
		imageField.setBounds(379, 520, 474, 20);
		singInPanel.add(imageField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setForeground(Color.WHITE);
		passwordField_1.setFont(new Font("Dialog", Font.PLAIN, 14));
		passwordField_1.setCaretColor(Color.WHITE);
		passwordField_1.setBorder(null);
		passwordField_1.setBounds(555, 360, 298, 20);
		passwordField_1.setBackground(new Color(44, 64, 84));
		singInPanel.add(passwordField_1);

		passwordField_2 = new JPasswordField();
		passwordField_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!(new String(passwordField_1.getPassword())).equals(new String(passwordField_2.getPassword()))) {
					validSenhalabel.setText("*As senhas não conferem");

				}
				if ((new String(passwordField_1.getPassword())).length() < 2) {
					validSenhalabel.setText("*A senha deve conter no minimo 3 caracteres");
				} else
					validSenhalabel.setText(null);
			}
		});
		passwordField_2.setForeground(Color.WHITE);
		passwordField_2.setFont(new Font("Dialog", Font.PLAIN, 14));
		passwordField_2.setCaretColor(Color.WHITE);
		passwordField_2.setBorder(null);
		passwordField_2.setBackground(new Color(44, 64, 84));
		passwordField_2.setBounds(611, 440, 242, 20);
		singInPanel.add(passwordField_2);

		validMaillabel = new JLabel("");
		validMaillabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		validMaillabel.setForeground(Color.WHITE);
		validMaillabel.setBorder(null);
		validMaillabel.setBounds(39, 308, 188, 14);
		singInPanel.add(validMaillabel);

		validSenhalabel = new JLabel("");
		validSenhalabel.setForeground(Color.WHITE);
		validSenhalabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		validSenhalabel.setBorder(null);
		validSenhalabel.setBounds(493, 468, 188, 20);
		singInPanel.add(validSenhalabel);
		basePanel.add(singInPanel);

		valid_name = new JLabel("");
		valid_name.setForeground(Color.WHITE);
		valid_name.setFont(new Font("Dialog", Font.PLAIN, 12));
		valid_name.setBorder(null);
		valid_name.setBounds(39, 228, 352, 14);
		singInPanel.add(valid_name);

		valid_sobre = new JLabel("");
		valid_sobre.setForeground(Color.WHITE);
		valid_sobre.setFont(new Font("Dialog", Font.PLAIN, 12));
		valid_sobre.setBorder(null);
		valid_sobre.setBounds(379, 228, 352, 14);
		singInPanel.add(valid_sobre);

		label_Verif = new JLabel("");
		label_Verif.setBorder(null);
		label_Verif.setForeground(Color.WHITE);
		label_Verif.setFont(new Font("Dialog", Font.BOLD, 14));
		label_Verif.setBounds(39, 573, 320, 20);
		singInPanel.add(label_Verif);
		chatPanel.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		basePanel.add(chatPanel);
		chatPanel.setLayout(null);

		panel_chat = new JPanel();
		panel_chat.setBackground((Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f)));
		panel_chat.setBorder(null);
		panel_chat.setBounds(301, 162, 593, 510);
		chatPanel.add(panel_chat);
		panel_chat.setLayout(null);
		panel_chat.setVisible(false);

		label_ld = new JLabel("");
		label_ld.setIcon(new ImageIcon("Icons\\3.png"));
		label_ld.setBorder(null);
		label_ld.setBounds(527, 0, 38, 44);
		panel_chat.add(label_ld);
		label_ld.setVisible(false);

		label_etr = new JLabel("");
		label_etr.setIcon(new ImageIcon("Icons\\success2.png"));
		label_etr.setBounds(537, 0, 38, 44);
		panel_chat.add(label_etr);
		label_etr.setVisible(false);

		separator_20 = new JSeparator();
		separator_20.setBounds(0, 0, 1, 510);
		panel_chat.add(separator_20);
		separator_20.setBackground(new Color(0, 153, 153));
		separator_20.setBorder(null);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 5, 1, 0, (Color) new Color(255, 255, 255)));
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(11, 44, 574, 371);
		scrollPane.setBackground(new Color(0, 153, 156));
		scrollPane.setForeground(new Color(0, 153, 156));

		var = scrollPane.getVerticalScrollBar();

		panel_chat.add(scrollPane);
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		textPane.setBorder(null);
		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sendM();

			}
		});

		btnEnviar.setForeground(Color.WHITE);
		btnEnviar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnEnviar.setFocusPainted(false);
		btnEnviar.setBorder(null);
		btnEnviar.setBackground(new Color(0, 153, 153));
		btnEnviar.setBounds(497, 469, 88, 30);
		panel_chat.add(btnEnviar);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					rootPane.setDefaultButton(btnEnviar);
					sendM();

				}
			}

		});
		textField.setBorder(new MatteBorder(2, 5, 2, 2, (Color) new Color(255, 255, 255)));
		textField.setBounds(12, 469, 475, 30);
		panel_chat.add(textField);
		textField.setColumns(10);

		ChatName = new JLabel("");
		ChatName.setHorizontalAlignment(SwingConstants.LEFT);
		ChatName.setFont(new Font("Dialog", Font.PLAIN, 16));
		ChatName.setForeground(Color.WHITE);
		ChatName.setBounds(29, 0, 485, 43);
		panel_chat.add(ChatName);

		label_ev = new JLabel("");
		label_ev.setIcon(new ImageIcon("Icons\\success1.png"));
		label_ev.setBorder(null);
		label_ev.setBounds(547, 0, 38, 44);
		panel_chat.add(label_ev);

		panel_dow = new Panel();
		panel_dow.setBounds(12, 421, 573, 44);
		panel_chat.add(panel_dow);
		panel_dow.setLayout(null);
		panel_dow.setVisible(false);

		progressBar = new JProgressBar();
		progressBar.setBorder(null);
		progressBar.setFont(new Font("Dialog", Font.PLAIN, 11));
		progressBar.setForeground(new Color(0, 153, 156));
		progressBar.setBounds(0, 5, 476, 22);
		progressBar.setBackground(Color.WHITE);
		panel_dow.add(progressBar);

		button_play = new JButton("");
		button_play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (play) {
					button_play.setIcon(new ImageIcon("Icons\\pause.png"));
					reciever.setPaused(false);
					play = false;
				} else {
					button_play.setIcon(new ImageIcon("Icons\\play-button (2).png"));
					reciever.setPaused(true);
					play = true;
				}

			}
		});
		button_play.setIcon(new ImageIcon("Icons\\pause.png"));
		button_play.setFocusPainted(false);
		button_play.setContentAreaFilled(false);
		button_play.setBorderPainted(false);
		button_play.setBackground(new Color(44, 64, 84));
		button_play.setBounds(489, 0, 38, 26);
		panel_dow.add(button_play);

		button_cancel = new JButton("");
		button_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_dow.setVisible(false);
				panel_te.setVisible(true);
				button_play.setVisible(false);
				play = false;
				button_cancel.setVisible(false);
				reciever.setCanceled(true);
			}
		});
		button_cancel.setIcon(new ImageIcon("Icons\\error.png"));
		button_cancel.setFocusPainted(false);
		button_cancel.setContentAreaFilled(false);
		button_cancel.setBorderPainted(false);
		button_cancel.setBackground(new Color(44, 64, 84));
		button_cancel.setBounds(535, 0, 38, 26);
		panel_dow.add(button_cancel);

		label_rtt = new JLabel("");
		label_rtt.setFont(new Font("Tahoma", Font.PLAIN, 9));
		label_rtt.setForeground(Color.WHITE);
		label_rtt.setBounds(186, 30, 157, 14);
		panel_dow.add(label_rtt);

		lbl_vel = new JLabel("");
		lbl_vel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lbl_vel.setForeground(Color.WHITE);
		lbl_vel.setBounds(3, 29, 77, 14);
		panel_dow.add(lbl_vel);

		label_time = new JLabel("");
		label_time.setFont(new Font("Tahoma", Font.PLAIN, 9));
		label_time.setForeground(Color.WHITE);
		label_time.setBounds(99, 30, 77, 14);
		panel_dow.add(label_time);

		panel_te = new JPanel();
		panel_te.setBackground((Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f)));
		panel_te.setBounds(11, 421, 574, 44);
		panel_chat.add(panel_te);
		panel_te.setLayout(null);

		choice_arquivo = new Choice();
		choice_arquivo.setBounds(53, 12, 468, 20);
		panel_te.add(choice_arquivo);

		button_arquivo = new JButton("");
		button_arquivo.setBounds(5, 7, 47, 32);
		panel_te.add(button_arquivo);
		button_arquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (UnsupportedLookAndFeelException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				JFileChooser seletorFoto = new JFileChooser();
				SwingUtilities.updateComponentTreeUI(seletorFoto);
				seletorFoto.setBounds(0, 0, 894, 672);
				seletorFoto.setBackground(Color.WHITE);
				basePanel.add(seletorFoto);
				seletorFoto.doLayout();
				seletorFoto.setVisible(true);
				int returnVal = seletorFoto.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					directoryUp = seletorFoto.getSelectedFile().getPath();
					panel_te.setVisible(false);
					button_play.setVisible(false);
					button_cancel.setVisible(false);
					panel_dow.setVisible(true);
					control.initUpload(ids.get(contList.getSelectedIndex()), seletorFoto.getSelectedFile().getName(),
							seletorFoto.getSelectedFile().length());
				} else {
					seletorFoto.setVisible(false);
					chatPanel.setVisible(true);
				}
			}
		});
		button_arquivo.setIcon(new ImageIcon("Icons\\attachment (2).png"));
		button_arquivo.setFocusPainted(false);
		button_arquivo.setContentAreaFilled(false);
		button_arquivo.setBorderPainted(false);
		button_arquivo.setBackground(new Color(44, 64, 84));

		JButton button_down = new JButton("");
		button_down.setBounds(527, 7, 47, 32);
		panel_te.add(button_down);
		button_down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (choice_arquivo.getSelectedIndex() != -1) {
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (ClassNotFoundException e4) {
						// TODO Auto-generated catch block
						e4.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (UnsupportedLookAndFeelException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					JFileChooser seletorFoto = new JFileChooser();
					seletorFoto.setSelectedFile(new File(choice_arquivo.getSelectedItem()));
					seletorFoto.setCurrentDirectory(new java.io.File("."));
					SwingUtilities.updateComponentTreeUI(seletorFoto);
					seletorFoto.setBounds(0, 0, 894, 672);
					seletorFoto.setBackground(Color.WHITE);
					basePanel.add(seletorFoto);
					seletorFoto.doLayout();
					seletorFoto.setVisible(true);
					int returnVal = seletorFoto.showSaveDialog(null);
					seletorFoto.setAcceptAllFileFilterUsed(false);
					seletorFoto.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						directoryDown = seletorFoto.getCurrentDirectory().toString();
						control.initDownload(ids.get(contList.getSelectedIndex()), choice_arquivo.getSelectedItem());
						play = false;
						button_play.setIcon(new ImageIcon("Icons\\pause.png"));

					} else {
						seletorFoto.setVisible(false);
						chatPanel.setVisible(true);
					}
				}

			}
		});
		button_down.setIcon(new ImageIcon("Icons\\download.png"));
		button_down.setFocusPainted(false);
		button_down.setContentAreaFilled(false);
		button_down.setBorderPainted(false);
		button_down.setBackground(new Color(44, 64, 84));
		label_ev.setVisible(false);

		panel_nomeGrup = new JPanel();
		panel_nomeGrup.setBounds(299, 162, 595, 44);
		chatPanel.add(panel_nomeGrup);
		panel_nomeGrup.setBackground(new Color(0, 153, 156));
		panel_nomeGrup.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 153, 156)));
		panel_nomeGrup.setLayout(null);
		panel_nomeGrup.setVisible(false);

		lblNewLabel_1 = new JLabel("Nome do grupo: ");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 11, 123, 22);
		panel_nomeGrup.add(lblNewLabel_1);

		textGroupName = new JTextField();
		textGroupName.setForeground(Color.WHITE);
		textGroupName.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.WHITE));
		textGroupName.setBackground(new Color(0, 153, 156));
		textGroupName.setFont(new Font("Dialog", Font.PLAIN, 12));
		textGroupName.setBounds(132, 14, 397, 19);
		panel_nomeGrup.add(textGroupName);
		textGroupName.setColumns(10);

		button = new JButton("OK");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] it = contList.getSelectedIndices();
				String[] str = new String[contList.getSelectedIndices().length];
				for (int i = 0; i < contList.getSelectedIndices().length; i++) {
					str[i] = contList.getModel().getElementAt(it[i]).toString();
				}
				String nameGroup = null;
				if (textGroupName.getText().trim().isEmpty()) {
					erroGroupName.setText("*Digite o nome do grupo!");
					erroGroupName.requestFocus();
				} else {
					nameGroup = textGroupName.getText();

				}

				textGroupName.setText(null);
				control.registerChatGroup(nameGroup, str);
				contList.setModel(new DefaultListModel<String>());
				SA = true;
			}
		});
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Dialog", Font.BOLD, 14));
		button.setFocusPainted(false);
		button.setBorder(null);
		button.setBackground(new Color(0, 153, 153));
		button.setBounds(539, 13, 46, 23);
		panel_nomeGrup.add(button);

		erroGroupName = new JLabel("");
		erroGroupName.setBorder(null);
		erroGroupName.setForeground(Color.WHITE);
		erroGroupName.setFont(new Font("Dialog", Font.PLAIN, 11));
		erroGroupName.setBounds(10, 30, 147, 14);
		panel_nomeGrup.add(erroGroupName);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		scrollPane_1.setBounds(0, 208, 297, 464);
		chatPanel.add(scrollPane_1);
		scrollPane_1.setViewportView(contList);
		contList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!SA) {
					if (contList.getSelectedIndices().length > 1) {
						btnNovoChat.setVisible(false);
						btnNovoGrupo.setVisible(true);
					} else {
						btnNovoGrupo.setVisible(false);
						btnNovoChat.setVisible(true);
					}
				}
				if (SA) {
					int i = contList.getSelectedIndex();
					if (i >= 0 && i < ids.size()) {
						panel_chat.setVisible(true);
						label_ev.setVisible(true);
						label_etr.setVisible(repChat.search(ids.get(i)).getSent());
						label_ld.setVisible(repChat.search(ids.get(i)).getSeen());
						ChatName.setText(
								"<html><b>Falando com: </b>" + contList.getSelectedValue().toString() + "</html>");

						String str = ids.get(i);
						Chat chataux = repChat.search(str);
						if (chataux != null) {
							StringBuffer sb = chataux.getSb();
							if (sb != null) {
								str = sb.substring(0);
								textPane.setText(str);
							}
						}
						control.seenMsg(ids.get(i));
					}
				}
			}
		});

		contList.setFont(new Font("Dialog", Font.PLAIN, 14));
		contList.setForeground(Color.WHITE);
		contList.setBackground(Color.getHSBColor(210 / 360.0f, 0.48f, 0.33f));
		contList.setBorder(new LineBorder(new Color(0, 0, 0), 0));

		contList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		chatPanel.setVisible(false);

		JLabel label_1 = new JLabel("");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setIcon(new ImageIcon("Icons\\ImageChat.png"));
		label_1.setBounds(0, 0, 297, 116);
		chatPanel.add(label_1);

		JPanel panel_cont = new JPanel();
		panel_cont.setBorder(new MatteBorder(1, 0, 1, 1, new Color(0, 153, 153)));
		panel_cont.setBackground(new Color(0, 153, 153));
		panel_cont.setBounds(0, 116, 894, 45);
		chatPanel.add(panel_cont);
		panel_cont.setLayout(null);

		lbl_c = new JLabel("Conversas");
		lbl_c.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 153, 156)));
		lbl_c.setFont(new Font("Dialog", Font.BOLD, 16));
		lbl_c.setForeground(Color.WHITE);
		lbl_c.setBounds(113, 0, 90, 41);
		panel_cont.add(lbl_c);

		label_infos = new JLabel("");
		label_infos.setHorizontalAlignment(SwingConstants.RIGHT);
		label_infos.setBounds(302, 0, 582, 45);
		panel_cont.add(label_infos);
		label_infos.setFont(new Font("Dialog", Font.BOLD, 16));
		label_infos.setForeground(Color.WHITE);

		separator_21 = new JSeparator();
		separator_21.setOrientation(SwingConstants.VERTICAL);
		separator_21.setForeground(new Color(0, 153, 153));
		separator_21.setBackground(new Color(0, 153, 153));
		separator_21.setBounds(299, 162, 2, 510);
		chatPanel.add(separator_21);

		JButton button_signOut = new JButton("");
		button_signOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_signOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				chatPanel.setVisible(false);
				logInPanel.setVisible(true);
				singInLabel.setVisible(true);
				unlockLogIn();

			}
		});
		button_signOut.setIcon(new ImageIcon("Icons\\next.png"));
		button_signOut.setFocusPainted(false);
		button_signOut.setContentAreaFilled(false);
		button_signOut.setBorderPainted(false);
		button_signOut.setBackground(new Color(44, 64, 84));
		button_signOut.setBounds(835, 11, 49, 45);
		chatPanel.add(button_signOut);

		JPanel panel_busca = new JPanel();
		panel_busca.setBorder(null);
		panel_busca.setBackground(new Color(0, 153, 156));
		panel_busca.setBounds(0, 162, 300, 45);
		chatPanel.add(panel_busca);
		panel_busca.setLayout(null);

		btnNovoChat = new JButton("Novo Chat");
		btnNovoChat.setBounds(151, 0, 139, 45);
		panel_busca.add(btnNovoChat);
		btnNovoChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (contList.getSelectedValue() != null) {
					control.registerChatPersonal(contList.getSelectedValue().toString());
				} else {
					JOptionPane.showMessageDialog(null, "Selecione algum usuário para iniciar um novo Chat!");
					btnBuscar.setVisible(false);
					btnVoltar.setVisible(true);
					btnNovoChat.setVisible(true);
					control.listUserRep();

				}
				contList.setModel(new DefaultListModel<String>());
				SA = true;
			}
		});
		btnNovoChat.setIcon(new ImageIcon("Icons\\speech-bubble.png"));
		btnNovoChat.setHorizontalAlignment(SwingConstants.LEFT);
		btnNovoChat.setForeground(Color.WHITE);
		btnNovoChat.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNovoChat.setFocusPainted(false);
		btnNovoChat.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 153, 156)));
		btnNovoChat.setBackground(new Color(0, 153, 153));
		btnNovoChat.setVisible(false);

		btnNovoGrupo = new JButton("Novo Grupo");
		btnNovoGrupo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_nomeGrup.setVisible(true);
			}
		});
		btnNovoGrupo.setBounds(151, 2, 139, 40);
		panel_busca.add(btnNovoGrupo);
		btnNovoGrupo.setHorizontalAlignment(SwingConstants.LEFT);
		btnNovoGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNovoGrupo.setIcon(new ImageIcon("Icons\\group.png"));
		btnNovoGrupo.setForeground(Color.WHITE);
		btnNovoGrupo.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNovoGrupo.setFocusPainted(false);
		btnNovoGrupo.setBorder(null);
		btnNovoGrupo.setBackground(new Color(0, 153, 153));

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(0, 0, 139, 45);
		panel_busca.add(btnBuscar);
		btnBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SA = false;
				panel_chat.setVisible(false);
				btnBuscar.setVisible(false);
				btnNovoChat.setVisible(true);
				btnVoltar.setVisible(true);
				btnNovoGrupo.setVisible(false);
				control.listUserRep();
				lbl_c.setText("Contatos");

			}
		});
		btnBuscar.setHorizontalAlignment(SwingConstants.LEFT);
		btnBuscar.setIcon(new ImageIcon("Icons\\search (1).png"));
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnBuscar.setFocusPainted(false);
		btnBuscar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 153, 156)));
		btnBuscar.setBackground(new Color(0, 153, 153));

		btnVoltar = new JButton("Voltar");
		btnVoltar.setHorizontalAlignment(SwingConstants.LEFT);
		btnVoltar.setForeground(Color.WHITE);
		btnVoltar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnVoltar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnNovoChat.setVisible(false);
				btnNovoGrupo.setVisible(false);
				btnBuscar.setVisible(true);
				btnVoltar.setVisible(false);
				lbl_c.setText("Conversas");
				contList.setModel(new DefaultListModel<String>());
				SA = true;
			}
		});
		btnVoltar.setIcon(new ImageIcon("Icons\\back-arrow.png"));
		btnVoltar.setBounds(10, 0, 131, 45);
		panel_busca.add(btnVoltar);
		btnVoltar.setFocusPainted(false);
		btnVoltar.setContentAreaFilled(false);
		btnVoltar.setBorderPainted(false);
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setVisible(false);
		btnNovoGrupo.setVisible(false);
		var.setValue(var.getMaximum());

	}

	/**
	 * 
	 * @param email
	 * @return true ou false retorna um boolean para um email em um formarto
	 *         correto
	 */

	private final static boolean valid(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	/**
	 * 
	 * @param string
	 * @param string2
	 * @return true or false para senhas,usada no cadastro de clientes para verificação de senhas iguais. 
	 */

	private final static boolean validPass(String string, String string2) {
		if (string.equals(string2))
			return true;
		else
			return false;
	}
	/**
	 * 
	 * @param a
	 * @return true or false dependendo do range correto de para a porta
	 */

	private final static boolean validPort(int a) {
		if (a < 65536 && a >= 0)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param text
	 * @return true ou false Faz a validação para nos, sobrenomes e nick, aqui,
	 *         resolvemos não aceitar caracteres especiais. No caso do nick
	 *         ainda permitimos numeros.
	 */
	public boolean validNick(String text) {
		return text.matches("[a-zA-Z0-9]+");
	}

	public boolean validName(String text) {
		return text.matches("[a-zA-Z]+");
	}

	/**
	 * 
	 * @param d
	 *            dia que será verificado
	 * @param m
	 *            mês
	 * @param y
	 *            ano
	 * @return true ou false Verificação simples de data. Leva em consideração
	 *         anos bissextos, e meses com 31 ou 30 dias.
	 */
	public boolean validDate(int d, int m, int y) {
		int[] limMes = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		boolean erro = false;

		if (m < 1 || m > 12) {
			erro = true;
		} else if (d < 1 || d > limMes[m - 1]) {
			erro = true;
		} else if (y < 1) {
			erro = true;
		} else if (!((y % 4 == 0 && y % 100 != 0) || y % 400 == 0)) {
			if (d == 29 && m == 2) {
				erro = true;
			}
		}
		return erro;
	}

	public void singInOK() {
		cleanSigIn();
		unlockSingIn();
		logInPanel.setVisible(true);
		singInPanel.setVisible(false);
	}

	public void singInExc1() {
		valid_nick.setText("*O nick já está em uso!");
		unlockSingIn();
		nickField.requestFocus();
	}

	/**
	 * Faz toda a limpeza da Tela de cadastro (campo e mensagens) deixando o
	 * Panel limpo para uso posterior
	 */
	public void cleanSigIn() {

		nameField.setText(null);
		lastNameField.setText(null);
		emailField.setText(null);
		nickField.setText(null);
		passwordField_1.setText(null);
		passwordField_2.setText(null);
		genderComboBox.setSelectedIndex(0);
		dayComboBox.setSelectedIndex(0);
		yearComboBox.setSelectedIndex(0);
		monthComboBox.setSelectedIndex(0);
		dayComboBox.setEditable(false);
		yearComboBox.setEditable(false);
		monthComboBox.setEditable(false);
		genderComboBox.setEditable(false);

		valid_name.setText(null);
		valid_sobre.setText(null);
		validMaillabel.setText(null);
		valid_nick.setText(null);
		validSenhalabel.setText(null);
		erroData_label.setText(null);

	}

	public void lockSingIn() {

		nameField.setEditable(false);
		lastNameField.setEditable(false);
		emailField.setEditable(false);
		nickField.setEditable(false);
		passwordField_1.setEditable(false);
		passwordField_2.setEditable(false);
		genderComboBox.setEditable(false);
		dayComboBox.setEditable(false);
		yearComboBox.setEditable(false);
		monthComboBox.setEditable(false);
	}

	public void unlockSingIn() {
		nameField.setEditable(true);
		lastNameField.setEditable(true);
		emailField.setEditable(true);
		nickField.setEditable(true);
		passwordField_1.setEditable(true);
		passwordField_2.setEditable(true);
		genderComboBox.setEditable(true);
		dayComboBox.setEditable(true);
		yearComboBox.setEditable(true);
		monthComboBox.setEditable(true);
	}

	public void lockLogIn() {
		userField.setEditable(false);
		passwordField.setEditable(false);
	}

	public void unlockLogIn() {
		userField.setEditable(true);
		passwordField.setEditable(true);
	}

	public void logInOK(User user) {
		this.user = user;
		lockLogIn();
		clearLogIn();
		nickErro.setText(null);
		passwordErro.setText(null);
		label_infos.setText(user.getName() + " " + user.getLastname() + " (" + user.getNickname() + ")");
		logInPanel.setVisible(false);
		chatPanel.setVisible(true);

		new myThread();
	}

	public void clearLogIn() {
		userField.setText(null);
		passwordField.setText(null);
	}

	public void logInException1() {
		unlockLogIn();
		nickErro.setText("*Nome de usuário inválido");
		passwordErro.setText(null);
		userField.requestFocus();

	}

	public void logInException2() {
		unlockLogIn();
		nickErro.setText(null);

		passwordErro.setText("*Senha inválida");
		passwordField.requestFocus();

	}

	public boolean userOk() {
		if (!validDate(Integer.parseInt(dayComboBox.getSelectedItem().toString()), monthComboBox.getSelectedIndex() + 1,
				Integer.parseInt(yearComboBox.getSelectedItem().toString())) && validName(nameField.getText())
				&& valid(emailField.getText())
				&& validPass(new String(passwordField_1.getPassword()), new String(passwordField_2.getPassword()))
				&& validNick(nickField.getText())) {
			return true;
		} else
			return false;

	}

	public void listUserRepOK(DefaultListModel<String> dlm) {
		dlm.removeElement(this.user.getNickname());
		contList.setModel(dlm);
		SA = false;

	}

	public void registerChatPersonalDone() {
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		Iterator<Chat> it = this.repChat.iterator();
		while (it.hasNext()) {
			dlm.addElement(it.next().getName());
		}
		contList.setModel(dlm);
		btnNovoChat.setVisible(false);
		btnNovoGrupo.setVisible(false);
		btnBuscar.setVisible(true);
		SA = true;

	}

	public void registerChatPersonalException1() {
		JOptionPane.showMessageDialog(null, "O chat já existe!");
	}

	public void registerChatGroupDone() {
		textField.setText(null);
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		Iterator<Chat> it = this.repChat.iterator();
		while (it.hasNext()) {
			dlm.addElement(it.next().getName());
		}
		btnVoltar.setVisible(false);
		btnNovoChat.setVisible(false);
		btnNovoGrupo.setVisible(false);
		panel_nomeGrup.setVisible(false);
		btnBuscar.setVisible(true);
		SA = true;
	}

	public void registerChatGroupException1() {
		textField.setText(null);
		textField.requestFocus();
		erroGroupName.setText("Nome do grupo já existe");
	}

	/**
	 * 
	 * @param substring
	 *            - Menssagem recebeida pelo servidor. O metódo sendMessage é
	 *            chamado pelo controle para repassar a menssagem para o
	 *            cliente.
	 */

	public void sendMessage(String substring) {
		textPane.setText(substring);
		textField.setText(null);
		textField.setEditable(true);
		label_ev.setVisible(true);
		var.setValue(var.getMaximum());
	}

	public void uploadFile(int port) {
		try {
			FileSender sender = new FileSender(directoryUp, ids.get(contList.getSelectedIndex()), serverIP, port);
			(new Thread(sender)).start();
			(new Thread(new ProgressBarUp(progressBar, label_time, lbl_vel, label_rtt, sender, this))).start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block

		}
	}

	public void returnFileSelector() {
		panel_dow.setVisible(false);
		panel_te.setVisible(true);
	}

	public void downloadFile(String id, String filename, long filesize, int port) {
		try {
			this.reciever = new FileReciev(this.directoryDown + "\\" + filename, serverIP, id, filesize, port);
			new Thread(reciever).start();
			(new Thread(new ProgressBarDown(progressBar, label_time, lbl_vel, label_rtt, reciever, this))).start();
			panel_dow.setVisible(true);
			button_play.setVisible(true);
			button_cancel.setVisible(true);
			panel_te.setVisible(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	public void downloadFinish(int port) {
		panel_dow.setVisible(false);
		panel_te.setVisible(true);
		button_play.setVisible(false);
		button_cancel.setVisible(false);
		control.endDownload(ids.get(contList.getSelectedIndex()), choice_arquivo.getSelectedItem(), port);
	}

	/**
	 * Função usada para o envio de mensagens,usada igualmente no botão enviar
	 * ou apertando a tecla ENTER
	 */
	public void sendM() {
		int i = contList.getSelectedIndex();
		if (!textField.getText().trim().isEmpty() && i >= 0) {
			control.sendMsg(ids.get(i), user.getName() + ": " + textField.getText());

			label_ev.setVisible(false);
			label_etr.setVisible(false);
			label_ld.setVisible(false);
			textField.setEditable(false);
			if (textField.getText().trim().isEmpty())
				textField.setEditable(false);
			Chat chat = repChat.search(ids.get(i));
			chat.setSeen(false);
			chat.setSent(false);
			repChat.update(chat);
		}
	}
}
