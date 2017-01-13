package com.briarshore.model;

import org.junit.Before;
import org.junit.Test;

public class CustomerTest {
    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer();
    }

    @Test
    public void terribleIfElseLogic() throws Exception {


        final CustomerType customerType = customer.getType();
        final int totalSales = customer.getTotalSales();
        if (CustomerType.SMALL.equals(customerType)) {
            if (totalSales > 1000) {
                // SMALL VIP Customer
            }
        } else if (CustomerType.MEDIUM.equals(customerType)) {
            if (totalSales > 5000) {
                // MEDIUM VIP Customer
            }
        } else if (CustomerType.LARGE.equals(customerType)) {
            if (totalSales > 10000) {
                // LARGE VIP Customer
            }
        }


    }
}
