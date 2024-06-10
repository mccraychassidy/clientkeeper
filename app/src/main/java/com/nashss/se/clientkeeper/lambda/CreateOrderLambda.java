package com.nashss.se.clientkeeper.lambda;
import com.nashss.se.clientkeeper.activity.requests.CreateOrderRequest;
import com.nashss.se.clientkeeper.activity.results.CreateOrderResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * AWS Lambda function handler for creating a new order.
 */
public class CreateOrderLambda extends LambdaActivityRunner<CreateOrderRequest, CreateOrderResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateOrderRequest>, LambdaResponse> {

    /**
     * Handles the Lambda function request to create a new order.
     *
     * @param input   the authenticated request
     * @param context the Lambda execution environment context
     * @return result of the order creation
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateOrderRequest> input, Context context) {
        return super.runActivity(
            () -> {
                CreateOrderRequest unauthenticatedRequest = input.fromBody(CreateOrderRequest.class);
                return input.fromUserClaims(claims ->
                            CreateOrderRequest.builder()
                                    .withUserEmail(claims.get("email"))
                                    .withOrderId(unauthenticatedRequest.getOrderId())
                                    .withClientId(unauthenticatedRequest.getClientId())
                                    .withItem(unauthenticatedRequest.getItem())
                                    .withShipped(unauthenticatedRequest.getShipped())
                                    .withPurchaseDate(unauthenticatedRequest.getPurchaseDate())
                                    .withShippingService(unauthenticatedRequest.getShippingService())
                                    .withExpectedDate(unauthenticatedRequest.getExpectedDate())
                                    .withDeliveredDate(unauthenticatedRequest.getDeliveredDate())
                                    .withTrackingNumber(unauthenticatedRequest.getTrackingNumber())
                                    .withReference(unauthenticatedRequest.getReference())
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideCreateOrderActivity().handleRequest(request)
        );
    }
}

