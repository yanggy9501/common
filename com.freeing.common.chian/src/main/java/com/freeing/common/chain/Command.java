package com.freeing.common.chain;

public interface Command {

    void execute(Context context) throws Exception;
}
