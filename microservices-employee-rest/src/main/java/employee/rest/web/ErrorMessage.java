package employee.rest.web;

import lombok.Data;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@Data
class ErrorMessage {
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }
}