package com.klef.jfsd.exam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.query.criteria.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class ClientDemo {

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Project.class);

        SessionFactory sessionFactory = config.buildSessionFactory();

        // Insert records
        insertRecords(sessionFactory);

        // Perform aggregate functions
        performAggregateFunctions(sessionFactory);

        sessionFactory.close();
    }

    private static void insertRecords(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Project project = new Project();
        project.setProjectName("JFSD");
        project.setDuration(12);
        project.setBudget(50);
        project.setTeamLead("Vishi"); 
        session.save(project);
        transaction.commit();
        session.close();
    }

    private static void performAggregateFunctions(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        Criteria criteria = session.createCriteria(Project.class);

        // Count
        criteria.setProjection(Projections.rowCount());
        System.out.println("Total Projects: " + criteria.uniqueResult());

        // Max Budget
        criteria.setProjection(Projections.max("budget"));
        System.out.println("Max Budget: " + criteria.uniqueResult());

        // Min Budget
        criteria.setProjection(Projections.min("budget"));
        System.out.println("Min Budget: " + criteria.uniqueResult());

        // Sum of Budget
        criteria.setProjection(Projections.sum("budget"));
        System.out.println("Total Budget: " + criteria.uniqueResult());

        // Average Budget
        criteria.setProjection(Projections.avg("budget"));
        System.out.println("Average Budget: " + criteria.uniqueResult());

    
        session.close();
    }
}
