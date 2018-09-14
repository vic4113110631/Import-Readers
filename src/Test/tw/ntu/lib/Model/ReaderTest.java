package tw.ntu.lib.Model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReaderTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration config = new Configuration().configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).configure().build();

        sessionFactory = config.buildSessionFactory(serviceRegistry);

        session = sessionFactory.openSession();

        transaction = session.beginTransaction();
    }

    @After
    public void destory() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testSaveReader() {
        Reader reader = new Reader("231231", "william", "A123456789", "123456@gmail.com");
        session.save(reader);
    }
}