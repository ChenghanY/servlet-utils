package com.james.dao;

import com.james.domain.User;
import com.james.utils.JDBCUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author james
 * @Description
 * @Date 2019/10/15
 */
public class UserDAO {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtil.getDataSource());

    public User findUser(String username, String password) {
        String sql = "SELECT * FROM USER WHERE username = ? AND PASSWORD = ?;";

        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException e) {
            System.out.println("jdbcTemplate 查询不到user");
        }

        return user;
    }

}
