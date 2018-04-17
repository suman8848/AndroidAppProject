package com.example.sumankhatiwada.vehiclebazzar.mvp.model.sessionmanagement;

/**
 *
 */
public class AppConstants {

    public enum SharedPreferenceKeys {
        USER_PASSWORD("userPassword"),
        USER_EMAIL("userEmail"),
        USER_COUNT_CHECK("counterChecker"),
        USER_ID("uid"),
        NAME("name");



        private String value;

        SharedPreferenceKeys(String value) {
            this.value = value;
        }

        public String getKey() {
            return value;
        }
    }

}
