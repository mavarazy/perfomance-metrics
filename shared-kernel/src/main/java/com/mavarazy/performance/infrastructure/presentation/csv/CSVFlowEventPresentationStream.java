package com.mavarazy.performance.infrastructure.presentation.csv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.mavarazy.performance.Invocation;
import com.mavarazy.performance.flow.FlowPosition;
import com.mavarazy.performance.flow.call.CallEvent;
import com.mavarazy.performance.flow.call.CallMetric;
import com.mavarazy.performance.flow.call.event.CallEndEvent;
import com.mavarazy.performance.flow.call.event.CallErrorEvent;
import com.mavarazy.performance.flow.call.event.CallExceptionEvent;
import com.mavarazy.performance.flow.call.event.CallStartEvent;
import com.mavarazy.performance.flow.call.metric.CallArgumentsMetric;
import com.mavarazy.performance.flow.call.metric.CallNanoTimeMetric;
import com.mavarazy.performance.flow.call.metric.CallResultMetric;
import com.mavarazy.performance.flow.call.metric.CallTimeMetric;
import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.PresentationStream;

public class CSVFlowEventPresentationStream implements PresentationStream<String, FlowEvent> {

    final static private PresentationWriter<CallEvent, StringBuilder> callEventWriter = new PresentationWriter<CallEvent, StringBuilder>() {
        @Override
        public StringBuilder write(StringBuilder accumulator, CallEvent callEvent) {
            if (callEvent instanceof CallStartEvent) {
                accumulator.append("start:");
            } else if(callEvent instanceof CallEndEvent) {
                accumulator.append("end:");
            } else if(callEvent instanceof CallErrorEvent) {
                accumulator.append("err:").append(prepare(((CallErrorEvent) callEvent).getErrorMessage())).append(":");
            } else if(callEvent instanceof CallExceptionEvent) {
                accumulator.append("exc:").append(prepare(((CallExceptionEvent) callEvent).getExceptionMessage())).append(":");
            }
            invocationWriter.write(accumulator, callEvent.getInvocation());
            return accumulator;
        }
    };
    final static private PresentationReader<CallEvent, String> callEventReader = new PresentationReader<CallEvent, String>() {
        @Override
        public CallEvent read(String source) {
            CallEvent callEvent = null;
            String[] invocation = source.split(":");
            if(invocation[0].equals("start")) {
                callEvent = new CallStartEvent(invocationReader.read(invocation[1]));
            } else if(invocation[0].equals("end")){
                callEvent = new CallEndEvent(invocationReader.read(invocation[1]));
            }else if(invocation[0].equals("err")) {
                callEvent = new CallErrorEvent(invocationReader.read(invocation[2]), invocation[1]);
            }else if(invocation[0].equals("exc")) {
                callEvent = new CallExceptionEvent(invocationReader.read(invocation[2]), invocation[1]);
            }
            return callEvent;
        }
    };
    
    final static private PresentationWriter<Invocation, StringBuilder> invocationWriter = new PresentationWriter<Invocation, StringBuilder>() {
        @Override
        public StringBuilder write(StringBuilder accumulator, Invocation callInvocation) {
            return accumulator.append(callInvocation.getClassName()).append(".").append(callInvocation.getMethodName());
        }
    };
    final static private PresentationReader<Invocation, String> invocationReader = new PresentationReader<Invocation, String>() {
        @Override
        public Invocation read(String source) {
            String[] invocation = source.split("[.]");
            return Invocation.valueOf(invocation[0], invocation[1]);
        }
    };

    final static private PresentationWriter<FlowPosition, StringBuilder> flowIdentifierWriter = new PresentationWriter<FlowPosition, StringBuilder>() {
        @Override
        public StringBuilder write(StringBuilder accumulator, FlowPosition flowIdentifier) {
            return accumulator.append(flowIdentifier.getFlowIdentifier()).append("/").append(flowIdentifier.getPosition());
        }
    };
    final static private PresentationReader<FlowPosition, String> flowIdentifierReader = new PresentationReader<FlowPosition, String>() {
        @Override
        public FlowPosition read(String source) {
            String[] flow = source.split("/");
            return new FlowPosition(flow[0], Integer.valueOf(flow[1]));
        }
    };

