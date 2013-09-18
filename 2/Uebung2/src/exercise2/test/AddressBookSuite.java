package exercise2.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class) 
@SuiteClasses( {ControllerAddressBookIntegrationTest.class, AddressBookControllerTest.class} ) 
public class AddressBookSuite { 
}
