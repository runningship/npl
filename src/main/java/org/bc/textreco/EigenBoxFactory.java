package org.bc.textreco;

import org.bc.textreco.entity.Eigen;

public class EigenBoxFactory {

	public static String EigenValueSperator="-";
	private static EigenBox box = new EigenBox();
	
	public void add(Eigen eigen){
		String[] data = eigen.value.split(EigenValueSperator);
		int value1 = Integer.valueOf(data[0]);
		int value2 = Integer.valueOf(data[1]);
		int value3 = Integer.valueOf(data[2]);
		int value4 = Integer.valueOf(data[3]);
		EigenBox tmp = addToBox(box, value1);
		tmp = addToBox(tmp , value2);
		tmp = addToBox(tmp , value3);
		tmp = addToBox(tmp , value4);
		tmp.ch = eigen.ch;
	}
	
	public EigenBox getEigenBox(){
		return box;
	}
	private EigenBox addToBox(EigenBox box, int value){
		EigenBox child = box.children[value%EigenBox.BoxSize];
		if(child==null){
			 child = new EigenBox();
			 box.children[value%EigenBox.BoxSize] = child;
		}
		return child;
	}
	
	public EigenBox getBox(EigenBox head , String eigenValue){
		String[] data = eigenValue.split(EigenValueSperator);
		EigenBox next =head ;
		for(String str : data){
			int value = Integer.valueOf(str);
			next = next.children[value%EigenBox.BoxSize];
		}
		return next;
	}
	public static void main(String[] args){
		EigenBoxFactory creator = new EigenBoxFactory();
		Eigen eigen = new Eigen();
		eigen.ch="b";
		eigen.value="148-037-130-093";
		creator.add(eigen );
		
		EigenBox result = creator.getBox(box, eigen.value);
		System.out.println(result.ch);
	}
}
