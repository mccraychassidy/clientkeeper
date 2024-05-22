package com.nashss.se.clientkeeper.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetClientLambda implements RequestHandler<LambdaRequest<String>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<String> input, Context context) {
        return LambdaResponse.success();
    }
}
