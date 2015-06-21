/**
 * The MIT License
 * Copyright (c) ${project.inceptionYear} Flemming Harms
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.fharms.marshalling.service;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Equator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fharms.marshalling.jpa.CustomerPersister;
import com.fharms.marshalling.model.Address;
import com.fharms.marshalling.model.Customer;
import com.fharms.marshalling.utils.HibernateDetachUtil;
import com.fharms.marshalling.utils.HibernateTestUtil;

/**
 * This test the {@link MarshallingService} with a number of different scenario 
 * for Serialize and Deserialize proxy object and lazy initialization
 *
 * @author Flemming Harms (flemming.harms at gmail.com)
 *
 */
public class MarshallingServiceTest {

    private CustomerPersister customerPersister;
    private Session session;
    private MarshallingService marshallingService;
    private FileOutputStream fileOutputStream;

    @Before
    public void setUp() throws Exception {
        marshallingService = new MarshallingService();
        SessionFactory sessionFactory = HibernateTestUtil.getSessionFactory();
        session = sessionFactory.openSession();
        customerPersister = new CustomerPersister(session);
        setupTestData();
        fileOutputStream = new FileOutputStream("marshalling.tmp");
    }

    @After
    public void tearDown() throws Exception {
        session.close();
        session = null;
    }

    @Test
    public void testSerialize() throws IOException {
        Collection<Customer> allData = getAllData();
        marshallingService.serialize(allData, fileOutputStream);
    }
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void testDeserialize() throws Exception {
        session.clear();
        Collection<Customer> allData = getAllData();
        marshallingService.serialize(allData, fileOutputStream);
        FileInputStream fileInputStream = new FileInputStream("marshalling.tmp");
        Collection<Customer> loadAllData = marshallingService.deserialize(fileInputStream,Collection.class);
        Assert.assertTrue(CollectionUtils.isEqualCollection(allData, loadAllData, new CustomerEquator()));
    }
    
    @Test
    public void testSerializeAndDeserializeSingleProxyObject() throws Exception {
        List<Address> addressList = new ArrayList<Address>();
        addressList.add(new Address("1000 Colonial Farm Rd",220101L,"","VA","United States"));
        
        Customer customer = new Customer("Catherine","Ryan", addressList);
        customerPersister.persist(customer);
        
        session.flush();
        session.clear();
        Customer loadedCustomer = customerPersister.getById(customer.getUuid());
        assertTrue(HibernateProxy.class.isInstance(loadedCustomer));
        marshallingService.serialize(loadedCustomer, fileOutputStream);
        
        FileInputStream fileInputStream = new FileInputStream("marshalling.tmp");
        Customer serializedcustomer = marshallingService.deserialize(fileInputStream,Customer.class);
        Assert.assertTrue(CollectionUtils.isEqualCollection(Collections.singletonList(loadedCustomer), Collections.singletonList(serializedcustomer), new CustomerEquator()));
        
    }
   
    private Collection<Customer> getAllData() {
        Collection<Customer> customers = customerPersister.getAllCustomers();
        return customers;
    }
    
    private void setupTestData() {
        int size = 1;
        for (int i = 0; i < size; i++) {
            List<Address> addressList = new ArrayList<Address>();
            addressList.add(new Address("1000 Colonial Farm Rd",220101L,"","VA","United States"));
            addressList.add(new Address("1000 Colonial Farm Rd",220101L,"","VA","United States"));
            
            Customer customer = new Customer("Jack","Ryan", addressList);
            customerPersister.persist(customer);

        }
        session.flush();
    }
    
    private static final class CustomerEquator implements Equator<Customer> {
        @SuppressWarnings("unchecked")
        @Override
        public boolean equate(Customer customer1, Customer customer2) {
           
            if(!customer1.getUuid().equals(customer2.getUuid())){
                return false;
            } else if(!customer1.getFirstname().equals(customer2.getFirstname())){
               return false; 
            } else if(!customer1.getLastname().equals(customer2.getLastname())){
                return false; 
            }
            
            //Because the original collection of customers will contain the PersistentBag type for the address
            //collection we have to clean it up before we can compare it, because Customer.equals will return false
            Collection<Customer> cleanCastCollection = new HibernateDetachUtil().cleanCastCollection(customer1.getAddress(), Collection.class);
            return CollectionUtils.isEqualCollection(cleanCastCollection, customer2.getAddress());
        }

        @Override
        public int hash(Customer o) {
            return 0;
        }
    }

}
