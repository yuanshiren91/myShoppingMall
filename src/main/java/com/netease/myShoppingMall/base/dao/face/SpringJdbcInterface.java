package com.netease.myShoppingMall.base.dao.face;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public interface SpringJdbcInterface {
	public List<Map<String,Object>> queryForList(String sql);   
    public Map<String,Object> queryForMap(String sql);
    public void execute(String sql);
  
    public Connection getConnection();
    public JdbcTemplate getJdbcTemplate();
}
