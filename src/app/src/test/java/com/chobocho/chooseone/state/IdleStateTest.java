package com.chobocho.chooseone.state;

import com.chobocho.chooseone.manager.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class IdleStateTest {
    private ChooseManager manager;
    IState idleState;

    @Before
    public void setUp() throws Exception {
        manager = new ChooseManagerImpl();
        idleState = new IdleState(manager);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void init() {
        idleState.Init();
        assert (idleState.mPointNum == 0);
    }

    @Test
    public void tick() {
    }

    @Test
    public void updatePointList() {
    }

    @Test
    public void toString1() {
        assert("IdleState".equals(idleState.toString()));
    }
}