package com.freeing.common.chain;

public interface Filter extends Command {
    /**
     * <p>Execute any cleanup activities, such as releasing resources that
     * were acquired during the <code>execute()</code> method of this
     * {@link Filter} instance.</p>
     *
     * @param context The {@link Context} to be processed by this
     *  {@link Filter}
     * @param exception The <code>Exception</code> (if any) that was thrown
     *  by the last {@link Command} that was executed; otherwise
     *  <code>null</code>
     *
     * @exception IllegalArgumentException if <code>context</code>
     *  is <code>null</code>
     *
     * @return If a non-null <code>exception</code> was "handled" by this
     *  method (and therefore need not be rethrown), return <code>true</code>;
     *  otherwise return <code>false</code>
     */
    boolean postprocess(Context context, Exception exception);
}
