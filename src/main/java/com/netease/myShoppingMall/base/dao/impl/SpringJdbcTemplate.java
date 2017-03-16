package com.netease.myShoppingMall.base.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.netease.myShoppingMall.base.dao.face.SpringJdbcInterface;

@Service
public class SpringJdbcTemplate implements SpringJdbcInterface{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		System.out.println(sql);
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public Map<String, Object> queryForMap(String sql) {
		System.out.println(sql);
		return jdbcTemplate.queryForMap(sql);
	}

	@Override
	public void execute(String sql) {
		System.out.println(sql);
		jdbcTemplate.execute(sql);	
	}

	@Override
    public Connection getConnection(){
        Connection conn = null;
        try {
            conn = (Connection)jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    public void close(Connection conn,Statement state){
        if(null != state){   
            try{   
                state.close() ;   
            }catch(SQLException e){   
                e.printStackTrace() ;   
            }   
        }   
        if(null != conn){
            try{   
                conn.close() ;  
            }catch(SQLException e){   
                e.printStackTrace() ;  
            }   
        }  
    }

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
}
