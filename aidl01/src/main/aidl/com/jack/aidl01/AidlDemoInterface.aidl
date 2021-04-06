// AidlDemoInterface.aidl
package com.jack.aidl01;

// Declare any non-default types here with import statements

interface AidlDemoInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void sendTwoNums(int nums1,int nums2);

    int getTwoNumsResult();
}