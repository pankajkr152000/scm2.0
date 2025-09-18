package com.scm.services.helpers;

import com.scm.constants.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Helper class to store a message with its content and type.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagePrint {

    /** Message content */
    private String content;

    /** Message type (defaults to SUCCESS) */
    private MessageType messageType;

}
