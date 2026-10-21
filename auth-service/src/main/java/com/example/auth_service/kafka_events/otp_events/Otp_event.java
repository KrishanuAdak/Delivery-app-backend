package com.example.auth_service.kafka_events.otp_events;

import com.example.auth_service.enums.EventsName.OtpEventTypes;

public record Otp_event(String phone_number, String otp, OtpEventTypes event_type) {

}
