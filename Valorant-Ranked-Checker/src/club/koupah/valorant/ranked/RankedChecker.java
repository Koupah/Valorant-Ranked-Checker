package club.koupah.valorant.ranked;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import me.tewpingz.valorant.auth.ValAuthentication;
import me.tewpingz.valorant.auth.ValRegion;

public class RankedChecker extends JFrame {

	double version = 0.13;

	private static final long serialVersionUID = -8694445418570903440L;

	/*
	 * 
	 * IF YOU ARE READING THIS, EVERYTHING NEEDS CLEANING UP THIS IS JUST WHAT I
	 * SCRAPPED TOGETHER IN AN HOUR OR SO
	 * 
	 */

	/*
	 * Also everything is static because I'm too lazy to make getters & setters
	 */
	private JPanel contentPane;

	// Authentication Variable
	public static ValAuthentication auth;
	// Most requests are region specific
	public static ValRegion region;

	// Whether or not we're logged in
	public static boolean loggedIn = false;

	public static void main(String[] args) {
		RankedChecker frame = new RankedChecker();
		frame.setVisible(true);
	}

	public static JPanel rankPanel; // the panel the ranks are shown on

	public RankedChecker() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		setTitle("Koupah's Ranked Checker v" + version);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 50, 680, 460);
		contentPane.add(scrollPane);

		rankPanel = new JPanel();
		rankPanel.setBounds(10, 50, 660, 460);
		rankPanel.setLayout(null);

		final JButton loginButton = new JButton("Valorant Login");

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final String button = loginButton.getText();
				if (button.startsWith("Logging In"))
					return;

				loginButton.setEnabled(false);
				loginButton.setText("Logging In");

				Utility.login();

				loginButton.setText(button);
				loginButton.setEnabled(true);
			}
		});

		loginButton.setBounds(10, 5, 150, 40);
		contentPane.add(loginButton);

		final JButton fetchButton = new JButton("Fetch Matches");

		fetchButton.setBounds(525, 5, 150, 40);
		contentPane.add(fetchButton);

		final JSpinner matchCount = new JSpinner();
		matchCount.setModel(new SpinnerNumberModel(40, 20, 200, 20));
		matchCount.setBounds(460, 15, 50, 20);
		((DefaultEditor) matchCount.getEditor()).getTextField().setEditable(false);
		contentPane.add(matchCount);

		JLabel matchText = new JLabel("Matches to fetch: ");
		matchText.setHorizontalAlignment(SwingConstants.RIGHT);
		matchText.setBounds(320, 5, 130, 40);
		contentPane.add(matchText);

		scrollPane.setPreferredSize(rankPanel.getPreferredSize());
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(rankPanel);

		fetchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final String button = fetchButton.getText();
				if (button.startsWith("Fetching"))
					return;

				fetchButton.setEnabled(false);
				fetchButton.setText("Fetching...");

				new Thread() {
					public void run() {

						Utility.getRankedGames(auth, region, (int) matchCount.getValue());
						fetchButton.setText(button);
						fetchButton.setEnabled(true);
					}
				}.start();

			}
		});
	}

}
