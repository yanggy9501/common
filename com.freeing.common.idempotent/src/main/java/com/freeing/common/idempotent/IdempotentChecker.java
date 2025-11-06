package com.freeing.common.idempotent;

import com.freeing.common.idempotent.annotation.Idempotent;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public interface IdempotentChecker {

    void checkToken(HttpServletRequest request);

    void checkArgs(Method executionMethod, Object[] args, Idempotent anno);
}
