package com.freeing.common.chain;

public interface Chain extends Command {
    /**
     * 在列表中添加一个 Command
     * @param command 责任链处理器
     */
    void addCommand(Command command);

    /**
     * 执行责任链
     *
     * @param context 责任链上下文
     * @throws Exception
     */
    void execute(Context context) throws Exception;
}
