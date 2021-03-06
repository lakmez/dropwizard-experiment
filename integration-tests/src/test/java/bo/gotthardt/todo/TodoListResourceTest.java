package bo.gotthardt.todo;

import bo.gotthardt.jersey.parameters.ListFilteringFactory;
import bo.gotthardt.model.User;
import bo.gotthardt.model.todo.TodoItem;
import bo.gotthardt.model.todo.TodoList;
import bo.gotthardt.test.ApiIntegrationTest;
import bo.gotthardt.test.DummyAuth;
import bo.gotthardt.todolist.rest.TodoListResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static bo.gotthardt.test.assertj.DropwizardAssertions.assertThat;

/**
 * @author Bo Gotthardt
 */
public class TodoListResourceTest extends ApiIntegrationTest {
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TodoListResource(db))
            .addResource(new DummyAuth.Binder())
            .addResource(ListFilteringFactory.getBinder())
            .setMapper(getMapper())
            .build();

    @Test
    public void blah() {
        User user = new User("test", "blah", "Blah");
        db.save(user);
        DummyAuth.setUser(user);

        TodoList list = new TodoList("testlist", user);
        list.getItems().add(new TodoItem("testitem1"));
        list.getItems().add(new TodoItem("testitem2"));
        db.save(list);

        assertThat(GET("/todolists/" + list.getId()))
            .hasStatus(Response.Status.OK)
            .hasJsonContent(list);
    }

    @Override
    public ResourceTestRule getResources() {
        return resources;
    }
}
