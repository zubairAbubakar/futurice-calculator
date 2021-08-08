package com.zlab.futurice.calculator.restController;

import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("calculator/v1/api")
public class Calculator {

    @GetMapping("/calculus")
    public String calculate(HttpServletRequest request) {

        String queryString = request.getQueryString().replaceAll("%20", "") ;
        String query = queryString.substring(queryString.indexOf('y')+2, queryString.length());
        Expression expression = new Expression(query);
        Double ans = expression.calculate();

        return ans.toString();
    }
}
