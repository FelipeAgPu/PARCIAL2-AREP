package edu.escuela;

import com.google.gson.Gson;

import static spark.Spark.*;

public class SparkWebServer {

    public static void main(String... args){
        staticFiles.location("/public");
        port(getPort());
        post("collatzsequence", (request,response) -> {
            response.type("application/json");
            return collatzJson(Integer.parseInt(request.queryParams("value")));
        });
    }

    public static String collatz(int number){
        if (number == 1){
            return "1";
        }else if (number % 2 == 0){
            return number + "->" + collatz(number/2);
        }else{
            return number + "->" + collatz((3*number)+1);
        }
    }

    public static String collatzJson(int number){
        return "{\n\"operation\": \"collatzsequence\",\n" +
                " \"input\":  " + number + ",\n" +
                " \"output\":  \"" + collatz(number) +"\",\n" +
                "}";
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }

}
