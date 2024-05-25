package nl.fontys.sem6.auth.message.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ResponseMessage {

	private String message;

	// Default constructor
	public ResponseMessage() {}

	// Constructor for deserialization
	@JsonCreator
	public ResponseMessage(@JsonProperty("message") String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ResponseMessage{" +
				"message='" + message + '\'' +
				'}';
	}
}

//public class ResponseMessage {
//
//	private String message;
//
//	public ResponseMessage(String message) {
//		this.message = message;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//}
