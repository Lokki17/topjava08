package ru.javawebinar.topjava.service;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimingRule implements TestRule {
    private static final Logger LOG = LoggerFactory.getLogger(TimingRule.class);

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long startTime = System.nanoTime();
                base.evaluate();
                long endTime = System.nanoTime();
                LOG.info("The time of executing test is {} ms", (endTime - startTime)/1000000);
            }
        };
    }
}
