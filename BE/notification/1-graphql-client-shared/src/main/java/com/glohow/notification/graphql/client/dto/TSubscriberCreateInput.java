package com.glohow.notification.graphql.client.dto;

import com.kobylynskyi.graphql.codegen.model.graphql.GraphQLRequestSerializer;
import java.util.StringJoiner;

public class TSubscriberCreateInput implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private java.util.List<String> _unset;
    private String email;
    private String fullName;
    private String refSource;
    private String refToken;

    public TSubscriberCreateInput() {
    }

    public TSubscriberCreateInput(java.util.List<String> _unset, String email, String fullName, String refSource, String refToken) {
        this._unset = _unset;
        this.email = email;
        this.fullName = fullName;
        this.refSource = refSource;
        this.refToken = refToken;
    }

    public java.util.List<String> get_unset() {
        return _unset;
    }
    public void set_unset(java.util.List<String> _unset) {
        this._unset = _unset;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRefSource() {
        return refSource;
    }
    public void setRefSource(String refSource) {
        this.refSource = refSource;
    }

    public String getRefToken() {
        return refToken;
    }
    public void setRefToken(String refToken) {
        this.refToken = refToken;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (_unset != null) {
            joiner.add("_unset: " + GraphQLRequestSerializer.getEntry(_unset));
        }
        if (email != null) {
            joiner.add("email: " + GraphQLRequestSerializer.getEntry(email));
        }
        if (fullName != null) {
            joiner.add("fullName: " + GraphQLRequestSerializer.getEntry(fullName));
        }
        if (refSource != null) {
            joiner.add("refSource: " + GraphQLRequestSerializer.getEntry(refSource));
        }
        if (refToken != null) {
            joiner.add("refToken: " + GraphQLRequestSerializer.getEntry(refToken));
        }
        return joiner.toString();
    }

    public static TSubscriberCreateInput.Builder builder() {
        return new TSubscriberCreateInput.Builder();
    }

    public static class Builder {

        private java.util.List<String> _unset;
        private String email;
        private String fullName;
        private String refSource;
        private String refToken;

        public Builder() {
        }

        public Builder set_unset(java.util.List<String> _unset) {
            this._unset = _unset;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setRefSource(String refSource) {
            this.refSource = refSource;
            return this;
        }

        public Builder setRefToken(String refToken) {
            this.refToken = refToken;
            return this;
        }


        public TSubscriberCreateInput build() {
            return new TSubscriberCreateInput(_unset, email, fullName, refSource, refToken);
        }

    }
}
