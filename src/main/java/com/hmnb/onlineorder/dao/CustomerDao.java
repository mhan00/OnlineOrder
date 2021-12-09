package com.hmnb.onlineorder.dao;

import com.hmnb.onlineorder.entity.Authorities;
import com.hmnb.onlineorder.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // similiar with component More detail -specifiy to interact with database
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void signUp(Customer customer) {
        Authorities authorities = new Authorities();
        authorities.setAuthorities("ROLE_USER");
        authorities.setEmail(customer.getEmail());

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit(); // sql中提交 如果无异常 成功提交

        } catch (Exception ex) {
            ex.printStackTrace(); // print error
            if (session != null) session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Customer getCustomer(String email) {
        Customer customer = null;
        try (Session session = sessionFactory.openSession()) {
            customer = session.get(Customer.class, email);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return customer;
    }
}


