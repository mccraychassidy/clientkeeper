package com.nashss.se.clientkeeper.lambda;

import com.nashss.se.clientkeeper.activity.requests.DeleteOrderRequest;
import com.nashss.se.clientkeeper.activity.results.DeleteOrderResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * AWS Lambda function handler for deleting an order.
 */
public class DeleteOrderLambda extends LambdaActivityRunner<DeleteOrderRequest, DeleteOrderResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteOrderRequest>, LambdaResponse> {

    /**
     * Handles the Lambda function request to delete an order.
     *
     * @param input   the authenticated request
     * @param context the Lambda execution environment context
     * @return result of the order deletion
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteOrderRequest> input, Context context) {
        return super.runActivity(
            () -> {
                DeleteOrderRequest unauthenticatedRequest = input.fromBody(DeleteOrderRequest.class);
                return input.fromUserClaims(claims ->
                        DeleteOrderRequest.builder()
                                .withUserEmail(claims.get("email"))
                                .withOrderId(unauthenticatedRequest.getOrderId())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideDeleteOrderActivity().handleRequest(request)
        );
    }
}
