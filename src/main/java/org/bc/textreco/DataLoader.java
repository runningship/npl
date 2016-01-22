package org.bc.textreco;

import java.util.List;

import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;
import org.bc.textreco.entity.Eigen;

//加载标准数据到内存中
public class DataLoader {

	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	public void init(){
		List<Eigen> list = dao.listByParams(Eigen.class, "from Eigen where 1=1");
		EigenBoxFactory boxFactory = new EigenBoxFactory();
		for(Eigen eigen : list){
			boxFactory.add(eigen);
		}
	}
}
