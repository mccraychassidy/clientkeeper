package com.nashss.se.clientkeeper.lambda;

import com.nashss.se.clientkeeper.activity.requests.EditOrderRequest;
import com.nashss.se.clientkeeper.activity.results.EditOrderResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * AWS Lambda function handler for editing an order.
 */
public class EditOrderLambda extends LambdaActivityRunner<EditOrderRequest, EditOrderResult>
        implements RequestHandler<AuthenticatedLambdaRequest<EditOrderRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<EditOrderRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromBody(EditOrderRequest.class),
                (request, serviceComponent) ->
                        serviceComponent.provideEditOrderActivity().handleRequest(request)
        );
    }
}
