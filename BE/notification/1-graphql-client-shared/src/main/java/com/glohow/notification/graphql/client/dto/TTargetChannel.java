package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TTargetChannel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Boolean sendEmail;
    private String severity;

    public TTargetChannel() {
    }

    public TTargetChannel(String name, Boolean sendEmail, String severity) {
        this.name = name;
        this.sendEmail = sendEmail;
        this.severity = severity;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSendEmail() {
        return sendEmail;
    }
    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getSeverity() {
        return severity;
    }
    public void setSeverity(String severity) {
        this.severity = severity;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (name != null) {
            joiner.add("name: " + GraphQLRequestSerializer.getEntry(name));
        }
        if (sendEmail != null) {
            joiner.add("sendEmail: " + GraphQLRequestSerializer.getEntry(sendEmail));
        }
        if (severity != null) {
            joiner.add("severity: " + GraphQLRequestSerializer.getEntry(severity));
        }
        return joiner.toString();
    }

    public static TTargetChannel.Builder builder() {
        return new TTargetChannel.Builder();
    }

    public static class Builder {

        private String name;
        private Boolean sendEmail;
        private String severity;

        public Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSendEmail(Boolean sendEmail) {
            this.sendEmail = sendEmail;
            return this;
        }

        public Builder setSeverity(String severity) {
            this.severity = severity;
            return this;
        }


        public TTargetChannel build() {
            return new TTargetChannel(name, sendEmail, severity);
        }

    }
}
