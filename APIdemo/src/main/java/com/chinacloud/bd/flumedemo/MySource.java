package com.chinacloud.bd.flumedemo;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

public class MySource extends AbstractSource implements Configurable, EventDrivenSource {

    @Override
    public synchronized void start() {

        // Initialize the connection to the external client
        super.start();

    }

    @Override
    public synchronized void stop() {

        // Disconnect from external client and do any additional cleanup
        // (e.g. releasing resources or nulling-out field values) ..
        super.stop();

    }

    public void process() throws EventDeliveryException {

        try {
            // This try clause includes whatever Channel operations you want to
            // do

            // Receive new data
            Event e = getSomeData();

            // Store the Event into this Source's associated Channel(s)
            getChannelProcessor().processEvent(e);

        } catch (Throwable t) {

            // Log exception, handle individual exceptions as needed

            // re-throw all Errors
            if (t instanceof Error) {
                throw (Error) t;
            }
        }
    }

    private Event getSomeData() {

        return new SimpleEvent();

    }

    public void configure(Context context) {
        String myProp = context.getString("myProp", "defaultValue");

        // Process the myProp value (e.g. validation, convert to another type,
        // ...)

        // Store myProp for later retrieval by process() method
    }

}