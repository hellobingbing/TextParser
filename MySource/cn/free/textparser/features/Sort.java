package cn.free.textparser.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Sort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		Map<String, Double> before = new HashMap<String, Double>();
		List<Map.Entry<String, Double>> sortedList = new ArrayList<Map.Entry<String,Double>>();
		before.put("haha", 1.2);
		before.put("hello", 0.9);
		before.put("world", 2.5);
		before.put("the", 0.9);
		before.put("what", 3.0);
		before.put("where", 3.0);
		Set<Map.Entry<String, Double>> set = before.entrySet();
		Iterator<Map.Entry<String, Double>> iterator = set.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		System.out.println();
		sortedList = sortMap(before);
		for(Map.Entry<String, Double> me : sortedList){
			System.out.println(me.toString());
		}
		
		
	}
	/**
	 * sort list
	 * @param before
	 */
	public static void sortList(List<Double> before){
		
		double tmp = 0.0f;
		for(int i = 0;i<before.size() - 1;i++){
			for(int j = before.size() - 1;j>i;j--){
				if(before.get(j) > before.get(j-1)){
					tmp = before.get(j-1);
					before.set(j-1, before.get(j));
					before.set(j, tmp);
				}else{
					continue;
				}
			}
		}
	}
	/**
	 * sort map
	 * @param before
	 * @return sorted list
	 */
	public static List<Map.Entry<String, Double>> sortMap(Map<String, Double> before){
		
		//double tmpValue = 0.0f;
		//String tmpString = null;
		Map.Entry<String, Double> tmp = null;
		List<Map.Entry<String, Double>> sortedList = 
				new ArrayList<Map.Entry<String,Double>>(before.entrySet());
		
		for(int i = 0;i<sortedList.size() - 1;i++){
			for(int j = sortedList.size() - 1;j>i;j--){
				if(sortedList.get(j).getValue() > sortedList.get(j-1).getValue()){
					tmp = sortedList.get(j-1);
					sortedList.set(j-1, sortedList.get(j));
					sortedList.set(j, tmp);
				}else{
					continue;
				}
			}			
		}	
		return sortedList;
	}
	
	//public static void swap(double d1,double d2){
		//double temp = 0.0f;
		//temp = d1;
		//d1 = d2;
		//d2 = temp;
	//}

}
