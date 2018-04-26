package com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement;

/**
 *
 */
public class AppConstants {

    public enum SharedPreferenceKeys {
        AUTH("auth"),
        NAME("name"),
        TOKEN("token");




        private String value;

        SharedPreferenceKeys(String value) {
            this.value = value;
        }

        public String getKey() {
            return value;
        }
    }

}
