package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

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
    public void updateBalance(Transfer transfer) {
        String sql = "UPDATE account SET balance = (balance - ?) WHERE account_id = ?";
        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountFrom());
        String sql2 = "UPDATE account SET balance = (balance + ?) WHERE account_id = ?";
        jdbcTemplate.update(sql2, transfer.getAmount(), transfer.getAccountTo());


    }

    @Override
    public List<Transfer> listOfTransfers(int userId) {
        List<Transfer> transfer = new ArrayList<>();
       // String sql1 = "SELECT transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE transfer_id = ?;";

        String sql = "SELECT transfer.transfer_id, userFrom.user_id AS user_id_from, userFrom.username AS user_from, \n" +
                "userTo.user_id AS user_id_to, userTo.username AS user_to, transfer.amount\n" +
                "FROM transfer\n" +
                "JOIN account AS acctFrom ON transfer.account_from = acctFrom.account_id\n" +
                "JOIN tenmo_user AS userFrom ON acctFrom.user_id = userFrom.user_id\n" +
                "JOIN account AS acctTo ON transfer.account_to = acctTo.account_id\n" +
                "JOIN tenmo_user AS userTo ON acctTo.user_id = userTo.user_id\n" +
                "WHERE userFrom.user_id = ? OR userTo.user_id = ?";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId, userId);
       while ( rows.next() ) {
            transfer.add(mapRowToTransfer(rows));
        }
        return transfer;
    }

//    @Override
//    public void updateBalanceReceiver(Transfer transfer) {
//        String sql = "UPDATE account SET balance = (balance + ?) WHERE account_id = ?";
//        jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountTo());
//
//    }

    @Override
    public Transfer transferById (int userId) {
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE transfer_id = ?;";

//        String sql = "SELECT transfer.transfer_id, userFrom.user_id AS user_id_from, userFrom.username AS user_from, \n" +
//                "userTo.user_id AS user_id_to, userTo.username AS user_to, transfer.amount\n" +
//                "FROM transfer\n" +
//                "JOIN account AS acctFrom ON transfer.account_from = acctFrom.account_id\n" +
//                "JOIN tenmo_user AS userFrom ON acctFrom.user_id = userFrom.user_id\n" +
//                "JOIN account AS acctTo ON transfer.account_to = acctTo.account_id\n" +
//                "JOIN tenmo_user AS userTo ON acctTo.user_id = userTo.user_id\n" +
//                "WHERE userFrom.user_id = ? OR userTo.user_id = ?";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId);
        while ( rows.next() ) {
            transfer = mapRowToTransfer2(rows);
        }
        return transfer;
    }





    private Transfer mapRowToTransfer(SqlRowSet rows) {
        Transfer transfer = new Transfer();

       // transfer.setAccountFrom(rows.getInt("account_from"));
    //   transfer.setAccountTo(rows.getInt("account_to"));
       transfer.setAmount(rows.getDouble("amount"));
      //  transfer.setTransferStatusId(rows.getInt("transfer_status_id"));
      // transfer.setTransferTypeId(rows.getInt("transfer_type_id"));
       transfer.setTransferId(rows.getInt("transfer_id"));
       transfer.setUsernameTo(rows.getString("user_to"));
       transfer.setUsernameFrom(rows.getString("user_from"));
       transfer.setUserIdTo(rows.getInt("user_id_to"));
       transfer.setUserIdFrom(rows.getInt("user_id_from"));


        return transfer;
    }


    private Transfer mapRowToTransfer2(SqlRowSet rows) {
        Transfer transfer = new Transfer();

        transfer.setAccountFrom(rows.getInt("account_from"));
        transfer.setAccountTo(rows.getInt("account_to"));
        transfer.setAmount(rows.getDouble("amount"));
        transfer.setTransferStatusId(rows.getInt("transfer_status_id"));
        transfer.setTransferTypeId(rows.getInt("transfer_type_id"));
       // transfer.setTransferId(rows.getInt("transfer_id"));
       // transfer.setUsernameTo(rows.getString("user_to"));
       // transfer.setUsernameFrom(rows.getString("user_from"));
       // transfer.setUserIdTo(rows.getInt("user_id_to"));
       // transfer.setUserIdFrom(rows.getInt("user_id_from"));


        return transfer;
    }


}
