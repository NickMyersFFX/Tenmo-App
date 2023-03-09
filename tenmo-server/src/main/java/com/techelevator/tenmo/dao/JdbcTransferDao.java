package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // methods needed
    // add transfer - insert values into the transfer table
    // update balance from user sending
    // update balance from user receiving
    // get transfer amount


//    @Override
//    public Transfer sendMoney(int transferId, int accountFrom, int accountTo, double amount) {
//        return null;
//    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";

        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());

        transfer.setTransferId(transferId);

        return transfer;
    }

    @Override
    public void updateBalanceSender(Transfer transfer) {
        String sql = "UPDATE account SET balance = (balance - ?) WHERE account_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountFrom());

    }

    @Override
    public void updateBalanceReceiver(Transfer transfer) {
        String sql = "UPDATE account SET balance = (balance + ?) WHERE account_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountTo());

    }


    private Transfer mapRowToTransfer(SqlRowSet rows) {
        Transfer transfer = new Transfer();

        transfer.setAccountFrom(rows.getInt("account_from"));
        transfer.setAccountTo(rows.getInt("account_to"));
        transfer.setAmount(rows.getInt("amount"));
        transfer.setTransferStatusId(rows.getInt("transfer_status_id"));
        transfer.setTransferTypeId(rows.getInt("transfer_type_id"));

        return transfer;
    }


}
