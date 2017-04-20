//
//  ========================================================================
//  Copyright (c) 1995-2017 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.toolchain.test.JDK;
import org.eclipse.jetty.util.resource.Resource;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TypeUtilLoadedFromTest
{
    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data()
    {
        List<Object[]> cases = new ArrayList<>();
        
        // A class from java runtime
        cases.add(new Object[]{String.class, containsString(JDK.IS_9 ? "jrt:/java.base/java/lang/String.clas" : "/rt.jar")});
        // A class from maven dependencies
        cases.add(new Object[]{Assert.class, containsString("/junit/")});
        // A class from project self
        cases.add(new Object[]{TypeUtil.class, containsString("/target/classes/")});
        
        return cases;
    }
    
    @Parameterized.Parameter(0)
    public Class<?> clazz;
    
    @Parameterized.Parameter(1)
    public Matcher<String> resultMatcher;
    
    @Test
    public void getGetLoadedFrom()
    {
        Resource resource = TypeUtil.getLoadedFrom(clazz);
        assertThat("TypeUtil.getLoadedFrom(" + clazz + ")", resource, notNullValue());
        assertThat("Resource.toString()", resource.toString(), resultMatcher);
    }
}