    @SuppressWarnings("rawtypes")
    final static private Map<Class, PresentationWriter> presentationWriters = new HashMap<Class, PresentationWriter>();
    @SuppressWarnings("rawtypes")
    final static private Map<String, PresentationReader> presentationReaders = new HashMap<String, PresentationStream.PresentationReader>();

    static {
        presentationWriters.put(CallTimeMetric.class, new PresentationWriter<CallTimeMetric, StringBuilder>() {
            @Override
            public StringBuilder write(StringBuilder accumulator, CallTimeMetric call) {
                return accumulator.append("time:").append(call.getTime());
            }
        });
        presentationReaders.put("time", new PresentationReader<CallTimeMetric, String>() {
            @Override
            public CallTimeMetric read(String source) {
                return new CallTimeMetric(Long.valueOf(source));
            }
        });
        presentationWriters.put(CallNanoTimeMetric.class, new PresentationWriter<CallNanoTimeMetric, StringBuilder>() {
            @Override
            public StringBuilder write(StringBuilder accumulator, CallNanoTimeMetric call) {
                return accumulator.append("nano:").append(call.getNanoTime());
            }
        });
        presentationReaders.put("nano", new PresentationReader<CallNanoTimeMetric, String>() {
            @Override
            public CallNanoTimeMetric read(String source) {
                return new CallNanoTimeMetric(Long.valueOf(source));
            }
        });
        presentationWriters.put(CallResultMetric.class, new PresentationWriter<CallResultMetric, StringBuilder>() {
            @Override
            public StringBuilder write(StringBuilder accumulator, CallResultMetric call) {
                String result = call.getResult() != null ? call.getResult().toString() : "null";
                return accumulator.append("res:").append(result.replace(",", ";"));
            }
        });
        presentationReaders.put("res", new PresentationReader<CallResultMetric, String>() {
            @Override
            public CallResultMetric read(String source) {
                return new CallResultMetric(source);
            }
        });
        presentationWriters.put(CallArgumentsMetric.class, new PresentationWriter<CallArgumentsMetric, StringBuilder>() {
            @Override
            public StringBuilder write(StringBuilder accumulator, CallArgumentsMetric object) {
                StringBuilder argumentList = new StringBuilder();
                if (object.getArguments() != null && object.getArguments().length > 0) {
                    Object[] arguments = object.getArguments();
                    for (int i = 0; i < arguments.length; i++) {
                        Object argument = arguments[i];
                        if(i != 0)
                            argumentList.append("/");
                        if (argument != null) {
                            argumentList.append(argument.toString().replace("/", "").replace(",", ";"));
                        } else {
                            argumentList.append("null");
                        }
                    }
                } else {
                    argumentList.append("none");
                }
                return accumulator.append("args:").append(argumentList);
            }
        });
        presentationReaders.put("args", new PresentationReader<CallArgumentsMetric, String>() {
            @Override
            public CallArgumentsMetric read(String source) {
                String[] arguments = source.split("/");
                return new CallArgumentsMetric(arguments);
            }
        });
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String write(FlowEvent call) {
        StringBuilder accumulator = new StringBuilder();

        flowIdentifierWriter.write(accumulator, call.getFlowPosition());
        accumulator.append(CSVConstants.SEPARATOR);
        callEventWriter.write(accumulator, call.getCallEvent());

        for (CallMetric callMetric : call.getCallMetrics()) {
            PresentationWriter callAppender = presentationWriters.get(callMetric.getClass());
            accumulator.append(CSVConstants.SEPARATOR);
            callAppender.write(accumulator, callMetric);
        }

        accumulator.append(CSVConstants.EOL);

        return accumulator.toString();
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public FlowEvent read(String result) {
        String[] splittedString = result.trim().split(",");

        FlowPosition flowIdentifier = flowIdentifierReader.read(splittedString[0]);
        CallEvent callEvent = callEventReader.read(splittedString[1]);
        Collection<CallMetric> callMetrics = new ArrayList<CallMetric>();

        for (int i = 2; i < splittedString.length; i++) {
            String[] value = splittedString[i].split(":");
            PresentationReader metricsReader = presentationReaders.get(value[0]);
            if (metricsReader != null) {
                callMetrics.add((CallMetric) metricsReader.read(value[1]));
            }
        }

        return new FlowEvent(flowIdentifier, callEvent, callMetrics);
    }

    final private static String prepare(String message) {
        return message.replace(",", ";").replace(":", ";");
    }
    
}


