package VSM;
import java.util.*;
import java.io.*;
//import sun.rmi.runtime.Log;
import java.lang.Math;

//import sun.rmi.runtime.Log;


public class tfidf
{
	public List<String> fileList = new ArrayList<String>();
   	public HashMap<String, HashMap<String, Float>> allTheTf = new HashMap<String, HashMap<String, Float>>();
	public static Map<String,Integer> termMap=new HashMap<String,Integer>();
	//public Map<String,Integer> dfMap=new HashMap();
	public Map<String, Double> idf = new HashMap<String, Double>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			String contents = ".\\text\\terms.txt";
			FileInputStream fis2 = new FileInputStream(contents);
			InputStreamReader isr2 = new InputStreamReader(fis2);
			BufferedReader br2 = new BufferedReader(isr2);
			String filestr = "";
			filestr = br2.readLine();
			
			tfidf hh = new tfidf();
			//Map<String, HashMap<String, Float>> hello = hh.result(".\\text\\texts");
			HashMap<String, Float> hello = hh.tf(filestr);
			System.out.print(hello);
			
		} catch (Exception ex){
			
		}
		

	}
	public static void getVsm()
	{
		try
		{
			File file = new File (".\\text\\terms.txt");  
			String row="";  

			FileInputStream in=new FileInputStream(file);    
   	
			InputStreamReader inReader=new InputStreamReader(in);    
   
			BufferedReader bufReader=new BufferedReader(inReader);  
			while((row = bufReader.readLine())!=null)
			{
				String[] line=row.split(" ");
				termMap.put(line[0],0);
			}
			
			bufReader.close();
			inReader.close();
			in.close();
		}
		catch(Exception e){e.printStackTrace();}
		System.out.println(termMap.size());
	}
	public List<String> readDirs(String filepath) throws FileNotFoundException, IOException 
	{
        try
		{
           			File file = new File(filepath);
            		if (!file.isDirectory()) 
            		{
                		System.out.println("输入的参数应该为[文件夹名]");
                		System.out.println("filepath: " + file.getAbsolutePath());
            		} 
            		else if (file.isDirectory()) 
            		{
                			String[] filelist = file.list();
                			for (int i = 0; i < filelist.length; i++) 
                			{
                    				File readfile = new File(filepath + "\\" + filelist[i]);
                    				if (!readfile.isDirectory()) 
                    				{
                        			//System.out.println("filepath: " + readfile.getAbsolutePath());
                    					fileList.add(readfile.getAbsolutePath());
                    				} 
                    				else if (readfile.isDirectory()) 
                    				{
                    					readDirs(filepath + "\\" + filelist[i]);
                    				}
                			}
            		}

        } 
		catch (FileNotFoundException e)   {System.out.println(e.getMessage());}
        return fileList;
    }
	public String readFiles(String file) throws FileNotFoundException, IOException 
	{
        	String sb = "";
        	InputStreamReader is = new InputStreamReader(new FileInputStream(file), "gbk");
        	BufferedReader br = new BufferedReader(is);
        	String line = br.readLine();
        	while (line != null) 
			{
	            	sb=sb+line;
	        		line = br.readLine();
	        }
	        br.close();
	        is.close();
			//String text=sb.toString();
	        return sb;
	}	
	public void process(String content)
	{
			List<String> wordList=new ArrayList<String>();
			String term="";
			int space=content.indexOf(" ");
			while(space!=-1)
			{
				term=content.substring(0, space);
				content=content.substring(space+1);
				if(!wordList.contains(term))
				{
					wordList.add(term);
					if(termMap.containsKey(term))
					{
						int d=termMap.get(term);
						d++;
						termMap.put(term, d);
					}
				}
				content=content.trim();
				space=content.indexOf(" ");
			}
			if(!wordList.contains(content))
			{
				if(termMap.containsKey(content))
				{
					int d=termMap.get(content);
					d++;
					termMap.put(content, d);
				}
			}
	}
	public HashMap<String, Float> tf(String content) 
	{
			HashMap<String, Float> tf = new HashMap<String, Float>();//存储一片文章的tf.正规化，tf=文档内词频/文档内词总数
			for(String word:termMap.keySet())
			{
				tf.put(word,new Float(0));
			}
			String term="";
			float d=0;
			int space=content.indexOf(" ");
			while(space!=-1)
			{
				term=content.substring(0, space);
				content=content.substring(space+1);
				if(tf.containsKey(term))
				{
						d=tf.get(term);
						d=d+1;
						tf.put(term,d);
				}
				content=content.trim();
				space=content.indexOf(" ");
			}
			if(tf.containsKey(content))
			{
					d=tf.get(content);
					d=d+1;
					tf.put(content, d);
			}
        	
			//System.out.println(tf.size());
			/*String[] cutWordResult=content.split(" ");
			int wordtf = 0;
	    	int vsmNum = termMap.keySet().size();
	    	int wordNum = cutWordResult.length;
	    	for (String str:termMap.keySet()) 
	    	{
	        	wordtf = 0;
	    		for (int j = 0; j < wordNum; j++)  if (str.equals(cutWordResult[j])) wordtf++;
	    		//System.out.println(str+"  "+wordtf);
	    		tf.put(str, wordtf);
	    	}*/
        	return tf;
    }
	public Map<String, HashMap<String, Float>> tfOfAll(String dir) throws IOException 
	{
        	//List<String> fileList = readDirs(dir);
			int i=0;
        	for (String file : fileList) 
        	{
        			System.out.println(i);
            		HashMap<String, Float> dict = new HashMap<String, Float>();
            		dict = tf(readFiles(file));
            		allTheTf.put(file, dict);
            		i++;
        	}
        	return allTheTf;
    }
	 public Map<String, Double> idf(String dir) throws FileNotFoundException, UnsupportedEncodingException, Exception 
	{
		 try
			{
				String content="";
				for(String file:fileList)
				{
					content=readFiles(file);
					process(content);
					System.out.println(file);
				}
				//String fileName="df.txt"; 
				//BufferedWriter out=new BufferedWriter(new FileWriter(fileName,true)); 
				double df=0;
				double fileTotal=fileList.size();
				double base=10;
				for(String term:termMap.keySet())
				{
					//out.write(term+"   "+termMap.get(term)+"\r\n");
					df=(double)termMap.get(term);
					if(df==0) df=1;//idf.put(term, Log.log(fileTotal/df, base));
					idf.put(term, Math.log10(fileTotal/df));
				}
			}
			catch(Exception e){e.printStackTrace();}
        	return idf;
   	}	 
	public Map<String, HashMap<String, Float>> result(String dir) throws Exception 
	{
			getVsm();
			fileList=readDirs(dir);
        	idf(dir);
			System.out.println(fileList.size());
			tfOfAll(dir);
        	Map<String, HashMap<String, Float>> tfidf =new HashMap<String, HashMap<String, Float>>();
        	//System.out.println(tf_map.size());
        	for (String file : allTheTf.keySet())
        	{
            		HashMap<String, Float> singelFile = allTheTf.get(file);
            		double normal=0;
            		System.out.println(file+"   tfidf");
            		for (String word : singelFile.keySet()) 
            		{
            			//System.out.println(word);
            			float f=singelFile.get(word);
            			normal=normal+(idf.get(word)) * singelFile.get(word)*(idf.get(word)) * singelFile.get(word);
            			//singelFile.put(word, (idf_map.get(word)) * singelFile.get(word));
            			//System.out.println(word+"  "+wordtf);
            			System.out.println(word+"  "+f+"  "+idf.get(word)+"  "+normal);
            		}
            		for (String word : singelFile.keySet()) 
            		{
            			//System.out.println(word);
            			//System.out.println(word+"  "+allTheTf.get(file).get(word)+"  "+singelFile.get(word));
            			//System.out.println(str+"  "+wordtf);
            			if(normal!=0)
            			singelFile.put(word,new Float((idf.get(word)) * singelFile.get(word)/Math.sqrt(normal)));
            			else singelFile.put(word,new Float(0));
            			//System.out.println(word+"  "+idf.get(word)+"  "+singelFile.get(word));
            		}
            		tfidf.put(file, singelFile);
            		//break;
        	}
        	return tfidf;
    }
}