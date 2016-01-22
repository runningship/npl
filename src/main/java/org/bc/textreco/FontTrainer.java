package org.bc.textreco;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bc.npl.StartUpListener;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;
import org.bc.textreco.entity.Eigen;

public class FontTrainer {
	
	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	public Eigen learn(BufferedImage img , String ch) throws IOException{
		BufferedImage target = BinarizationUtil.processByMaxOffset(img , 0.70f);
		int[][] data = FeatrueCaculator.getDataArea(target);
		Eigenvalue result = FeatrueCaculator.getEigenvalue(data);
		Eigen eigen = new Eigen();
		eigen.value = result.toStringValue();
		eigen.ch = ch;
		Eigen po = dao.getUniqueByKeyValue(Eigen.class, "value", eigen.value);
		if(po==null){
			dao.saveOrUpdate(eigen);
		}
		return eigen;
	}
	
	public static void main(String[] args) throws IOException{
		StartUpListener.initDataSource();
		FontTrainer trainer = new FontTrainer();
		for(int i=48;i<=57;i++){
			BufferedImage img = ImageIO.read(new File("D:\\code\\text-reco\\std\\chars\\yahei\\normal\\"+i+".jpg"));
			trainer.learn(img, String.valueOf((char)i));
		}
		
	}
}
