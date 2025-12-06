package com.ttl.common.core.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Holder class that wraps command input and execution context
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandHolder<In> {

    private In input;
    private Context context;
    private boolean acceptNullInput = false;

    public CommandHolder(In input, Context context) {
        this.input = input;
        this.context = context;
    }
}
