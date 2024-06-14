package com.nashss.se.clientkeeper.lambda;

import com.nashss.se.clientkeeper.activity.requests.GetOrdersByClientIdRequest;
import com.nashss.se.clientkeeper.activity.results.GetOrdersByClientIdResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * AWS Lambda function handler for getting orders by clientId.
 */
public class GetOrdersByClientIdLambda extends
        LambdaActivityRunner<GetOrdersByClientIdRequest, GetOrdersByClientIdResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetOrdersByClientIdRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetOrdersByClientIdRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPath(path ->
                    GetOrdersByClientIdRequest.builder()
                            .withClientId(path.get("clientId"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetOrdersByClientIdActivity().handleRequest(request)
        );
    }
}
