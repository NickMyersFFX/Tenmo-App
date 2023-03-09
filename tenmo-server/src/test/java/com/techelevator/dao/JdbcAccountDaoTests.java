package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcAccountDaoTests extends BaseDaoTests{

private JdbcAccountDao jdbcAccountDao;
private Account testAccount;

private static final Account ACCOUNT_1 = new Account(2001, 1001, 1000.00);


    @Before
    public void setup() {
        jdbcAccountDao = new JdbcAccountDao(dataSource);
        testAccount = new Account(2001, 1001, 1000.00);

    }

    @Test
    public void seeAccountBalance_returns_correct_balance() {
        Account account = jdbcAccountDao.seeAccountBalance(1001);
        Assert.assertEquals(1000.00, account.getBalance(), 0.009); //bc we have .00 we go one more so .009

    }

}
