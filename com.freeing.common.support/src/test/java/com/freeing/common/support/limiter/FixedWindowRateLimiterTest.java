
package com.freeing.common.support.limiter;

/**
 * @author yanggy
 */
public class FixedWindowRateLimiterTest {
    public static void main(String[] args) {
        FixedWindowRateLimiter limiter = new FixedWindowRateLimiter(100, 100);
        for (int i = 0; i < 10000; i++) {
            boolean b = limiter.tryAcquire();
            if (b) {
                System.out.println("放行");
            } else {
                System.out.println("限流了");
            }
        }
    }
}
