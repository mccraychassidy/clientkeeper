package com.nashss.se.clientkeeper.lambda;

import com.nashss.se.clientkeeper.activity.requests.GetOrderRequest;
import com.nashss.se.clientkeeper.activity.results.GetOrderResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * AWS Lambda function handler for getting an order.
 */
public class GetOrderLambda extends LambdaActivityRunner<GetOrderRequest, GetOrderResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetOrderRequest>, LambdaResponse> {

    /**
     * Handles the Lambda function request to get an order.
     *
     * @param input the authenticated request
     * @param context the Lambda execution environment context
     * @return result of the order retrieval
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetOrderRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromPathAndQuery((path, query) ->
                        GetOrderRequest.builder()
                                .withUserEmail(query.get("email"))
                                .withOrderId(path.get("orderId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetOrderActivity().handleRequest(request)
        );
    }
}
