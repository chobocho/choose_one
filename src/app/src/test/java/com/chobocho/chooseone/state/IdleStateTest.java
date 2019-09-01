package com.chobocho.chooseone.state;

import com.chobocho.chooseone.manager.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class IdleStateTest {
    ChooseManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new ChooseManagerImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void init() {
        IState idleState = new IdleState(manager);
        assert (idleState.mPointNum == 0);
    }

    @Test
    public void tick() {
    }

    @Test
    public void updatePointList() {
    }

    @Test
    public void updatePointList1() {
    }

    @Test
    public void toString1() {
    }
}