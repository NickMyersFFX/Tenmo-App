package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Account seeAccountBalance(int userId) {

       Account account = new Account();

       String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";

       SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId);

        while( rows.next() ) {
            account = ( mapRowToAccount(rows) );
        }

        return account;

    }

    private Account mapRowToAccount(SqlRowSet row) {
        Account account = new Account();

        account.setAccountId( row.getInt("account_id") );
        account.setUserId( row.getInt("user_id") );
        account.setBalance( row.getDouble("balance") );

        return account;
    }

}
