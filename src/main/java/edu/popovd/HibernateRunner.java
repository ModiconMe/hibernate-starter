package edu.popovd;

import edu.popovd.entity.User;
import edu.popovd.entity.UserChat;
import edu.popovd.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HibernateRunner {

    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
//            session.enableFetchProfile("withCompany");

            RootGraph<User> userGraph = session.createEntityGraph(User.class);
            userGraph.addAttributeNodes("userChats");
            SubGraph<UserChat> userChatsSubgraph = userGraph.addSubgraph("userChats", UserChat.class);
            userChatsSubgraph.addAttributeNodes("chat");

//            RootGraph<?> graph = session.getEntityGraph("withChat");

            Map<String, Object> props = Map.of(GraphSemantic.LOAD.getJakartaHintName(), userGraph);
            session.find(User.class, 1L, props);

//            User user = session.get(User.class, 1L);
//            System.out.println(user.getPayments().size());
//            System.out.println(user.getCompany().getName());
//
            List<User> users = session.createQuery("select u from User u", User.class)
                    .setHint(GraphSemantic.LOAD.getJakartaHintName(), userGraph)
                    .list();
            users.forEach(user -> System.out.println(user.getPayments().size()));
            users.forEach(user -> System.out.println(user.getCompany().getName()));


            session.getTransaction().commit();
        }
    }
}
