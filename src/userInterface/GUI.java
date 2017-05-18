package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.Server;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField portField;
	private TextArea logField;

	Server server;
	private JButton btnStartServer;
	private static GUI instance;

	/**
	 * Create the frame.
	 */
	public GUI() {
		setResizable(false);
		server = new Server();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel controlPanel = new JPanel();
		contentPane.add(controlPanel, BorderLayout.EAST);
		GridBagLayout gbl_controlPanel = new GridBagLayout();
		gbl_controlPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_controlPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_controlPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_controlPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		controlPanel.setLayout(gbl_controlPanel);

		JLabel lblPort = new JLabel("Port:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 0;
		controlPanel.add(lblPort, gbc_lblPort);

		portField = new JTextField();
		GridBagConstraints gbc_portField = new GridBagConstraints();
		gbc_portField.insets = new Insets(0, 0, 5, 0);
		gbc_portField.fill = GridBagConstraints.HORIZONTAL;
		gbc_portField.gridx = 1;
		gbc_portField.gridy = 0;
		controlPanel.add(portField, gbc_portField);
		portField.setColumns(10);

		btnStartServer = new JButton("Server starten");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					server.setPort(Integer.parseInt(portField.getText()));
					server.start();
					btnStartServer.setEnabled(false);
				} catch (NumberFormatException e1) {
					portField.setBackground(Color.RED);
				}
			}
		});
		GridBagConstraints gbc_btnStartServer = new GridBagConstraints();
		gbc_btnStartServer.gridwidth = 2;
		gbc_btnStartServer.insets = new Insets(0, 0, 0, 5);
		gbc_btnStartServer.gridx = 0;
		gbc_btnStartServer.gridy = 1;
		controlPanel.add(btnStartServer, gbc_btnStartServer);
		getRootPane().setDefaultButton(btnStartServer);

		JPanel messagePanel = new JPanel();
		contentPane.add(messagePanel, BorderLayout.CENTER);

		logField = new TextArea(10, 50);
		logField.setEditable(false);
		messagePanel.add(logField);
	}

	public static GUI getInstance() {
		if (instance == null) {
			instance = new GUI();
		}
		return instance;
	}

	public void write(String s) {
		logField.append(s + "\n");
	}
}
