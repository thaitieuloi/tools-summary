package com.ttl.common.core.command;

import org.springframework.transaction.annotation.Transactional;

/**
 * Marker interface for commands that require transactional execution
 * All methods are automatically wrapped in a transaction
 */
@Transactional
public interface TransactionalCommand<In, Out> extends Command<In, Out> {

    @Override
    @Transactional
    default Out execute() {
        return Command.super.execute();
    }

    @Override
    @Transactional
    default Out execute(In input) {
        return Command.super.execute(input);
    }

    @Override
    @Transactional
    Out execute(CommandHolder<In> holder);
}
