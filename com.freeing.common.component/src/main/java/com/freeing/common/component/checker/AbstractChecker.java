package com.freeing.common.component.checker;

import com.freeing.common.component.annotation.CheckPersist;

import java.lang.reflect.Field;

/**
 * @author yanggy
 */
public abstract class AbstractChecker {

    public abstract void check(Field field, String value, CheckPersist checkPersist);
}
