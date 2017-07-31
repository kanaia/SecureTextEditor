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
			dialog.setLayout(new GridLayout(1, 5));
			// decrypt

			String[] encModeList = {"", "AES", "DES", "PBEWithSHAAnd128BitAES-CBC-BC", "PBEWithMD5AndDES", "PBEWithSHAAnd40BitRC4"};
			JComboBox encMode = new JComboBox(encModeList);
			
			String[] cipherModeList = {"", "ECB", "CBC", "CTS", "CTR", "OFB", "CFB", "GCM"};
			JComboBox cipherMode = new JComboBox(cipherModeList);
			
			String[] paddingList = {"", "PKCS7Padding", "NoPadding", "ZeroBytePadding"};
			JComboBox padding = new JComboBox(paddingList);
			
			JTextField pw = new JTextField("Passwort");
			dialog.add(encMode, 0);
			dialog.add(cipherMode, 1);
			dialog.add(padding, 2);
			dialog.add(pw);

			JButton go = new JButton("Go!");
			dialog.add(go);

			class DecL implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					String encryption = encMode.getSelectedItem().toString();
					String cipherM = cipherMode.getSelectedItem().toString();
					String pad = padding.getSelectedItem().toString();

					if (encMode.getSelectedItem() == null && cipherMode.getSelectedItem() == null && padding.getSelectedItem() == null) {
						FileChooser f = new FileChooser();
						textArea.setText(f.open());
					} else {
						try {
							if(encryption.equals("AES")){
								Cryptography c = new Cryptography();
								FileChooser f = new FileChooser();
								textArea.setText(c.decryptAES(f.open(), cipherM, pad));
							}
							else if(encryption.equals("DES")){
								Cryptography c = new Cryptography();
								FileChooser f = new FileChooser();
								textArea.setText(c.decryptDES(f.open(), cipherM, pad));
							}
							else if(encryption.equals("PBEWithSHAAnd128BitAES-CBC-BC")){
								PBCryptography c = new PBCryptography();
								FileChooser f = new FileChooser();
								textArea.setText(c.decodePBEWithSHAAnd128BitAES_CBC_BC(f.open(), pw.getText().toCharArray()));
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
			dialog.setSize(600, 50);
			dialog.setModal(true);
			dialog.setResizable(false);
			dialog.setLayout(new GridLayout(1, 5));
			// encrypt
			JPanel encryptPanel = new JPanel();
			JLabel encrypt = new JLabel("Encrypt with:");
			encryptPanel.add(encrypt);
			
			String[] encModeList = {"", "AES", "DES", "PBEWithSHAAnd128BitAES-CBC-BC", "PBEWithMD5AndDES", "PBEWithSHAAnd40BitRC4"};
			JComboBox encMode = new JComboBox(encModeList);
			
			String[] cipherModeList = {"", "ECB", "CBC", "CTS", "CTR", "OFB", "CFB", "GCM"};
			JComboBox cipherMode = new JComboBox(cipherModeList);
			
			String[] paddingList = {"", "PKCS7Padding", "NoPadding", "ZeroBytePadding"};
			JComboBox padding = new JComboBox(paddingList);
			
			JTextField pw = new JTextField("Passwort");
			dialog.add(encMode, 0);
			dialog.add(cipherMode, 1);
			dialog.add(padding, 2);
			dialog.add(pw);
			
			JButton go = new JButton("Go!");
			dialog.add(go);

			class EncL implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					String encryption = encMode.getSelectedItem().toString();
					String cipherM = cipherMode.getSelectedItem().toString();
					String pad = padding.getSelectedItem().toString();
					
					if (encMode.getSelectedItem() == null && cipherMode.getSelectedItem() == null && padding.getSelectedItem() == null) {
						encrypted = textArea.getText();
					} else {
						try {
							if(encryption.equals("AES")){
								Cryptography c = new Cryptography();
								encrypted = c.encryptAES(textArea.getText(), cipherM, pad);
							}else if(encryption.equals("DES")){
								Cryptography c = new Cryptography();
								encrypted = c.encryptDES(textArea.getText(), cipherM, pad);
							}else if(encryption.equals("PBEWithSHAAnd128BitAES-CBC-BC")){
								PBCryptography c = new PBCryptography();
								encrypted = c.encodePBEWithSHAAnd128BitAES_CBC_BC(textArea.getText(), pw.getText().toCharArray());
							}
								
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
