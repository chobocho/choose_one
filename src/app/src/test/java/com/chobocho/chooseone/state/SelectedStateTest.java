package com.chobocho.chooseone.state;

import com.chobocho.chooseone.manager.ChooseManager;
import com.chobocho.chooseone.manager.ChooseManagerImpl;

import org.junit.Before;
import org.junit.Test;

public class SelectedStateTest {
    ChooseManager manager;
    IState selectedState;

    @Before
    public void setUp() throws Exception {
        manager = new ChooseManagerImpl();
        selectedState = new SelectedState(manager);
    }

    @Test
    public void init() {
    }

    @Test
    public void tick() {
    }

    @Test
    public void updatePointList() {
    }

    @Test
    public void toString1() {
    }
}