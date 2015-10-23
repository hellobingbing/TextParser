package cn.free.textparser.segandfilter;

import java.io.File;
import java.util.Properties;

//import cn.com.sina.dataanalyze.config.Config;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class BasicSegmenter extends CRFClassifier<CoreLabel>{
	    private static BasicSegmenter segmenter;
	    
	    private BasicSegmenter(Properties props){
	    	super(props);
	    }
	    
	    private static void init (){
	    	File dir=new File("." + File.separator + "lib");
	    	Properties props = new Properties();
	         props.setProperty("sighanCorporaDict", dir.getAbsolutePath()+ File.separator + "data");
	         // props.setProperty("NormalizationTable", "data/norm.simp.utf8");
	         // props.setProperty("normTableEncoding", "UTF-8");
	         // below is needed because CTBSegDocumentIteratorFactory accesses it
	         props.setProperty("serDictionary", dir.getAbsolutePath() + 
	        		 File.separator + "data" + File.separator + "dict-chris6.ser.gz");
	         props.setProperty("inputEncoding", "UTF-8");
	         props.setProperty("sighanPostProcessing", "true");

	         segmenter = new BasicSegmenter(props);
	         segmenter.loadClassifierNoExceptions(dir.getAbsolutePath() + 
	        		 File.separator + "data" + File.separator + "ctb.gz", props);
	    }
	    public static synchronized BasicSegmenter getInstance() {
		if (segmenter == null) {
			init();
		}
		return segmenter;
	    }
}
