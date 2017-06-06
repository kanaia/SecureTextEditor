import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;

public class Editor {

	JTextArea textArea = new JTextArea();
	JFrame ste = new JFrame("Secure Text Editor");
	JDialog dialog;
	Cryptography crypto = new Cryptography();
	String encrypted = "";
	String decrypted = "";

	public void initFrame() {
		ste.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ste.setResizable(true);
		ste.setVisible(true);
		ste.setLayout(null);
		ste.setSize(600, 400);

		ste.add(textArea);
		textArea.setLocation(0, 30);
		textArea.setSize(600, 370);

		addButtons();

	}

	public void addButtons() {
		JButton open = new JButton("Open");
		open.addActionListener(new OpenL());
		ste.add(open);
		open.setLocation(0, 0);
		open.setSize(70, 30);

		JButton save = new JButton("Save");
		save.addActionListener(new SaveL());
		ste.add(save);
		save.setLocation(70, 0);
		save.setSize(70, 30);
	}

	class OpenL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dialog = new JDialog();
			dialog.setTitle("Encryption");
			dialog.setSize(400, 270);
			dialog.setModal(true);
			dialog.setResizable(false);
			dialog.setLayout(new GridLayout(4, 1));
			// decrypt
			JPanel decryptPanel = new JPanel();
			JLabel decrypt = new JLabel("Decrypt with:");
			decryptPanel.add(decrypt);
			JRadioButton aes = new JRadioButton("AES");
			JRadioButton des = new JRadioButton("DES");
			ButtonGroup dec = new ButtonGroup();
			dec.add(aes);
			dec.add(des);
			decryptPanel.add(aes);
			decryptPanel.add(des);

			JPanel cipherModePanel = new JPanel();
			JLabel ciphermode = new JLabel("Cipher Mode:");
			cipherModePanel.add(ciphermode);
			JRadioButton ecb = new JRadioButton("ECB");
			JRadioButton cbc = new JRadioButton("CBC");
			JRadioButton cts = new JRadioButton("CTS");
			ButtonGroup cm = new ButtonGroup();
			cm.add(ecb);
			cm.add(cbc);
			cm.add(cts);
			cipherModePanel.add(ecb);
			cipherModePanel.add(cbc);
			cipherModePanel.add(cts);

			JPanel paddingPanel = new JPanel();
			JLabel padding = new JLabel("Padding");
			paddingPanel.add(padding);
			JRadioButton pkcs7 = new JRadioButton("PKCS7Padding");
			JRadioButton noP = new JRadioButton("NoPadding");
			JRadioButton zbP = new JRadioButton("ZeroBytePadding");
			ButtonGroup pad = new ButtonGroup();
			pad.add(pkcs7);
			pad.add(noP);
			pad.add(zbP);
			paddingPanel.add(pkcs7);
			paddingPanel.add(noP);
			paddingPanel.add(zbP);

			dialog.add(decryptPanel, 0);
			dialog.add(cipherModePanel, 1);
			dialog.add(paddingPanel, 2);

			JButton go = new JButton("Go!");
			dialog.add(go);

			class DecL implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					String encryption = "";
					String cipherMode = "";
					String padding = "";
					if (aes.isSelected())
						encryption = aes.getText();
					if (des.isSelected())
						encryption = des.getText();

					if (ecb.isSelected())
						cipherMode = ecb.getText();
					if (cbc.isSelected())
						cipherMode = cbc.getText();
					if (cts.isSelected())
						cipherMode = cts.getText();

					if (pkcs7.isSelected())
						padding = pkcs7.getText();
					if (noP.isSelected())
						padding = noP.getText();
					if (zbP.isSelected())
						padding = zbP.getText();

					if (dec.getSelection() == null && cm.getSelection() == null && pad.getSelection() == null) {
						FileChooser f = new FileChooser();
						textArea.setText(f.open());
					} else {
						Cryptography c = new Cryptography();
						try {
							if(encryption.equals("AES")){
								FileChooser f = new FileChooser();
								textArea.setText(c.decryptAES(f.open(), cipherMode, padding));
							}
							else if(encryption.equals("DES")){
								FileChooser f = new FileChooser();
								textArea.setText(c.decryptDES(f.open(), cipherMode, padding));
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

					dialog.dispose();
				}
			}

			go.addActionListener(new DecL());

			dialog.setVisible(true);

		}
	}

	class SaveL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dialog = new JDialog();
			dialog.setTitle("Encryption");
			dialog.setSize(400, 270);
			dialog.setModal(true);
			dialog.setResizable(false);
			dialog.setLayout(new GridLayout(4, 1));
			// encrypt
			JPanel encryptPanel = new JPanel();
			JLabel encrypt = new JLabel("Encrypt with:");
			encryptPanel.add(encrypt);
			JRadioButton aes = new JRadioButton("AES");
			JRadioButton des = new JRadioButton("DES");
			ButtonGroup enc = new ButtonGroup();
			enc.add(aes);
			enc.add(des);
			encryptPanel.add(aes);
			encryptPanel.add(des);

			JPanel cipherModePanel = new JPanel();
			JLabel ciphermode = new JLabel("Cipher Mode:");
			cipherModePanel.add(ciphermode);
			JRadioButton ecb = new JRadioButton("ECB");
			JRadioButton cbc = new JRadioButton("CBC");
			JRadioButton cts = new JRadioButton("CTS");
			ButtonGroup cm = new ButtonGroup();
			cm.add(ecb);
			cm.add(cbc);
			cm.add(cts);
			cipherModePanel.add(ecb);
			cipherModePanel.add(cbc);
			cipherModePanel.add(cts);

			JPanel paddingPanel = new JPanel();
			JLabel padding = new JLabel("Padding");
			paddingPanel.add(padding);
			JRadioButton pkcs7 = new JRadioButton("PKCS7Padding");
			JRadioButton noP = new JRadioButton("NoPadding");
			JRadioButton zbP = new JRadioButton("ZeroBytePadding");
			ButtonGroup pad = new ButtonGroup();
			pad.add(pkcs7);
			pad.add(noP);
			pad.add(zbP);
			paddingPanel.add(pkcs7);
			paddingPanel.add(noP);
			paddingPanel.add(zbP);

			dialog.add(encryptPanel, 0);
			dialog.add(cipherModePanel, 1);
			dialog.add(paddingPanel, 2);

			JButton go = new JButton("Go!");
			dialog.add(go);

			class EncL implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					String encryption = "";
					String cipherMode = "";
					String padding = "";
					if (aes.isSelected())
						encryption = aes.getText();
					if (des.isSelected())
						encryption = des.getText();

					if (ecb.isSelected())
						cipherMode = ecb.getText();
					if (cbc.isSelected())
						cipherMode = cbc.getText();
					if (cts.isSelected())
						cipherMode = cts.getText();

					if (pkcs7.isSelected())
						padding = pkcs7.getText();
					if (noP.isSelected())
						padding = noP.getText();
					if (zbP.isSelected())
						padding = zbP.getText();

					if (enc.getSelection() == null && cm.getSelection() == null && pad.getSelection() == null) {
						encrypted = textArea.getText();
					} else {
						Cryptography c = new Cryptography();
						try {
							if(encryption.equals("AES"))
								encrypted = c.encryptAES(textArea.getText(), cipherMode, padding);
							else if(encryption.equals("DES"))
								encrypted = c.encryptDES(textArea.getText(), cipherMode, padding);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

					FileChooser f = new FileChooser();
					f.save(encrypted);
					dialog.dispose();
				}
			}

			go.addActionListener(new EncL());

			dialog.setVisible(true);

		}
	}

}
