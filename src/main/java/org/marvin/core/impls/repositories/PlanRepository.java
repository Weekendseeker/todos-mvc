package org.marvin.core.impls.repositories;

import org.marvin.core.interfaces.IPlanRepositories;
import org.marvin.core.models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PlanRepository implements IPlanRepositories<Todo> {

    private JdbcTemplate template;

    @Override
    public long create(Todo plan, long accountId) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(con -> {

            PreparedStatement ps =
                    con.prepareStatement("INSERT INTO todos (description, isdone)" +
                            "VALUES (?,?)",new String[]{"id"});

                    ps.setString(1,plan.getTitle());
                    ps.setBoolean(2,plan.isCompleted());

                    return ps;
        },keyHolder);

        long key = keyHolder.getKey().longValue();

        template.update( con -> {

            PreparedStatement ps =
                    con.prepareStatement("INSERT INTO todos_in_account (id_account, id_plan)" +
                            "VALUES (?,?)");

            ps.setLong(1,accountId);
            ps.setLong(2,key);

            return ps;
        });

        return key;
    }

    @Override
    public Todo update(Todo plan) {

        template.update("UPDATE todos SET description = ?, isdone = ? WHERE id = ?",
                plan.getTitle(),
                plan.isCompleted(),
                plan.getId());

        return plan;
    }

    @Override
    public void delete(long id) {

        template.update("DELETE FROM todos_in_account WHERE id_plan = ?",
                id);

        template.update("DELETE FROM todos WHERE id = ?",
                id);
    }

    @Override
    public void deleteComplete() {
        List<Todo> todos = findDonePlans();

        todos.forEach(todo -> {
            template.update("DELETE FROM todos_in_account WHERE id_plan = ?", todo.getId());
            template.update("DELETE FROM todos WHERE id = ?", todo.getId());
        });

    }

    @Override
    public List<Todo> findDonePlans() {

        return template.query("SELECT * FROM todos WHERE isDone = 'true'",
                this::convertFromResult
        );

    }

    @Override
    public Todo findById(long id) {

        return template.queryForObject("SELECT * FROM todos WHERE id = ? ",
                new Object[]{id},
                this::convertFromResult
        );

    }

    @Override
    public List<Todo> findByUserId(long id) {
        return template.query("SELECT * FROM todos_in_account " +
                        "INNER JOIN todos ON todos_in_account.id_plan = todos.id " +
                        "WHERE id_account = ?",

                new Object[]{id},
                this::convertFromResult
        );
    }

    private Todo convertFromResult(ResultSet rs, int row) throws SQLException {

        Todo plan = new Todo();
        plan.setId(rs.getLong("id"));
        plan.setTitle(rs.getString("description"));
        plan.setCompleted(rs.getBoolean("isDone"));

        return plan;
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public JdbcTemplate getTemplate() {
        return template;
    }
}
