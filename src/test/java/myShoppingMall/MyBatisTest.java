package myShoppingMall;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.netease.myShoppingMall.base.domain.PageEntity;
import com.netease.myShoppingMall.base.domain.PageResult;
import com.netease.myShoppingMall.core.dao.GoodsMapper;
import com.netease.myShoppingMall.core.domain.Goods;



public class MyBatisTest {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		
		SqlSessionFactory sqlSessionFactory = context.getBean("sqlSessionFactory",SqlSessionFactory.class);
		
		SqlSession session = sqlSessionFactory.openSession();
		
		GoodsMapper goodsDao  = session.getMapper(GoodsMapper.class);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", 1);
	}
}
