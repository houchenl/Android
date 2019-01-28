// IMyAidlInterface.aidl
package com.hc.service.aidl;

// Declare any non-default types here with import statements
import com.hc.service.aidl.Person;

interface IMyAidlInterface {

    void pay(int amount);

    List<Person> add(in Person person);

}
