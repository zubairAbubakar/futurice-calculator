package com.zlab.futurice.calculator.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("calculator/v1/api")
public class Calculator {

    @GetMapping(value = "/calculus", produces = "application/json")
    public ResponseEntity<?> calculate(HttpServletRequest request) throws JSONException {

        String queryString = request.getQueryString().replaceAll("%20", "") ;
        String query = queryString.substring(queryString.indexOf('y')+2, queryString.length());

        if(Base64.isBase64(query)){
             query = new String(Base64.decodeBase64(query.getBytes()));
        }

        Expression expression = new Expression(query);
        Double answer = expression.calculate();

        JSONObject response = new JSONObject();

        if (Double.isNaN(answer)) {
            response.put("error", true);
            response.put("message", "incorrect expression");
        }
        else {
            response.put("error", false);
            response.put("result", answer);
        }

        return new ResponseEntity<String>(response.toString(), Double.isNaN(answer) ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }
}
