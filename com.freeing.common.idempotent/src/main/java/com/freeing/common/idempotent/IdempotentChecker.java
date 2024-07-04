package com.freeing.common.idempotent;

import javax.servlet.http.HttpServletRequest;

public interface IdempotentChecker {

    void checkToken(HttpServletRequest request);
}
