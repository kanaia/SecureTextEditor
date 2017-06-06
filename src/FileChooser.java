import java.io.*;
import javax.swing.*;

public class FileChooser {
	
	public JFileChooser chooser = new JFileChooser();
	
	public void save(String data){
		int returnValue = chooser.showSaveDialog(null);
		
		if(returnValue == JFileChooser.APPROVE_OPTION){
			try{
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(chooser.getSelectedFile()), "utf-8"));
				writer.write(data);
				writer.close();
			} catch (IOException e){
				System.out.println("IO Exception, Fehler beim Speichern");
				e.printStackTrace();
			}
		}
	}
	
	public String open(){
		int returnValue = chooser.showOpenDialog(null);
		String data = "";
		String lineFeed = System.getProperty("line.separator");
		
		if(returnValue == JFileChooser.APPROVE_OPTION){
			try{
				BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));
				data = reader.readLine();
				while(reader.read() != -1){
					data = data + lineFeed + reader.readLine();
				}
				reader.close();
			} catch (IOException e){
				System.out.println("IO Exception, Fehler beim Laden");
				e.printStackTrace();
			}
		}
		
		return data;
	}

}
