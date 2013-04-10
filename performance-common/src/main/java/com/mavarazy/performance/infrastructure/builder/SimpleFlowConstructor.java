package com.mavarazy.performance.infrastructure.builder;

import java.util.HashMap;
import java.util.Map;

import com.mavarazy.performance.flow.Flow;
import com.mavarazy.performance.flow.call.event.CallStartEvent;
import com.mavarazy.performance.flow.event.FlowEvent;

public class SimpleFlowConstructor extends FlowConstructor {

    final private Map<Integer, FlowEvent> events = new HashMap<Integer, FlowEvent>();
    final private Map<Integer, FlowBuilder> startEntries = new HashMap<Integer, FlowBuilder>();
    final private Map<Integer, FlowBuilder> endEntries = new HashMap<Integer, FlowBuilder>();

    final private String identifier;

    public SimpleFlowConstructor(String identifier) {
        this.identifier = identifier;
    }

    public boolean add(FlowEvent call) {
        int position = call.getFlowPosition().getPosition();
        events.put(position, call);
        if (call.getCallEvent() instanceof CallStartEvent) {
            FlowBuilder entry = new FlowBuilder(call);
            startEntries.put(position, entry);
            buildEntry(entry, entry.getStartPosition() + 1);
        } else {
            FlowEvent previousEvent = events.get(position - 1);
            if (previousEvent != null) {
                if (previousEvent.getCallEvent() instanceof CallStartEvent) {
                    buildEntry(startEntries.get(previousEvent.getFlowPosition().getPosition()), position);
                } else {
                    int depth = 1;
                    do {
                        depth++;
                        previousEvent = events.get(position - depth);
                    } while (previousEvent != null && !(previousEvent.getCallEvent() instanceof CallStartEvent));

                    if (previousEvent != null) {
                        FlowBuilder previousEntry = startEntries.get(previousEvent.getFlowPosition().getPosition());

                        while (depth != 1 && previousEntry != null) {
                            previousEntry = previousEntry.getParent();
                            depth--;
                        }
                        if (previousEntry != null)
                            buildEntry(previousEntry, position);
                    }
                }
            }
        }

        return complete();
    }

    private void buildEntry(FlowBuilder entry, int checkedPostion) {
        FlowEvent checkedValue = events.get(checkedPostion);
        if (checkedValue != null) {
            if (checkedValue.getCallEvent() instanceof CallStartEvent) {
                FlowBuilder nextEntry = startEntries.get(checkedValue.getFlowPosition().getPosition());

                nextEntry.setParent(entry);

                if (nextEntry.filled()) {
                    buildEntry(entry, nextEntry.getEndPosition() + 1);
                }
            } else {
                entry.setEnd(checkedValue);
                endEntries.put(checkedValue.getFlowPosition().getPosition(), entry);
                if (entry.getStartPosition() != 0 && entry.getParent() == null) {
                    FlowBuilder parentEntry = calculateParent(entry);
                    if (parentEntry != null) {
                        entry.setParent(parentEntry);
                        buildEntry(parentEntry, entry.getEndPosition() + 1);
                    }
                } else if (entry.getParent() != null) {
                    buildEntry(entry.getParent(), checkedPostion + 1);
                }
            }
        }
    }

    private FlowBuilder calculateParent(FlowBuilder entry) {
        if (entry == null || entry.getStartPosition() == 0)
            return null;
        if (entry.getParent() != null)
            return entry.getParent();

        FlowBuilder previousEntry = startEntries.get(entry.getStartPosition() - 1);
        if (previousEntry != null) {
            return previousEntry;
        } else if (endEntries.get(entry.getStartPosition() - 1) != null) {
            FlowBuilder subEntry = endEntries.get(entry.getStartPosition() - 1);
            return calculateParent(subEntry);
        }
        return null;
    }

    public boolean complete() {
        FlowBuilder firstEntry = startEntries.get(0);
        if (firstEntry == null || !firstEntry.filled())
            return false;
        return firstEntry.getEndPosition() + 1 == events.size();
    }

    public Flow build() {
        if (complete())
            return startEntries.get(0).build();
        throw new IllegalAccessError("Not yet complete");
    }

    public String getIdentifier() {
        return identifier;
    }

}
