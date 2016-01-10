/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lealone.cgroup.subsystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.lealone.cgroup.Group;
import org.lealone.cgroup.Threads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubSystemTest {

    protected Logger log;
    protected Group root;
    protected Group g1;

    private static ThreadLocal<SubSystemTest> instance = new ThreadLocal<>();

    public SubSystemTest() {
    }

    protected SubSystemTest(SubSystemType type) {
        log = LoggerFactory.getLogger(this.getClass());

        root = Group.createRootGroup(type.name(), type);
        g1 = root.createSubGroup("g1");

        instance.set(this);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        SubSystemTest test = instance.get();
        instance.remove();
        if (test != null) {
            test.root.umount();
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // @Test
    public void testAddTask() {
        int tid = Threads.getThreadId();
        g1.getCpu().addTask(tid);
    }

    // FIXME
    // @Test
    public void testSetEventControl() {
    }

    // FIXME
    // @Test
    public void testSetNotifyOnRelease() {
        g1.getCpu().setNotifyOnRelease(true);
        assertTrue(g1.getCpu().isNotifyOnRelease());
    }

    // FIXME
    // @Test
    public void testSetReleaseAgent() {
        String excepted = "echo 0";
        g1.getCpu().setReleaseAgent(excepted);
        String actual = g1.getCpu().getReleaseAgent();
        assertEquals(actual, excepted);
    }

}
