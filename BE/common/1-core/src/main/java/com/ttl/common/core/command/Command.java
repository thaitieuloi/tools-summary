package com.ttl.common.core.command;

/**
 * Base interface for all commands in the system.
 * Commands follow the Command Pattern to encapsulate actions as objects.
 * 
 * @param <In>  Input type for the command
 * @param <Out> Output type returned by the command
 */
public interface Command<In, Out> {

    /**
     * Execute the command without input (for commands that don't require input)
     */
    default Out execute() {
        CommandHolder<In> holder = new CommandHolder<>(null, new Context());
        holder.setAcceptNullInput(true);
        return this.execute(holder);
    }

    /**
     * Execute the command with input
     */
    default Out execute(In input) {
        return this.execute(new CommandHolder<>(input, new Context()));
    }

    /**
     * Execute the command with a CommandHolder containing input and context
     */
    Out execute(CommandHolder<In> holder);
}
