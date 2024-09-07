import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class communication {
	
	public static int CRC_Reciver(int myData[][], int countNumber,int puttern[] ,int index ) {
		 int endArray=0;
		 int i,j=0;
		 // difine an array which contain data && number of buttern bit -1 fill with zero
		 int checkData[]=new int[myData[index].length];
		
		 
		for(int k=0;k<myData[index].length;k++) {
			checkData[k]=myData[index ][k];
		
		}
	
		  
		  int storeRemenderArray[] = new int[puttern.length];
			
	      for(i=0;i<checkData.length;i++) {
		         
		  if(checkData[i] == 1) {
		            
	     for(j=0;j<puttern.length;j++) {
	      if(i+j>checkData.length-1) {
		     endArray=1;
		       break;
	  }
	    else   storeRemenderArray[j] = checkData[i+j];
	}
		             //flag = 0 means divison is possible...
	     if(endArray==0) 
	     for(j=0;j<puttern.length;j++){
		 if(storeRemenderArray[j] == puttern[j])  checkData[i+j] = 0;
		                      
	    else    checkData[i+j] = 1;
	     }
		               
	  }
 }
		  
		  for(i=0;i<checkData.length;i++)
	       {
			  if(checkData[i]==1) {
				  countNumber=countNumber+1;
				  break;
			  }
			 
			//  System.out.print(checkData[i]+" ");
	       }
	//	 System.out.println();
		  return countNumber;
	}	 

	public static void CRC(String myData[][], int reciveArray[][],int puttern[] ,int index ) {
		 int endArray=0;
		 int i,j=0;
		 // difine an array which contain data && number of buttern bit -1 fill with zero
		 int sendData[]=new int[puttern.length+myData[index].length-1];
		 reciveArray[index]=new int[puttern.length+myData[index].length-1];
		 
		for(int k=0;k<myData[index].length;k++) {
			sendData[k]=Integer.parseInt(myData[index ][k]);
			reciveArray[index][k]=Integer.parseInt(myData[index ][k]);
			
			
		}
	
		  for( int c=myData[index].length;c<sendData.length;c++) {
			   sendData[c] = 0;
		     }
		  
		  int storeRemenderArray[] = new int[puttern.length];
			
	      for(i=0;i<sendData.length;i++) {
		         
		  if(sendData[i] == 1) {
		            
	     for(j=0;j<puttern.length;j++) {
	      if(i+j>sendData.length-1) {
		     endArray=1;
		       break;
	  }
	    else   storeRemenderArray[j] = sendData[i+j];
		             }

		             //flag = 0 means divison is possible...
	     if(endArray==0) 
	     for(j=0;j<puttern.length;j++){
		 if(storeRemenderArray[j] == puttern[j])  sendData[i+j] = 0;
		                      
	    else    sendData[i+j] = 1;
	     }
		               
	  }
 }
		  
		  for(i=myData[index].length;i<reciveArray[index].length;i++)
	       {
			  reciveArray[index][i] = sendData[i];
	       }
		  
		 
	}
	
	

public static void main(String[] args) throws FileNotFoundException {
		
		//inalize array
		ArrayList<String>data=new ArrayList<String>();
		String d[][];
		int myData[];
		int reciveArray[][]=new int [1024][];
		ArrayList<Integer>randomNumberError=new ArrayList<Integer>();
		int puttern_12[]= {1,1,0,0,0,0,0,0,0,1,1,1,1};
	//	int puttern_16[]= {1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,1};
		int puttern_16[]= {1,0,1,0,0,0,1,1,0,1};
		int index=0;
		
		//Create File To Write Data That will Be Send
		 try {
		    
		     FileWriter myWriter = new FileWriter("CRC.txt");    
		
		     for(int u=0;u<1024;u++) {
		 		
				 int y[]=new int[10];
				  String d1[];
				  String  c=Integer.toBinaryString(u);
			      int  writeToFile[]=new int[10];
				  d1=c.split("");
				  int size=d1.length;
				 
				
				  
				   for(int j=9;j>=size;j--) y[j]=0;
					
					
				   for(int j=0;j<size;j++)  y[j] =Integer.parseInt(d1[size-j-1]);
					 
				  
				   
				   for(int j=0;j<10;j++)   writeToFile[9-j]= y[j];
					   
				 
				  for(int p=0;p<10;p++){
					  String str=String.valueOf(writeToFile[p]); 
				       myWriter.write(str);
				       myWriter.write(" ");
		     }
				  
				  myWriter.write(" \n");
		 }	   
		     
		      myWriter.close();
	  } 
		    catch (IOException e) {
			 JOptionPane.showMessageDialog(null, "An error occurred.");
		      e.printStackTrace();
		    }
		 
		
		 //Read From File and store in array list
		 
		 
		 File communicationFile = new File("CRC.txt");
	      if(communicationFile.exists()) {
	      Scanner input = new Scanner(communicationFile);
	      while (input.hasNextLine()) {
	    	  data.add(input.nextLine());
	 
	      }
	  
	      input.close();
	     
	    } 
	      else   
	    	  JOptionPane.showMessageDialog(null, "An error occurred.");
	    
        d=new String [data.size()][];
      
        //convert array list content tostring array
        for (int i=0;i<data.size();i++) {
         d[i]=data.get(i).split(" ");
        }
         for(int j=0;j<data.size();j++) {
        	CRC( d, reciveArray,puttern_12,index );
        	index++;
        }
         
         int random=0;
         for(int v=0;v<512;v++) {
        	 random=(int)((Math.random()*5)+1); 
       	    randomNumberError.add(random); 
       	 //   System.out.print(randomNumberError.get(v)+"*");
         }
         try {
        	 int rr=-1;
 		 
		     FileWriter myWriter1 = new FileWriter("CRC_12.txt");    
		     for (int i=0;i<1024;i++) {
		    	 
		    	 if(i%2==0) {
		    		rr++;
		    		 for(int h=1;h<randomNumberError.get(rr) ;h++) {
				     if(reciveArray[i][2*h]==1) reciveArray[i][2*h]=0;
				     else reciveArray[i][2*h]=1;
		    		 }	
		    	
		    	 }
		    	  for (int l=0;l<reciveArray[i].length;l++) { 
		        
		    	//make random error in even line and store it in file
		       //  int random=(int)((Math.random()*5)+1);  
		  
		     myWriter1.write(String.valueOf(reciveArray[i][l]));	 
		     myWriter1.write(" ");
		     
		      }  
		     
		        myWriter1.write(" \n");
		     }
	
		      myWriter1.close();
}
		    catch (IOException e) {
			 JOptionPane.showMessageDialog(null, "An error occurred.");
		      e.printStackTrace();
		    }
		//////////////////////////////// 
		index=0;
		double countt_12=0;
		int countcrc_12=0;
		int countcrc_16=0;
		
		 for(int j=0;j<reciveArray.length;j++) {
			 countt_12+=	 CRC_Reciver( reciveArray, countcrc_12, puttern_12 ,index );
	        	index++;
	        	
	        }
	        
		 System.out.print(countt_12+" ");
 
         //**********************CRC_16*************************
           index=0;
         for(int j=0;j<data.size();j++) {
         	CRC( d, reciveArray,puttern_16,index );
         	index++;
         }
         
          try {
  		    int o=-1;
        	  System.out.print(randomNumberError.size()+"********");
 		     FileWriter myWriter1 = new FileWriter("CRC_16.txt");    
 		     for (int i=0;i<reciveArray.length;i++) {
 		    	 

		    	 if(i%2==0) {
		    		o++;
		    		 for(int h=1;h<randomNumberError.get(o) ;h++) {
				     if(reciveArray[i][2*h]==1) reciveArray[i][2*h]=0;
				     else reciveArray[i][2*h]=1;
		    		 }	
		    	
		    	 }
 		    	  for (int l=0;l<reciveArray[i].length;l++) { 
 		        
 		    
 		    
 		    			  
 		     myWriter1.write(String.valueOf(reciveArray[i][l]));	 
 		     myWriter1.write(" ");
 		     
 		      }  
 		     
 		        myWriter1.write(" \n");
 		     }
 	
 		      myWriter1.close();
 }
 		    catch (IOException e) {
 			 JOptionPane.showMessageDialog(null, "An error occurred.");
 		      e.printStackTrace();
 		    }
          index=0;
  		  double countt_16=0;
  	/*	 for (int i=0;i<reciveArray.length;i++) {
	    	  for (int l=0;l<reciveArray[i].length;l++) { 
	        System.out.print(reciveArray[i][l]);
	    	  } System.out.println();}
  		
  		*/
  		 for(int j=0;j<reciveArray.length;j++) {
  			 countt_16+= CRC_Reciver( reciveArray, countcrc_16, puttern_16 ,index );
  	        	index++;
  	        	
  	        }
  		System.out.print(countt_16+" ");
  		 
  		 double percentError_12=((double)countt_12/512.0*100.0);
  	//	System.out.print(percentError_12+" ");
  		double percentError_16=(double)(countt_16/512)*100;
  		if(percentError_12>percentError_16) {
  			JOptionPane.showMessageDialog(null, "The Percent Error for CCITT= %"+percentError_12+"\n"+"The Percent Error for CRC_16= %"+percentError_16+"\n"+"So CCITT Is The Better");
  		}
  		if(percentError_12<percentError_16)
  			JOptionPane.showMessageDialog(null, "The Percent Error for CCITT= %"+percentError_12+"\n"+"The Percent Error for CRC_16= %"+percentError_16+"\n"+"So CRC_16 Is The Better");
  		if(percentError_12==percentError_16)
  			JOptionPane.showMessageDialog(null, "The Percent Error for CCITT= %"+percentError_12+"\n"+"The Percent Error for CRC_16= %"+percentError_16+"\n"+"The tow are The same");
  }
}
