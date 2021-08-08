package com.zlab.futurice.calculator.controllers;

//import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@RestController
@RequestMapping("calculator/v1/api")
public class CalculatorController {

    @GetMapping(value = "/calculus", produces = "application/json")
    public ResponseEntity<?> calculate(HttpServletRequest request) throws JSONException {

        String queryString = request.getQueryString().replaceAll("%20", "") ;
        String query = queryString.substring(queryString.indexOf('y')+2, queryString.length());

        if(isBase64(query)){
            query = convertStringFromBase64(query);
        }

        Expression expression = new Expression(query);
        Double answer = expression.calculate();

        JSONObject response = new JSONObject();

        if (Double.isNaN(answer)) {
            response.put("error", true);
            response.put("message", "Invalid Expression");
        }
        else {
            response.put("error", false);
            response.put("result", answer);
        }

        return new ResponseEntity<String>(response.toString(), Double.isNaN(answer) ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }

    private boolean isBase64(String input) {
        boolean result = false;
        String test;
        try {
            test = convertStringFromBase64(input);
            if (input.equals(convertStringToBase64(test))) {
                result = true;
            }
        }
        catch (Exception ex) {
            result = false;
        }
        return result;
    }

    private String convertStringToBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    private String convertStringFromBase64(String input) {
        return new String(Base64.getDecoder().decode(input));
    }
}
