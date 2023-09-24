package com.freeing.common.component.checker;

import com.freeing.common.component.annotation.CheckPersist;
import com.freeing.common.component.checker.exception.CheckPersistException;

import java.lang.reflect.Field;

/**
 * String 属性校验器
 *
 * @author yanggy
 */
public class StringValuePersistChecker extends AbstractChecker {
    /**
     * check
     *
     * @param field object field
     * @param value value of field
     */
    @Override
    public void check(Field field, String value, CheckPersist checkPersist) {
        this.allowEmpty(field, value, checkPersist);
        this.allowNull(field, value, checkPersist);
    }

    private void allowEmpty(Field field, String value, CheckPersist checkPersist) {
        if (!checkPersist.allowEmpty()) {
            if (value == null || value.isEmpty()) {
                throw new CheckPersistException("check persist string allowEmpty error, " +
                    ", field:" + field.getName() + ", can not be null or empty.");
            }
        }
    }

    private void allowNull(Field field, String value, CheckPersist checkPersist) {
        if (!checkPersist.allowNull()) {
            if (value == null) {
                throw new CheckPersistException("check persist string allowEmpty error, " +
                    ", field:" + field.getName() + ", can not be null.");
            }
        }
    }
}
