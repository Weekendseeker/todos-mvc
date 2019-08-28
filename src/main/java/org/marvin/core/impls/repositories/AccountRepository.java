package org.marvin.core.impls.repositories;

import org.marvin.core.interfaces.IAccountsRepository;

import org.marvin.core.interfaces.IPlanRepositories;
import org.marvin.core.models.Account;
import org.marvin.core.models.Role;
import org.marvin.core.models.Todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class AccountRepository implements IAccountsRepository<Account> {

    private JdbcTemplate template ;
    private IPlanRepositories planRepository;

    @Override
    public Account findByLogin(String login){

        Account account = null;
        try{

            account = template.queryForObject("SELECT * FROM accounts WHERE username = ?",
                    new Object[]{login},
                    this::convertFromResultToAccount);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

        return account;
    }

    @Override
    public long createAccount(Account account) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(con -> {

            PreparedStatement statement =
                    con.prepareStatement(
                            "INSERT INTO accounts (username, password, active)  VALUES(?,?,?)",
                            new String[] {"id"});

            statement.setString(1,account.getUsername());
            statement.setString(2,account.getPassword());
            statement.setBoolean(3,account.isActive());

         return statement;

        },keyHolder);

        long accountId = keyHolder.getKey().longValue();

        account.getRoles().forEach(role -> {

            int id = getRoleId(role);

            template.update(con -> {

                PreparedStatement statement =
                        con.prepareStatement(
                                "INSERT INTO user_role (user_id, role_id)  VALUES(?,?)");

                statement.setLong(1,accountId);
                statement.setInt(2,id);

                return statement;
            });
        });

        return accountId;
    }

    public List<Role> roleSet(long id){

        return  template.query("SELECT r.role FROM user_role ur INNER JOIN roles r ON r.id = ur.role_id WHERE ur.user_id = ? ",
                    new Object[]{id},
                    this::convertFromResultToRole
                );
    }

    private int getRoleId(Role role){

        return template.queryForObject("SELECT id FROM roles WHERE role = ?",
                new Object[]{role.toString()},
                this::getRoleId
        );

    }

    private Account convertFromResultToAccount(ResultSet rs, int row) throws SQLException {

        long id = rs.getLong("id");

        List<Todo> plans = (List<Todo>)planRepository.findByUserId(id) ;
        List<Role> roles = roleSet(id);

        return new Account(
                id,
                rs.getString("username"),
                rs.getString("password"),
                rs.getBoolean("active"),
                plans,
                roles
        );

    }

    private Role convertFromResultToRole(ResultSet rs, int row) throws SQLException {

        return Role.valueOf(rs.getString("role"));
    }

    private int getRoleId(ResultSet rs, int row) throws SQLException {
        return rs.getInt("id");
    }

    public IPlanRepositories getPlanRepository() {
        return planRepository;
    }

    @Autowired
    public void setPlanRepository(IPlanRepositories planRepository){
        this.planRepository = planRepository;
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

}
