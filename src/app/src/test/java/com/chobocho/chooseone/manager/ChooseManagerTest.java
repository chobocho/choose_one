package com.chobocho.chooseone.manager;

import com.chobocho.chooseone.state.IState;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChooseManagerTest {
    ChooseManager chooseManager;

    @Before
    public void setUp() throws Exception {
        chooseManager = new ChooseManagerImpl();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void transit() {
        chooseManager.transit(IState.IDLE);
        assert("IdleState".equals(chooseManager.toString()));
    }

    @Test
    public void choosePoint() {
    }
}