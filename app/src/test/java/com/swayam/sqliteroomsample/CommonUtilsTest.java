package com.swayam.sqliteroomsample;

import com.swayam.sqliteroomsample.Utils.CommonUtils;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by akhil on 21/2/18.
 */
public class CommonUtilsTest {
    String otp,userid;

    @Before
    public void setUp(){
        otp = CommonUtils.randomString(8);
        userid = CommonUtils.generateUID();
    }

    @Test
    public void checkOTP() throws Exception {
        assertNotEquals(CommonUtils.randomString(8),is(otp));
    }

    @Test
    public void checkUId() throws Exception{
        assertNotEquals(CommonUtils.generateUID(),is(userid));
    }

}