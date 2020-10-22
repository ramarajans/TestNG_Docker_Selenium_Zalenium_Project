package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.testng.SkipException;

public class GenUtils {
	
	 public static void setUpDocker(String remoteMode) throws IOException, Exception {

		 Runtime runtime=Runtime.getRuntime();

		 if("Selenium".equalsIgnoreCase(remoteMode)) {

			 runtime.exec("cmd /c start dockerUp.bat");

			 Thread.sleep(10000);
			 runtime.exec("taskkill /f /im cmd.exe") ;
		 }
		 else {
			 runtime.exec("cmd /c start zaleniumUp.bat");
			 verifyDockerIsUp();
			 Thread.sleep(10000);
		 }

	 }

	 public static void verifyDockerIsUp() throws FileNotFoundException, Exception {
		 Thread.sleep(10000);
		 boolean flag=false;
		 String file="output.txt";
		 BufferedReader reader= new BufferedReader(new FileReader(file));
		 String currentline=reader.readLine();

		 while(currentline!=null) {
			 if((currentline.contains("The node is registered to the hub and ready to use"))||(currentline.contains("Zalenium is now ready!"))) {
				 flag=true;
				 break;
			 }
			 currentline=reader.readLine();
		 }
		 reader.close();

		 if(!flag) {
			 throw new SkipException("Docker have not started. Please try again or try manually.");
		 }
	 }

	 
	 public static void downDocker(String remoteMode) throws IOException, InterruptedException {

			Runtime	runtime=Runtime.getRuntime();

			if("Selenium".equalsIgnoreCase(remoteMode)) {

				runtime.exec("cmd /c start dockerDown.bat"); 

			} else {
				
				runtime.exec("cmd /c start zaleniumDown.bat"); 
				
			} 
			
			File file=new File("output.txt");
			if(file.exists()) {
				System.out.println("file deleted");
				file.delete();
				} 
			
			Thread.sleep(20000); 
			
			runtime.exec("taskkill /f /im cmd.exe");

	 }
}
